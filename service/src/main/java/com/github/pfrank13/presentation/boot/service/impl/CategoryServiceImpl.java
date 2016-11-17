package com.github.pfrank13.presentation.boot.service.impl;

import com.github.pfrank13.presentation.boot.client.PersistenceDbClient;
import com.github.pfrank13.presentation.boot.client.PriceClient;
import com.github.pfrank13.presentation.boot.client.dto.PriceResponse;
import com.github.pfrank13.presentation.boot.dto.CategoryDto;
import com.github.pfrank13.presentation.boot.dto.ItemDto;
import com.github.pfrank13.presentation.boot.model.Category;
import com.github.pfrank13.presentation.boot.model.Item;
import com.github.pfrank13.presentation.boot.service.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author pfrank
 */
public class CategoryServiceImpl implements CategoryService{
  private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);
  private final PersistenceDbClient persistenceDbClient;
  private final PriceClient priceClient;

  public CategoryServiceImpl(
      final PersistenceDbClient persistenceDbClient,
      final PriceClient priceClient) {
    this.persistenceDbClient = persistenceDbClient;
    this.priceClient = priceClient;
    afterPropertiesSet();
  }

  private void afterPropertiesSet(){
    Assert.notNull(persistenceDbClient, "PersistenceDbClient cannot be null.");
    Assert.notNull(priceClient, "PriceClient cannot be null.");
  }

  @Override
  public Optional<CategoryDto> loadCategoryById(final int categoryId) {
    final Optional<Category> categoryOptional = persistenceDbClient.loadCategoryById(categoryId);
    final Optional<CategoryDto> retVal;
    if(categoryOptional.isPresent()){
      LOG.debug("Category with id={} was found", categoryId);
      final Category category = categoryOptional.get();
      retVal = Optional.of(transformCategory(category));
    }else{
      LOG.debug("Category with id={} was NOT found returning empty", categoryId);
      retVal = Optional.empty();
    }

    return retVal;
  }

  CategoryDto transformCategory(final Category category){
    final CategoryDto categoryDto = transformToCategoryDto(category);
    final List<ItemDto> itemDtos;
    if(!CollectionUtils.isEmpty(category.getItems())) {
      itemDtos = category.getItems().stream().map(this::transformItem).collect(Collectors.toList());
      PriceResponse priceResponse;
      for(ItemDto itemDto : itemDtos){
        priceResponse = priceClient.findPriceByItemId(itemDto.getId());
        mapPrice(priceResponse, itemDto);
      }
    }else{
      itemDtos = Collections.emptyList();
    }

    categoryDto.setItems(itemDtos);

    return categoryDto;
  }

  ItemDto transformItem(final Item item){
    final ItemDto itemDto = new ItemDto();
    itemDto.setName(item.getName());
    itemDto.setId(item.getId());

    return itemDto;
  }

  CategoryDto transformToCategoryDto(final Category category){
    final CategoryDto categoryDto = new CategoryDto();
    categoryDto.setId(category.getId());
    categoryDto.setName(category.getName());

    return categoryDto;
  }

  void mapPrice(final PriceResponse priceResponse, final ItemDto itemDto){
    itemDto.setPrice(priceResponse.getPrice());
  }
}
