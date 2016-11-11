package com.github.pfrank13.presentation.boot.client;

import com.github.pfrank13.presentation.boot.model.Category;
import com.github.pfrank13.presentation.boot.model.Item;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

/**
 * @author pfrank
 */
@Transactional
public interface PersistenceDbClient {
  Category persistCategory(final Category category);
  Optional<Category> loadCategoryById(final int id);
  List<Item> findItemsByCategoryId(final int id);
}
