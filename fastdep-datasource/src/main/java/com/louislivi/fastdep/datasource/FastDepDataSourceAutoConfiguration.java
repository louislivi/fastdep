package com.louislivi.fastdep.datasource;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * FastDepDataSourceAutoConfiguration
 *
 * @author : louislivi
 */
@Configuration
@EnableConfigurationProperties({FastDepDataSource.class, DataSourceProperties.class})
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@Import({FastDepDataSourceRegister.class, FastDepMybatisRegister.class, FastDepAtomikosTransactionConfigure.class})
public class FastDepDataSourceAutoConfiguration {
    public FastDepDataSourceAutoConfiguration() {
    }
}
