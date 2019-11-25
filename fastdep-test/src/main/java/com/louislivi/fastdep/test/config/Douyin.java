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
 * douyin 源配置
 */
//@Configuration
//@MapperScan(basePackages = {"com.louislivi.fastdep.test.mapper.douyin"}, sqlSessionTemplateRef = "douyinSqlSessionTemplate")
public class Douyin extends MyDataSource {

    @Bean(name = "douyinDataSource")
    @DependsOn({"txManager"})
    public DataSource douyinDataSource() {
        return super.dataSource("douyin");
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */

    @Bean(name = "douyinSqlSessionFactory")
    public SqlSessionFactory douyinSqlSessionFactory(@Qualifier("douyinDataSource") DataSource dataSource) throws Exception {
        return super.sqlSessionFactory(dataSource);
    }


    @Bean(name = "douyinSqlSessionTemplate")
    public SqlSessionTemplate douyinSqlSessionTemplate(
            @Qualifier("douyinSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return super.sqlSessionTemplate(sqlSessionFactory);
    }

}