package com.github.pfrank13.presentation.boot.client.impl;

import com.google.common.collect.ImmutableList;

import com.github.pfrank13.presentation.boot.client.PersistenceDbClient;
import com.github.pfrank13.presentation.boot.model.Category;
import com.github.pfrank13.presentation.boot.model.Item;
import com.github.pfrank13.presentation.boot.repository.CategoryRepository;
import com.github.pfrank13.presentation.boot.repository.ItemRepository;

import org.springframework.util.Assert;

import java.util.List;

/**
 * @author pfrank
 */
public class PersistenceDbClientImpl implements PersistenceDbClient {
  private final CategoryRepository categoryRepository;
  private final ItemRepository itemRepository;

  public PersistenceDbClientImpl(
      final CategoryRepository categoryRepository,
      final ItemRepository itemRepository) {
    this.categoryRepository = categoryRepository;
    this.itemRepository = itemRepository;
    afterPropertiesSet();
  }

  private void afterPropertiesSet(){
    Assert.notNull(categoryRepository, "CategoryRepository cannot be null");
    Assert.notNull(itemRepository, "ItemRepository cannot be null");
  }

  @Override
  public Category loadCategoryById(final long id) {
    final Category category = categoryRepository.findOne(id);
    category.setItems(findItemsByCategoryId(id));

    return category;
  }

  @Override
  public List<Item> findItemsByCategoryId(final long id) {
    return ImmutableList.copyOf(itemRepository.findAll());
  }
}
