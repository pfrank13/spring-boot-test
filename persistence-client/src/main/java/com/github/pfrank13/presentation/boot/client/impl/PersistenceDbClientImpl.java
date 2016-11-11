package com.github.pfrank13.presentation.boot.client.impl;

import com.google.common.collect.ImmutableList;

import com.github.pfrank13.presentation.boot.client.PersistenceDbClient;
import com.github.pfrank13.presentation.boot.model.AbstractEntity;
import com.github.pfrank13.presentation.boot.model.Category;
import com.github.pfrank13.presentation.boot.model.Item;
import com.github.pfrank13.presentation.boot.repository.CategoryRepository;
import com.github.pfrank13.presentation.boot.repository.ItemRepository;

import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
  public Category persistCategory(final Category category) {
    AbstractEntity.setDates(category);
    return categoryRepository.save(category);
  }

  @Override
  public Optional<Category> loadCategoryById(final int id) {
    final Optional<Category> categoryOptional = Optional.ofNullable(categoryRepository.findOne(id));
    if(categoryOptional.isPresent()) {
      categoryOptional.get().setItems(findItemsByCategoryId(id));
    }

    return categoryOptional;
  }

  @Override
  public List<Item> findItemsByCategoryId(final int id) {
    return ImmutableList.copyOf(itemRepository.findAll());
  }
}
