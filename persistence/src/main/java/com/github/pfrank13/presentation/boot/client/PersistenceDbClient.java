package com.github.pfrank13.presentation.boot.client;

import com.github.pfrank13.presentation.boot.model.Category;
import com.github.pfrank13.presentation.boot.model.Item;

import java.util.List;

import javax.transaction.Transactional;

/**
 * @author pfrank
 */
@Transactional
public interface PersistenceDbClient {
  Category loadCategoryById(final long id);
  List<Item> findItemsByCategoryId(final long id);
}
