package com.github.pfrank13.presentation.boot.conf;

import com.github.pfrank13.presentation.boot.client.PersistenceDbClient;
import com.github.pfrank13.presentation.boot.client.impl.PersistenceDbClientImpl;
import com.github.pfrank13.presentation.boot.repository.CategoryRepository;
import com.github.pfrank13.presentation.boot.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * @author pfrank
 */
@Configuration
public class PersistenceDbClientConfig {
  private final CategoryRepository categoryRepository;
  private final ItemRepository itemRepository;

  @Autowired
  public PersistenceDbClientConfig(
      final CategoryRepository categoryRepository,
      final ItemRepository itemRepository) {
    this.categoryRepository = categoryRepository;
    this.itemRepository = itemRepository;
    afterPropertiesSet();
  }

  private void afterPropertiesSet(){
    Assert.notNull(categoryRepository, "CategoryRepository cannot be null.");
    Assert.notNull(itemRepository, "ItemRepository cannot be null.");
  }

  @Bean
  public PersistenceDbClient persistenceDbClient(){
    return new PersistenceDbClientImpl(categoryRepository, itemRepository);
  }
}
