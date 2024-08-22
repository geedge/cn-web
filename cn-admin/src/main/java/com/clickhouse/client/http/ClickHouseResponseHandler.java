package com.clickhouse.client.http;

import com.clickhouse.data.ClickHouseByteBuffer;
import com.clickhouse.data.ClickHouseInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClickHouseResponseHandler implements HttpResponse.BodySubscriber<InputStream> {
    private static final List<ByteBuffer> LAST_LIST;
    private final BlockingQueue<ByteBuffer> buffers;
    private final ClickHouseInputStream in;
    private final AtomicBoolean subscribed;

    ClickHouseResponseHandler(int queueLength, int timeout) {
        this.buffers = (BlockingQueue)(queueLength > 1 ? new ArrayBlockingQueue(queueLength) : new LinkedBlockingQueue());
        this.in = ClickHouseInputStream.of(this.buffers, timeout);
        this.subscribed = new AtomicBoolean();
    }

    public void onSubscribe(Flow.Subscription s) {
        try {
            if (!this.subscribed.compareAndSet(false, true)) {
                s.cancel();
            } else {
                if (this.in.isClosed()) {
                    s.cancel();
                    return;
                }

                s.request(Long.MAX_VALUE);
            }
        } catch (Throwable var10) {
            Throwable t = var10;

            try {
                this.in.close();
            } catch (IOException var8) {
            } finally {
                this.onError(t);
            }
        }

    }

    public void onNext(List<ByteBuffer> item) {
        try {
            if (!this.buffers.addAll(item)) {
                throw new IllegalStateException("Queue is full");
            }
        } catch (Throwable var10) {
            Throwable t = var10;

            try {
                this.in.close();
            } catch (IOException var8) {
            } finally {
                this.onError(t);
            }
        }

    }

    public void onError(Throwable throwable) {
        this.buffers.offer(ClickHouseByteBuffer.EMPTY_BUFFER);
    }

    public void onComplete() {
        this.onNext(LAST_LIST);
    }

    public CompletionStage<InputStream> getBody() {
        return CompletableFuture.completedStage(this.in);
    }

    static {
        LAST_LIST = List.of(ClickHouseByteBuffer.EMPTY_BUFFER);
    }
}

