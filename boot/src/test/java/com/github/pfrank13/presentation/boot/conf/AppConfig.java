package com.github.pfrank13.presentation.boot.conf;

import com.github.pfrank13.presentation.boot.client.impl.RestOperationsPriceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.client.MockRestServiceServer;

/**
 * @author pfrank
 */
@Configuration
public class AppConfig {
  @Autowired
  private RestOperationsPriceClient restOperationsPriceClient;

  @Bean
  public MockRestServiceServer mockRestServiceServer(){
    return MockRestServiceServer.createServer(restOperationsPriceClient.getRestOperations());
  }
}
