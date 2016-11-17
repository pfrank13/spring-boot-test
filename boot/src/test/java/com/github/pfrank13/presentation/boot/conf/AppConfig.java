package com.github.pfrank13.presentation.boot.conf;

import com.github.pfrank13.presentation.boot.client.PersistenceBootstrapClient;
import com.github.pfrank13.presentation.boot.client.impl.RestOperationsPriceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.client.MockRestServiceServer;

/**
 * @author pfrank
 */
@Configuration
public class AppConfig {
  private final RestOperationsPriceClient restOperationsPriceClient;
  private final TestEntityManager testEntityManager;

  @Autowired
  public AppConfig(final TestEntityManager testEntityManager, final RestOperationsPriceClient restOperationsPriceClient){
    this.restOperationsPriceClient = restOperationsPriceClient;
    this.testEntityManager = testEntityManager;
  }

  @Bean
  public MockRestServiceServer mockRestServiceServer(){
    return MockRestServiceServer.createServer(restOperationsPriceClient.getRestOperations());
  }

  @Bean
  public PersistenceBootstrapClient persistenceBootstrapClient(){
    return new PersistenceBootstrapClient(testEntityManager);
  }
}
