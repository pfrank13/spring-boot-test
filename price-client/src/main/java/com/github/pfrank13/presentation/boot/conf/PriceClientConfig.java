package com.github.pfrank13.presentation.boot.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pfrank13.presentation.boot.client.PriceClient;
import com.github.pfrank13.presentation.boot.client.impl.RestOperationsPriceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.zalando.jackson.datatype.money.MoneyModule;

/**
 * @author pfrank
 */
@Configuration
@ComponentScan(basePackages = {"com.github.pfrank13.presentation.boot.client.impl"})
public class PriceClientConfig {
  private final String baseUrl;

  @Autowired
  public PriceClientConfig(@Value("${price.client.baseUri}") final String baseUrl){
    this.baseUrl = baseUrl;
  }

  @Bean
  public RestTemplate priceClientRestTemplate(){
    final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                                                                 .modules(new MoneyModule())
                                                                 .build();
    final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
    return new RestTemplateBuilder().rootUri(baseUrl).messageConverters(mappingJackson2HttpMessageConverter).build();
  }

  @Bean
  public RestOperationsPriceClient priceClient(){
    return new RestOperationsPriceClient(this.priceClientRestTemplate());
  }

}
