package com.github.pfrank13.presentation.boot.conf;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.zalando.jackson.datatype.money.MoneyModule;

/**
 * @author pfrank
 */
@Configuration
public class SpringBootApplicationConf {

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
    return (Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) -> jacksonObjectMapperBuilder.modules(new MoneyModule());
  }
}
