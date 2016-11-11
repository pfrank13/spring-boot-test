package com.github.pfrank13.presentation.boot.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author pfrank
 */
@Configuration
public class EmbeddedDataSourceConfig {
  @Bean
  public DataSource persistenceDbDataSource(){
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                                        .addScript("classpath:/sql/schema-h2.sql")
                                        .setName("persistenceDb")
                                        .build();
  }
}
