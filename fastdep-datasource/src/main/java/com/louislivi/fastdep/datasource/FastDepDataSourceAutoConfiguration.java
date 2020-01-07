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
 */
@Configuration
@EnableConfigurationProperties({FastDepDataSourceProperties.class})
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@Import({FastDepDataSourceRegister.class, FastDepAtomikosTransactionConfigure.class})
public class FastDepDataSourceAutoConfiguration {
    public FastDepDataSourceAutoConfiguration() {
    }
}
