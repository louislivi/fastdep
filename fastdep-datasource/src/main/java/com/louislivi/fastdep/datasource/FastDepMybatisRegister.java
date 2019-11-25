package com.louislivi.fastdep.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * FastDepDataSource Register
 *
 * @author : louislivi
 */
@SuppressWarnings("unchecked")
public class FastDepMybatisRegister implements EnvironmentAware, BeanDefinitionRegistryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(FastDepMybatisRegister.class);

    private Environment env;
    private Binder binder;

    /**
     * postProcessBeanDefinitionRegistry
     *
     * @param beanDefinitionRegistry beanDefinitionRegistry
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) {
    }

    /**
     *
     * @param factory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        Map<String, Map> multipleDataSources;
        try {
            multipleDataSources = binder.bind("fastdep.datasource", Map.class).get();
        } catch (NoSuchElementException e) {
            logger.error("Failed to configure fastDep DataSource: 'fastdep.datasource' attribute is not specified and no embedded datasource could be configured.");
            return;
        }
        for (String key : multipleDataSources.keySet()) {
            DataSource dataSource = (DataSource) factory.getBean(key + "DataSource");
            // sqlSessionFactory
            Supplier<SqlSessionFactory> sqlSessionFactorySupplier = () -> {
                System.out.println("=====" + dataSource.hashCode());
                try {
                    SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
                    fb.setDataSource(dataSource);
                    fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));
                    // mybatis.mapper-locations
                    fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
                    return fb.getObject();
                } catch (Exception e) {
                    logger.error("", e);
                }
                return null;
            };
            BeanDefinitionBuilder builder2 = BeanDefinitionBuilder.genericBeanDefinition(SqlSessionFactory.class, sqlSessionFactorySupplier);
            BeanDefinition sqlSessionFactoryBean = builder2.getRawBeanDefinition();
            ((DefaultListableBeanFactory) factory).registerBeanDefinition(key + "SqlSessionFactory", sqlSessionFactoryBean);
            // sqlSessionTemplate
            GenericBeanDefinition sqlSessionTemplate = new GenericBeanDefinition();
            sqlSessionTemplate.setBeanClass(SqlSessionTemplate.class);
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            constructorArgumentValues.addIndexedArgumentValue(0, factory.getBean(key + "SqlSessionFactory"));
            sqlSessionTemplate.setConstructorArgumentValues(constructorArgumentValues);
            ((DefaultListableBeanFactory) factory).registerBeanDefinition(key + "SqlSessionTemplate", sqlSessionTemplate);
            // MapperScanner
            ClassPathMapperScanner scanner = new ClassPathMapperScanner(((DefaultListableBeanFactory) factory));
            scanner.setSqlSessionTemplateBeanName(key + "SqlSessionTemplate");
            scanner.registerFilters();
            String mapperProperty = env.getProperty("fastdep.datasource." + key + ".mapper");
            if (mapperProperty == null) {
                logger.error("Failed to configure fastDep DataSource: fastdep.datasource." + key + ".mapper cannot be null.");
                return;
            }
            scanner.doScan(mapperProperty);
            logger.info("Registration dataSource ({}DataSource) !", key);
        }
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
