package com.github.pfrank13.presentation.boot.web;

import com.github.pfrank13.presentation.boot.dto.CategoryDto;
import com.github.pfrank13.presentation.boot.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * @author pfrank
 */
@RestController
public class CategoryController {
  private final CategoryService categoryService;

  @Autowired
  public CategoryController(final CategoryService categoryService){
    this.categoryService = categoryService;
    afterPropertiesSet();
  }

  private void afterPropertiesSet(){
    Assert.notNull(categoryService, "CategoryService categoryService cannot be null.");
  }

  @RequestMapping(method = {RequestMethod.GET}, path = "/category/{id}")
  public CategoryDto loadCategoryById(@PathParam("id") final int id){
    return categoryService.loadCategoryById(id).orElse(null);
  }
}
