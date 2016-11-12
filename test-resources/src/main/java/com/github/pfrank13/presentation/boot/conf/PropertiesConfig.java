package com.github.pfrank13.presentation.boot.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author pfrank
 */
@Configuration
@PropertySources({
    @PropertySource("classpath:/persistence.properties"),
    @PropertySource("classpath:/price-client.properties")
})
public class PropertiesConfig {
  @Bean
  public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
    return new PropertySourcesPlaceholderConfigurer();
  }
}
