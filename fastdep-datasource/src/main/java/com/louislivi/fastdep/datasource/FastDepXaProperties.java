package com.louislivi.fastdep.datasource;

import com.alibaba.druid.util.Utils;
import com.atomikos.datasource.pool.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * FastDepXa Properties
 *
 * @author : louislivi
 */
public class FastDepXaProperties {

    private static final Logger logger = LoggerFactory.getLogger(FastDepDataSource.class);

    private static final String PREFIX = "fastdep.datasource.";
    private int minPoolSize = 1;
    private int maxPoolSize = 1;
    private int borrowConnectionTimeout = 30;
    private int reapTimeout = 0;
    private int maxIdleTime = 60;
    private String testQuery;
    private int maintenanceInterval = 60;
    private int loginTimeout;
    private transient ConnectionPool connectionPool;
    private transient PrintWriter logWriter;
    private String resourceName;
    private int defaultIsolationLevel = -1;
    private int maxLifetime;
    private boolean enableConcurrentConnectionValidation = true;
    private Environment env;
    private Properties properties = new Properties();

    public FastDepXaProperties(Environment env, String key) {
        this.env = env;
        this.initConfig(key);
    }

    public static Logger getLogger() {
        return logger;
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getBorrowConnectionTimeout() {
        return borrowConnectionTimeout;
    }

    public void setBorrowConnectionTimeout(int borrowConnectionTimeout) {
        this.borrowConnectionTimeout = borrowConnectionTimeout;
    }

    public int getReapTimeout() {
        return reapTimeout;
    }

    public void setReapTimeout(int reapTimeout) {
        this.reapTimeout = reapTimeout;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public String getTestQuery() {
        return testQuery;
    }

    public void setTestQuery(String testQuery) {
        this.testQuery = testQuery;
    }

    public int getMaintenanceInterval() {
        return maintenanceInterval;
    }

    public void setMaintenanceInterval(int maintenanceInterval) {
        this.maintenanceInterval = maintenanceInterval;
    }

    public int getLoginTimeout() {
        return loginTimeout;
    }

    public void setLoginTimeout(int loginTimeout) {
        this.loginTimeout = loginTimeout;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public void setConnectionPool(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public PrintWriter getLogWriter() {
        return logWriter;
    }

    public void setLogWriter(PrintWriter logWriter) {
        this.logWriter = logWriter;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public int getDefaultIsolationLevel() {
        return defaultIsolationLevel;
    }

    public void setDefaultIsolationLevel(int defaultIsolationLevel) {
        this.defaultIsolationLevel = defaultIsolationLevel;
    }

    public int getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(int maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public boolean isEnableConcurrentConnectionValidation() {
        return enableConcurrentConnectionValidation;
    }

    public void setEnableConcurrentConnectionValidation(boolean enableConcurrentConnectionValidation) {
        this.enableConcurrentConnectionValidation = enableConcurrentConnectionValidation;
    }

    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * 初始化配置
     *
     * @param key
     */
    private void initConfig(String key) {
        String prefixKey = PREFIX + key + ".";

        String property = env.getProperty(prefixKey + "name");
        if (property != null) {
            this.properties.put("name", property);
        }

        property = env.getProperty(prefixKey + "url");
        if (property != null) {
            this.properties.put("url", property);
        }

        property = env.getProperty(prefixKey + "username");
        if (property != null) {
            this.properties.put("username", property);
        }

        property = env.getProperty(prefixKey + "password");
        if (property != null) {
            this.properties.put("password", property);
        }

        Boolean value = Utils.getBoolean(properties, prefixKey + "testWhileIdle");
        if (value != null) {
            this.properties.put("testWhileIdle", property);
        }

        value = Utils.getBoolean(properties, prefixKey + "testOnBorrow");
        if (value != null) {
            this.properties.put("testOnBorrow", property);
        }

        property = env.getProperty(prefixKey + "validationQuery");
        if (property != null && property.length() > 0) {
            this.properties.put("validationQuery", property);
            this.setTestQuery(property);
        }

        value = Utils.getBoolean(properties, prefixKey + "useGlobalDataSourceStat");
        if (value != null) {
            this.properties.put("useGlobalDataSourceStat", property);
        }

        value = Utils.getBoolean(properties, prefixKey + "useGloalDataSourceStat");
        if (value != null) {
            this.properties.put("useGloalDataSourceStat", property);
        }

        value = Utils.getBoolean(properties, prefixKey + "asyncInit");
        if (value != null) {
            this.properties.put("asyncInit", property);
        }

        property = env.getProperty(prefixKey + "filters");
        if (property != null && property.length() > 0) {
            this.properties.put("filters", property);
        }

        property = env.getProperty(prefixKey + "timeBetweenLogStatsMillis");
        if (property != null && property.length() > 0) {
            try {
                this.properties.put("timeBetweenLogStatsMillis", Long.parseLong(property));
            } catch (NumberFormatException var20) {
                logger.error("illegal property 'druid.timeBetweenLogStatsMillis'", var20);
            }
        }

        property = env.getProperty(prefixKey + "stat.sql.MaxSize");
        if (property != null && property.length() > 0) {
            try {
                if (this.properties.getProperty("stat.sql.MaxSize") != null) {
                    this.properties.put("stat.sql.MaxSize", Integer.parseInt(property));
                }
            } catch (NumberFormatException var19) {
                logger.error("illegal property 'druid.stat.sql.MaxSize'", var19);
            }
        }

        value = Utils.getBoolean(properties, prefixKey + "clearFiltersEnable");
        if (value != null) {
            this.properties.put("clearFiltersEnable", value);
        }

        value = Utils.getBoolean(properties, prefixKey + "resetStatEnable");
        if (value != null) {
            this.properties.put("resetStatEnable", value);
        }

        property = env.getProperty(prefixKey + "notFullTimeoutRetryCount");
        if (property != null && property.length() > 0) {
            try {
                this.properties.put("notFullTimeoutRetryCount", Integer.parseInt(property));
            } catch (NumberFormatException var18) {
                logger.error("illegal property 'druid.notFullTimeoutRetryCount'", var18);
            }
        }

        property = env.getProperty(prefixKey + "timeBetweenEvictionRunsMillis");
        if (property != null && property.length() > 0) {
            try {
                long timeBetweenEvictionRunsMillis = Long.parseLong(property);
                this.properties.put("timeBetweenEvictionRunsMillis", timeBetweenEvictionRunsMillis);
                this.setBorrowConnectionTimeout((int) timeBetweenEvictionRunsMillis);
            } catch (NumberFormatException var17) {
                logger.error("illegal property 'druid.timeBetweenEvictionRunsMillis'", var17);
            }
        }

        property = env.getProperty(prefixKey + "maxWaitThreadCount");
        if (property != null && property.length() > 0) {
            try {
                this.properties.put("maxWaitThreadCount", Integer.parseInt(property));
            } catch (NumberFormatException var16) {
                logger.error("illegal property 'druid.maxWaitThreadCount'", var16);
            }
        }

        property = env.getProperty(prefixKey + "maxWait");
        if (property != null && property.length() > 0) {
            try {
                this.properties.put("maxWait", Long.valueOf(property));
            } catch (NumberFormatException var15) {
                logger.error("illegal property 'druid.maxWait'", var15);
            }
        }

        value = Utils.getBoolean(properties, prefixKey + "failFast");
        if (value != null) {
            this.properties.put("failFast", value);
        }

        property = env.getProperty(prefixKey + "phyTimeoutMillis");
        if (property != null && property.length() > 0) {
            try {
                this.properties.put("phyTimeoutMillis", Long.parseLong(property));
            } catch (NumberFormatException var14) {
                logger.error("illegal property 'druid.phyTimeoutMillis'", var14);
            }
        }

        property = env.getProperty(prefixKey + "phyMaxUseCount");
        if (property != null && property.length() > 0) {
            try {
                this.properties.put("phyMaxUseCount", Long.parseLong(property));
            } catch (NumberFormatException var13) {
                logger.error("illegal property 'druid.phyMaxUseCount'", var13);
            }
        }

        property = env.getProperty(prefixKey + "minEvictableIdleTimeMillis");
        if (property != null && property.length() > 0) {
            try {
                this.properties.put("minEvictableIdleTimeMillis", Long.parseLong(property));
            } catch (NumberFormatException var12) {
                logger.error("illegal property 'druid.minEvictableIdleTimeMillis'", var12);
            }
        }

        property = env.getProperty(prefixKey + "maxEvictableIdleTimeMillis");
        if (property != null && property.length() > 0) {
            try {
                long maxEvictableIdleTimeMillis = Long.parseLong(property);
                this.properties.put("maxEvictableIdleTimeMillis", maxEvictableIdleTimeMillis);
                this.setMaxIdleTime((int) maxEvictableIdleTimeMillis);
            } catch (NumberFormatException var11) {
                logger.error("illegal property 'druid.maxEvictableIdleTimeMillis'", var11);
            }
        }

        value = Utils.getBoolean(properties, prefixKey + "keepAlive");
        if (value != null) {
            this.properties.put("keepAlive", value);
        }

        property = env.getProperty(prefixKey + "keepAliveBetweenTimeMillis");
        if (property != null && property.length() > 0) {
            try {
                this.properties.put("keepAliveBetweenTimeMillis", Long.parseLong(property));
            } catch (NumberFormatException var10) {
                logger.error("illegal property 'druid.keepAliveBetweenTimeMillis'", var10);
            }
        }

        value = Utils.getBoolean(properties, prefixKey + "poolPreparedStatements");
        if (value != null) {
            this.properties.put("poolPreparedStatements", value);
        }

        value = Utils.getBoolean(properties, prefixKey + "initVariants");
        if (value != null) {
            this.properties.put("initVariants", value);
        }

        value = Utils.getBoolean(properties, prefixKey + "initGlobalVariants");
        if (value != null) {
            this.properties.put("initGlobalVariants", value);
        }

        value = Utils.getBoolean(properties, prefixKey + "useUnfairLock");
        if (value != null) {
            this.properties.put("useUnfairLock", value);
        }

        property = env.getProperty(prefixKey + "driverClassName");
        if (property != null) {
            this.properties.put("driverClassName", property);
        }

        property = env.getProperty(prefixKey + "initialSize");
        if (property != null && property.length() > 0) {
            try {
                this.properties.put("initialSize", Integer.parseInt(property));
            } catch (NumberFormatException var9) {
                logger.error("illegal property 'druid.initialSize'", var9);
            }
        }

        property = env.getProperty(prefixKey + "minIdle");
        if (property != null && property.length() > 0) {
            try {
                int minIdle = Integer.parseInt(property);
                this.properties.put("minIdle", minIdle);
                this.setMinPoolSize(minIdle);
            } catch (NumberFormatException var8) {
                logger.error("illegal property 'druid.minIdle'", var8);
            }
        }

        property = env.getProperty(prefixKey + "maxActive");
        if (property != null && property.length() > 0) {
            try {
                int maxActive = Integer.parseInt(property);
                this.properties.put("maxActive", maxActive);
                this.setMaxPoolSize(maxActive);
            } catch (NumberFormatException var7) {
                logger.error("illegal property 'druid.maxActive'", var7);
            }
        }

        value = Utils.getBoolean(properties, prefixKey + "killWhenSocketReadTimeout");
        if (value != null) {
            this.properties.put("killWhenSocketReadTimeout", value);
        }

        property = env.getProperty(prefixKey + "connectProperties");
        if (property != null) {
            this.properties.put("connectProperties", property);
        }

        property = env.getProperty(prefixKey + "maxPoolPreparedStatementPerConnectionSize");
        if (property != null && property.length() > 0) {
            try {
                this.properties.put("maxPoolPreparedStatementPerConnectionSize", Integer.parseInt(property));
            } catch (NumberFormatException var6) {
                logger.error("illegal property 'druid.maxPoolPreparedStatementPerConnectionSize'", var6);
            }
        }

        property = env.getProperty(prefixKey + "initConnectionSqls");
        if (property != null && property.length() > 0) {
            try {
                StringTokenizer tokenizer = new StringTokenizer(property, ";");
                this.properties.put("initConnectionSqls", Collections.list(tokenizer));
            } catch (NumberFormatException var5) {
                logger.error("illegal property 'druid.initConnectionSqls'", var5);
            }
        }

        property = env.getProperty(prefixKey + "connectionInitSqls");
        if (property != null && property.length() > 0) {
            try {
                StringTokenizer tokenizer = new StringTokenizer(property, ";");
                this.properties.put("connectionInitSqls", Collections.list(tokenizer));
            } catch (NumberFormatException var5) {
                logger.error("illegal property 'druid.connectionInitSqls'", var5);
            }
        }

        property = System.getProperty(prefixKey + "load.spifilter.skip");
        if (property != null && !"false".equals(property)) {
            this.properties.put("loadSpifilterSkip", true);
        }
    }
}
