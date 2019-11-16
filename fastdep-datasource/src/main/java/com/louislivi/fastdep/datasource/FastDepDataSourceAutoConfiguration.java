package com.louislivi.fastdep.datasource;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * FastDepDataSourceAutoConfiguration
 *
 * @author : louislivi
 * @date : 2019-11-12 10:23
 */
@Configuration
@EnableConfigurationProperties({FastDepDataSource.class})
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@Import({FastDepDataSourceRegister.class, FastDepAtomikosTransactionConfigure.class})
public class FastDepDataSourceAutoConfiguration {
    public FastDepDataSourceAutoConfiguration() {
    }
}
