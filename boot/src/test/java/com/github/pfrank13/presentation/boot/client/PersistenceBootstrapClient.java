package com.github.pfrank13.presentation.boot.client;

import com.github.pfrank13.presentation.boot.model.Category;
import com.github.pfrank13.presentation.boot.model.Item;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pfrank
 */
@Transactional
public class PersistenceBootstrapClient {
  private TestEntityManager testEntityManager;

  public PersistenceBootstrapClient(final TestEntityManager testEntityManager){
    this.testEntityManager = testEntityManager;
  }

  public Category persistCategory(final Category category){
    return testEntityManager.persistAndFlush(category);
  }

  public List<Item> persistItemsForCategory(final List<Item> items){
    items.stream().forEach(i -> testEntityManager.persistAndFlush(i));

    return items;
  }
}
