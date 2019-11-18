package com.louislivi.fastdep.datasource;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * FastDepDataSource
 *
 * @author : louislivi
 */
@ConditionalOnProperty("fastdep.datasource")
@ConfigurationProperties("fastdep")
public class FastDepDataSource {

    private Map<String, DataSource> datasource;

    public FastDepDataSource() {
    }

    public Map<String, DataSource> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, DataSource> datasource) {
        this.datasource = datasource;
    }

    private static class DataSource extends DruidXADataSource {
        //fix druid  maxEvictableIdleTimeMillis must be grater than minEvictableIdleTimeMillis
        protected volatile long minEvictableIdleTimeMillis;

        protected volatile long maxEvictableIdleTimeMillis;

        @Override
        public long getMinEvictableIdleTimeMillis() {
            return minEvictableIdleTimeMillis;
        }

        @Override
        public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
            this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        }

        @Override
        public long getMaxEvictableIdleTimeMillis() {
            return maxEvictableIdleTimeMillis;
        }

        @Override
        public void setMaxEvictableIdleTimeMillis(long maxEvictableIdleTimeMillis) {
            this.maxEvictableIdleTimeMillis = maxEvictableIdleTimeMillis;
        }

        private String mapper;

        public String getMapper() {
            return mapper;
        }

        public void setMapper(String mapper) {
            this.mapper = mapper;
        }
    }
}
