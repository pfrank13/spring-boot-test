package com.github.pfrank13.presentation.boot.conf;

import com.github.pfrank13.presentation.boot.client.PersistenceBootstrapClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

/**
 * @author pfrank
 */
@Configuration
public class AppConfig {
  private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);
  private final RestTemplate priceClientRestTemplate;
  private final TestEntityManager testEntityManager;

  @Autowired
  public AppConfig(final TestEntityManager testEntityManager, @Qualifier("priceClientRestTemplate") final RestTemplate priceClientRestTemplate){
    this.priceClientRestTemplate = priceClientRestTemplate;
    this.testEntityManager = testEntityManager;
  }

  @Bean
  public MockRestServiceServer mockRestServiceServer(){
    final MockServerRestTemplateCustomizer mockServerRestTemplateCustomizer = new MockServerRestTemplateCustomizer();
    mockServerRestTemplateCustomizer.customize(priceClientRestTemplate);
    final MockRestServiceServer mockRestServiceServer = mockServerRestTemplateCustomizer.getServer(priceClientRestTemplate);
    return mockRestServiceServer;
  }

  @Bean
  public PersistenceBootstrapClient persistenceBootstrapClient(){
    return new PersistenceBootstrapClient(testEntityManager);
  }
}
