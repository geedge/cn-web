package com.clickhouse.client.http;

import cn.hutool.log.Log;
import com.clickhouse.client.ClickHouseClient;
import com.clickhouse.client.ClickHouseConfig;
import com.clickhouse.client.ClickHouseNode;
import com.clickhouse.client.ClickHouseRequest;
import com.clickhouse.client.ClickHouseSslContextProvider;
import com.clickhouse.client.config.ClickHouseClientOption;
import com.clickhouse.client.config.ClickHouseProxyType;
import com.clickhouse.client.http.config.ClickHouseHttpOption;
import com.clickhouse.data.ClickHouseChecker;
import com.clickhouse.data.ClickHouseDataStreamFactory;
import com.clickhouse.data.ClickHouseExternalTable;
import com.clickhouse.data.ClickHouseFormat;
import com.clickhouse.data.ClickHouseInputStream;
import com.clickhouse.data.ClickHouseOutputStream;
import com.clickhouse.data.ClickHousePassThruStream;
import com.clickhouse.data.ClickHousePipedOutputStream;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.ssl.SSLContext;

public class HttpClientConnectionImpl extends ClickHouseHttpConnection {
    private static final Log log = Log.get();
    private static final String USER_AGENT = ClickHouseClientOption.buildUserAgent((String)null, "HttpClient");
    private final AtomicBoolean busy;
    private final HttpClient httpClient;
    private final HttpRequest pingRequest;

    private ClickHouseHttpResponse buildResponse(ClickHouseConfig config, HttpResponse<InputStream> r, ClickHouseOutputStream output, Runnable postAction) throws IOException {
        HttpHeaders headers = r.headers();
        String displayName = (String)headers.firstValue("X-ClickHouse-Server-Display-Name").orElse(this.server.getHost());
        String queryId = (String)headers.firstValue("X-ClickHouse-Query-Id").orElse("");
        String summary = (String)headers.firstValue("X-ClickHouse-Summary").orElse("{}");
        ClickHouseFormat format = config.getFormat();
        TimeZone timeZone = config.getServerTimeZone();
        if (!ClickHouseChecker.isNullOrEmpty(queryId)) {
            String value = (String)headers.firstValue("X-ClickHouse-Format").orElse("");
            format = !ClickHouseChecker.isNullOrEmpty(value) ? ClickHouseFormat.valueOf(value) : format;
            value = (String)headers.firstValue("X-ClickHouse-Timezone").orElse("");
            timeZone = !ClickHouseChecker.isNullOrEmpty(value) ? TimeZone.getTimeZone(value) : timeZone;
        }

        boolean hasCustomOutput = output != null && output.getUnderlyingStream().hasOutput();
        Object source;
        Runnable action;
        if (output != null) {
            source = ClickHouseInputStream.empty();
            action = () -> {
                try {
                    OutputStream o = output;

                    try {
                        ClickHouseInputStream.pipe((InputStream)this.checkResponse(config, r).body(), o, config.getWriteBufferSize());
                        if (postAction != null) {
                            postAction.run();
                        }
                    } catch (Throwable var14) {
                        if (o != null) {
                            try {
                                ((OutputStream)o).close();
                            } catch (Throwable var13) {
                                var14.addSuppressed(var13);
                            }
                        }

                        throw var14;
                    }

                    if (o != null) {
                        ((OutputStream)o).close();
                    }
                } catch (IOException var15) {
                    IOException e = var15;
                    throw new UncheckedIOException("Failed to redirect response to given output stream", e);
                } finally {
                    this.closeQuietly();
                }

            };
        } else {
            source = (InputStream)this.checkResponse(config, r).body();
            action = () -> {
                if (postAction != null) {
                    postAction.run();
                }

                this.closeQuietly();
            };
        }

        return new ClickHouseHttpResponse(this, hasCustomOutput ? ClickHouseInputStream.of((InputStream)source, config.getReadBufferSize(), action) : ClickHouseInputStream.wrap((ClickHousePassThruStream)null, (InputStream)source, config.getReadBufferSize(), config.getResponseCompressAlgorithm(), config.getResponseCompressLevel(), action), displayName, queryId, summary, format, timeZone);
    }

    private HttpResponse<InputStream> checkResponse(ClickHouseConfig config, HttpResponse<InputStream> r) throws IOException {
        if (r.statusCode() != 200) {
            String errorCode = (String)r.headers().firstValue("X-ClickHouse-Exception-Code").orElse("");
            String serverName = (String)r.headers().firstValue("X-ClickHouse-Server-Display-Name").orElse("");
            int bufferSize = (Integer)ClickHouseClientOption.BUFFER_SIZE.getDefaultValue();
            ByteArrayOutputStream output = new ByteArrayOutputStream(bufferSize);
            ClickHouseInputStream.pipe((InputStream)r.body(), output, bufferSize);
            byte[] bytes = output.toByteArray();

            String errorMsg;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(ClickHouseClient.getResponseInputStream(config, new ByteArrayInputStream(bytes), this::closeQuietly), StandardCharsets.UTF_8));

                try {
                    StringBuilder builder = new StringBuilder();

                    while(true) {
                        if ((errorMsg = reader.readLine()) == null) {
                            errorMsg = builder.toString();
                            break;
                        }

                        builder.append(errorMsg).append('\n');
                    }
                } catch (Throwable var13) {
                    try {
                        reader.close();
                    } catch (Throwable var12) {
                        var13.addSuppressed(var12);
                    }

                    throw var13;
                }

                reader.close();
            } catch (IOException var14) {
                IOException e = var14;
                errorMsg = parseErrorFromException(errorCode, serverName, e, bytes);
            }

            throw new IOException(errorMsg);
        } else {
            return r;
        }
    }

    private HttpRequest newRequest(String url) {
        return HttpRequest.newBuilder().uri(URI.create(url)).version(Version.HTTP_1_1).timeout(Duration.ofMillis((long)this.config.getSocketTimeout())).build();
    }

    protected HttpClientConnectionImpl(ClickHouseNode server, ClickHouseRequest<?> request, ExecutorService executor) throws IOException {
        super(server, request);
        HttpClient.Builder builder = HttpClient.newBuilder().version(Version.HTTP_1_1).connectTimeout(Duration.ofMillis((long)this.config.getConnectionTimeout())).followRedirects(Redirect.NORMAL);
        if (executor != null) {
            builder.executor(executor);
        }

        ClickHouseProxyType proxyType = this.config.getProxyType();
        if (proxyType == ClickHouseProxyType.DIRECT) {
            builder.proxy(NoProxySelector.INSTANCE);
        } else if (proxyType == ClickHouseProxyType.HTTP) {
            builder.proxy(ProxySelector.of(new InetSocketAddress(this.config.getProxyHost(), this.config.getProxyPort())));
        } else if (proxyType != ClickHouseProxyType.IGNORE) {
            throw new IllegalArgumentException("Only HTTP(s) proxy is supported by HttpClient but we got: " + proxyType);
        }

        if (this.config.isSsl()) {
            builder.sslContext((SSLContext)ClickHouseSslContextProvider.getProvider().getSslContext(SSLContext.class, this.config).orElse((SSLContext) null));
        }

        this.busy = new AtomicBoolean(false);
        this.httpClient = builder.build();
        this.pingRequest = this.newRequest(this.getBaseUrl() + "ping");
    }

    protected boolean isReusable() {
        return this.busy.get();
    }

    private CompletableFuture<HttpResponse<InputStream>> postRequest(HttpRequest request) {
        return this.httpClient.sendAsync(request, (responseInfo) -> {
            return new ClickHouseResponseHandler(this.config.getMaxQueuedBuffers(), this.config.getSocketTimeout());
        });
    }

    private ClickHouseHttpResponse postStream(ClickHouseConfig config, HttpRequest.Builder reqBuilder, byte[] boundary, String sql, ClickHouseInputStream data, List<ClickHouseExternalTable> tables, ClickHouseOutputStream output, Runnable postAction) throws IOException {
        ClickHouseHttpResponse var22;
        try {
            ClickHousePipedOutputStream stream = ClickHouseDataStreamFactory.getInstance().createPipedOutputStream(config);
            Objects.requireNonNull(stream);
            reqBuilder.POST(BodyPublishers.ofInputStream(stream::getInputStream));
            CompletableFuture<HttpResponse<InputStream>> f = this.postRequest(reqBuilder.build());
            postData(config, boundary, sql, data, tables, stream);

            HttpResponse r;
            try {
                r = (HttpResponse)f.get();
            } catch (InterruptedException var18) {
                InterruptedException e = var18;
                Thread.currentThread().interrupt();
                throw new IOException("Thread was interrupted when posting request or receiving response", e);
            } catch (ExecutionException var19) {
                ExecutionException e = var19;
                Throwable cause = e.getCause();
                if (cause instanceof HttpConnectTimeoutException) {
                    throw new ConnectException(cause.getMessage());
                }

                throw new IOException("Failed to post request", cause);
            }

            var22 = this.buildResponse(config, r, output, postAction);
        } finally {
            this.busy.set(false);
        }

        return var22;
    }

    private ClickHouseHttpResponse postString(ClickHouseConfig config, HttpRequest.Builder reqBuilder, String sql, ClickHouseOutputStream output, Runnable postAction) throws IOException {
        ClickHouseHttpResponse var17;
        try {
            reqBuilder.POST(BodyPublishers.ofString(sql));

            HttpResponse r;
            try {
                r = (HttpResponse)this.postRequest(reqBuilder.build()).get();
            } catch (InterruptedException var13) {
                InterruptedException e = var13;
                Thread.currentThread().interrupt();
                throw new IOException("Thread was interrupted when posting request or receiving response", e);
            } catch (ExecutionException var14) {
                ExecutionException e = var14;
                Throwable cause = e.getCause();
                if (cause instanceof HttpConnectTimeoutException) {
                    throw new ConnectException(cause.getMessage());
                }

                throw new IOException("Failed to post query", cause);
            }

            var17 = this.buildResponse(config, r, output, postAction);
        } finally {
            this.busy.set(false);
        }

        return var17;
    }

    protected final String getDefaultUserAgent() {
        return USER_AGENT;
    }

    protected ClickHouseHttpResponse post(ClickHouseConfig config, String sql, ClickHouseInputStream data, List<ClickHouseExternalTable> tables, ClickHouseOutputStream output, String url, Map<String, String> headers, Runnable postAction) throws IOException {
        if (!this.busy.compareAndSet(false, true)) {
            throw new ConnectException("Connection is busy");
        } else {
            ClickHouseConfig c = config == null ? this.config : config;
            HttpRequest.Builder reqBuilder = HttpRequest.newBuilder().uri(URI.create(ClickHouseChecker.isNullOrEmpty(url) ? this.url : url)).timeout(Duration.ofMillis((long)c.getSocketTimeout()));
            byte[] boundary = null;
            if (tables != null && !tables.isEmpty()) {
                String uuid = this.rm.createUniqueId();
                reqBuilder.setHeader("content-type", "multipart/form-data; boundary=" + uuid);
                boundary = uuid.getBytes(StandardCharsets.US_ASCII);
            } else {
                reqBuilder.setHeader("content-type", "text/plain; charset=UTF-8");
            }

            headers = this.mergeHeaders(headers);
            if (headers != null && !headers.isEmpty()) {
                Iterator var14 = headers.entrySet().iterator();

                while(var14.hasNext()) {
                    Map.Entry<String, String> header = (Map.Entry)var14.next();
                    reqBuilder.setHeader((String)header.getKey(), (String)header.getValue());
                }
            }

            return boundary == null && data == null && !c.isRequestCompressed() ? this.postString(c, reqBuilder, sql, output, postAction) : this.postStream(c, reqBuilder, boundary, sql, data, tables, output, postAction);
        }
    }

    public boolean ping(int timeout) {
        String response = this.config.getStrOption(ClickHouseHttpOption.DEFAULT_RESPONSE);

        try {
            HttpResponse<String> r = this.httpClient.send(this.pingRequest, BodyHandlers.ofString());
            return r.statusCode() == 200 && response.equals(r.body());
        } catch (InterruptedException var4) {
            Thread.currentThread().interrupt();
        } catch (IOException var5) {
            IOException e = var5;
            log.debug("Failed to ping server: %s", new Object[]{e.getMessage()});
        }

        return false;
    }

    public void close() {
    }

    static class NoProxySelector extends ProxySelector {
        static final NoProxySelector INSTANCE = new NoProxySelector();
        private static final List<Proxy> NO_PROXY_LIST;

        private NoProxySelector() {
        }

        public void connectFailed(URI uri, SocketAddress sa, IOException e) {
        }

        public List<Proxy> select(URI uri) {
            return NO_PROXY_LIST;
        }

        static {
            NO_PROXY_LIST = List.of(Proxy.NO_PROXY);
        }
    }
}

