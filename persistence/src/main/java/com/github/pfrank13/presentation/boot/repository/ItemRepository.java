package com.github.pfrank13.presentation.boot.repository;

import com.github.pfrank13.presentation.boot.model.Item;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author pfrank
 */
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
}
