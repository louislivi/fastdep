package com.louislivi.fastdep.datasource;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * FastDepDataSource Register
 *
 * @author : louislivi
 */
@SuppressWarnings("unchecked")
public class FastDepDataSourceRegister implements EnvironmentAware, ImportBeanDefinitionRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(FastDepDataSourceRegister.class);
    private final static ConfigurationPropertyNameAliases ALIASES = new ConfigurationPropertyNameAliases();

    static {
        ALIASES.addAliases("url", "jdbc-url");
        ALIASES.addAliases("username", "user");
    }

    private Environment env;
    private Binder binder;

    /**
     * registerBeanDefinitions
     *
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // get all datasource
        Map<String, Map> multipleDataSources;
        try {
            multipleDataSources = binder.bind("fastdep.datasource", Map.class).get();
        } catch (NoSuchElementException e) {
            logger.error("Failed to configure fastDep DataSource: 'fastdep.datasource' attribute is not specified and no embedded datasource could be configured.");
            return;
        }
        for (String key : multipleDataSources.keySet()) {
            // datasource
            Supplier<DataSource> dataSourceSupplier = () -> {
                AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
                FastDepXaProperties fastDepXaProperties = new FastDepXaProperties(env, key);
                Properties properties = fastDepXaProperties.getProperties();
                ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
                ds.setUniqueResourceName(key);
                ds.setMinPoolSize(fastDepXaProperties.getMinPoolSize());
                ds.setMaxPoolSize(fastDepXaProperties.getMaxPoolSize());
                ds.setBorrowConnectionTimeout(fastDepXaProperties.getBorrowConnectionTimeout());
                ds.setMaxIdleTime(fastDepXaProperties.getMaxIdleTime());
                ds.setTestQuery(fastDepXaProperties.getTestQuery());
                ds.setXaProperties(properties);
                System.out.println("-------" + ds.hashCode());
                return ds;
            };
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSource.class, dataSourceSupplier);
            AbstractBeanDefinition datasourceBean = builder.getRawBeanDefinition();
            datasourceBean.setDependsOn("txManager");
            beanDefinitionRegistry.registerBeanDefinition(key + "DataSource", datasourceBean);
        }
        logger.info("Registration dataSource completed !");
    }

    /**
     * init environment
     *
     * @param environment environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
        // bing binder
        binder = Binder.get(this.env);
    }
}
