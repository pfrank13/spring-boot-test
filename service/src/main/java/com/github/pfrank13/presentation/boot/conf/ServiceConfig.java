package com.github.pfrank13.presentation.boot.conf;

import com.github.pfrank13.presentation.boot.client.PersistenceDbClient;
import com.github.pfrank13.presentation.boot.client.PriceClient;
import com.github.pfrank13.presentation.boot.service.CategoryService;
import com.github.pfrank13.presentation.boot.service.impl.CategoryServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author pfrank
 */
@Configuration
public class ServiceConfig {
  private final PersistenceDbClient persistenceDbClient;
  private final PriceClient priceClient;

  @Autowired
  public ServiceConfig(
      final PersistenceDbClient persistenceDbClient,
      final PriceClient priceClient) {
    this.persistenceDbClient = persistenceDbClient;
    this.priceClient = priceClient;
    afterPropertiesSet();
  }

  private void afterPropertiesSet(){
    Assert.notNull(persistenceDbClient, "PersistenceDbClient persistenceDbClient cannot be null.");
    Assert.notNull(priceClient, "PriceClient cannot be null.");
  }

  @Bean
  public CategoryService categoryService(){
    return new CategoryServiceImpl(persistenceDbClient, priceClient);
  }
}
