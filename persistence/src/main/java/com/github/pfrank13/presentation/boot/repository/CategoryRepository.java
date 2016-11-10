package com.github.pfrank13.presentation.boot.repository;

import com.github.pfrank13.presentation.boot.model.Category;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author pfrank
 */
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>{
}
