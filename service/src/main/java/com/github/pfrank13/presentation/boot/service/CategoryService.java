package com.github.pfrank13.presentation.boot.service;

import com.github.pfrank13.presentation.boot.dto.CategoryDto;

import java.util.Optional;

/**
 * @author pfrank
 */
public interface CategoryService {
  Optional<CategoryDto> loadCategoryById(final int categoryId);
}
