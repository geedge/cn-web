package net.geedge.common.utils;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.postgresql.jdbc2.optional.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Auther: lijinyang
 * @Date: 2023/07/14/16:55
 */
@Component
public class TrinoConnectionPool {

    /**
     * trino数据源配置项
     */
    @Value("${trino.host}")
    private String trinoHost;
    @Value("${trino.port:8053}")
    private int trinoPort;
    @Value("${trino.user}")
    private String trinoUser;
    @Value("${trino.pin}")
    private String trinoPin;
    private static final String trinoDriver = "io.trino.jdbc.TrinoDriver";
    private static final String trinoUrl = "jdbc:trino://{{host}}:{{port}}";

    @Value("${spring.datasource.druid.initial-size:10}")
    private int initialSize;
    @Value("${spring.datasource.druid.max-active:100}")
    private int dbMaxActive;
    @Value("${spring.datasource.druid.min-idle:5}")
    private int dbMinIdle;
    @Value("${spring.datasource.druid.max-wait:60000}")
    private int dbMaxWait;

    private static DruidDataSource dataSource;

    private void init(){
        dataSource = new DruidDataSource();
        dataSource.setUrl(getTrinoDbUrl());
        dataSource.setUsername(trinoUser);
        dataSource.setDriverClassName(trinoDriver);
        dataSource.setInitialSize(this.initialSize);
        dataSource.setMaxActive(this.dbMaxActive);
        dataSource.setMinIdle(this.dbMinIdle);
        dataSource.setMaxWait(this.dbMaxWait);
    }

    public Connection getConnection() throws SQLException {
        if (Tool.ObjectUtil.isEmpty(dataSource)) {
            init();
        }
        return dataSource.getConnection();
    }

    /**
     * 获取trino数据库连接url
     *
     * @return
     */
    private String getTrinoDbUrl() {
        return trinoUrl.replace("{{host}}", trinoHost)
                .replace("{{port}}", String.valueOf(trinoPort));
    }

}
