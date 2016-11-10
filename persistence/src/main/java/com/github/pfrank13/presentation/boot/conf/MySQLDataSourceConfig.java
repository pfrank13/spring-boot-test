package com.github.pfrank13.presentation.boot.conf;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author pfrank
 */
@Configuration
public class MySQLDataSourceConfig {
  private String jdbcUrl;
  private String username;
  private String password;


  @Autowired
  public MySQLDataSourceConfig(@Value("${persistence.username}") final String username,
                               @Value("${persistence.password}") final String password,
                               @Value("${persistence.jdbc.url}") final String jdbcUrl){
    this.username = username;
    this.password = password;
    this.jdbcUrl = jdbcUrl;
  }

  @Bean
  public DataSource persistenceDbDataSource(){
    final BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUsername(username);
    basicDataSource.setPassword(password);
    basicDataSource.setUrl(jdbcUrl);

    return basicDataSource;
  }
}
