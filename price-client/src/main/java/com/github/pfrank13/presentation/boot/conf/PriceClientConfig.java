package com.github.pfrank13.presentation.boot.conf;

import com.github.pfrank13.presentation.boot.client.PriceClient;
import com.github.pfrank13.presentation.boot.client.impl.RestOperationsPriceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author pfrank
 */
@Configuration
@ComponentScan(basePackages = {"com.github.pfrank13.presentation.boot.client.impl"})
public class PriceClientConfig {
  private final RestTemplateBuilder restTemplateBuilder;
  private final String baseUrl;

  @Autowired
  public PriceClientConfig(final RestTemplateBuilder restTemplateBuilder,
                           @Value("${price.client.baseUri}") final String baseUrl){
    this.restTemplateBuilder = restTemplateBuilder;
    this.baseUrl = baseUrl;
  }

  @Bean
  public RestOperationsPriceClient priceClient(){
    return new RestOperationsPriceClient(baseUrl, restTemplateBuilder);
  }

}
