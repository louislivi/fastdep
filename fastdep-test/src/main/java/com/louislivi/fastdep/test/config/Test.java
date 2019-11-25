package com.louislivi.fastdep.test.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

/**
 * test 源配置
 */
//@Configuration
//@MapperScan(basePackages = {"com.louislivi.fastdep.test.mapper.test"}, sqlSessionTemplateRef = "testSqlSessionTemplate")
public class Test extends MyDataSource {

    @Bean(name = "testDataSource")
    @DependsOn({"txManager"})
    public DataSource testDataSource() {
        return super.dataSource("test");
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */

    @Bean(name = "testSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("testDataSource") DataSource dataSource) throws Exception {
        return super.sqlSessionFactory(dataSource);
    }


    @Bean(name = "testSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("testSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return super.sqlSessionTemplate(sqlSessionFactory);
    }

}