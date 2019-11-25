package com.louislivi.fastdep.test.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * 数据源配置
 *
 * @author : louislivi
 * @date : 2019-03-23 08:47
 */
public class MyDataSource {
    @Autowired
    private Environment env;

    /**
     * 根据数据源创建SqlSessionFactory
     */
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(dataSource);
        // 指定数据源(这个必须有，否则报错)
        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));
        // 指定基包
        fb.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
        return fb.getObject();
    }

    /**
     * 生成 SqlSessionTemplate
     *
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 生成数据源
     *
     * @param name
     * @return
     */
    public DataSource dataSource(String name) {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName(name);
        ds.setMinPoolSize(3);
        ds.setMaxPoolSize(25);
        ds.setBorrowConnectionTimeout(60);
        ds.setTestQuery("SELECT 1");
        ds.setMaxLifetime(20000);
        String prefix = "fastdep.datasource.";
        ds.setXaProperties(build(prefix, name));
        System.out.println(1);
        return ds;
    }

    /**
     * 主要针对druid数据库链接池
     *
     * @param prefix
     * @return
     */
    protected Properties build(String prefix, String name) {
        prefix += name + ".";
        System.out.println(prefix + "url");
        Properties prop = new Properties();
        prop.put("url", env.getProperty(prefix + "url"));
        prop.put("username", env.getProperty(prefix + "username"));
        prop.put("password", env.getProperty(prefix + "password"));
        prop.put("driverClassName", env.getProperty(prefix + "driverClassName", ""));
//        prop.put("initialSize", env.getProperty(prefix + "initialSize", Integer.class));
//        prop.put("maxActive", env.getProperty(prefix + "maxActive", Integer.class));
//        prop.put("minIdle", env.getProperty(prefix + "minIdle", Integer.class));
//        prop.put("maxWait", env.getProperty(prefix + "maxWait", Integer.class));
//        prop.put("poolPreparedStatements", env.getProperty(prefix + "poolPreparedStatements", Boolean.class));
//        prop.put("maxPoolPreparedStatementPerConnectionSize",
//                env.getProperty(prefix + "maxPoolPreparedStatementPerConnectionSize", Integer.class));
//        prop.put("maxPoolPreparedStatementPerConnectionSize",
//                env.getProperty(prefix + "maxPoolPreparedStatementPerConnectionSize", Integer.class));
//        prop.put("validationQuery", env.getProperty(prefix + "validationQuery"));
//        prop.put("validationQueryTimeout", env.getProperty(prefix + "validationQueryTimeout", Integer.class));
//        prop.put("testOnBorrow", env.getProperty(prefix + "testOnBorrow", Boolean.class));
//        prop.put("testOnReturn", env.getProperty(prefix + "testOnReturn", Boolean.class));
//        prop.put("testWhileIdle", env.getProperty(prefix + "testWhileIdle", Boolean.class));
//        prop.put("timeBetweenEvictionRunsMillis",
//                env.getProperty(prefix + "timeBetweenEvictionRunsMillis", Integer.class));
//        prop.put("minEvictableIdleTimeMillis", env.getProperty(prefix + "minEvictableIdleTimeMillis", Integer.class));
//        prop.put("maxEvictableIdleTimeMillis", env.getProperty(prefix + "maxEvictableIdleTimeMillis", Integer.class));
//        prop.put("filters", env.getProperty(prefix + "filters"));
//        prop.put("connectionInitSqls", Collections.list(new StringTokenizer(env.getProperty(prefix + "connectionInitSqls"), ";")));
        return prop;
    }
}
