package com.github.pfrank13.presentation.boot.web;

import com.github.pfrank13.presentation.boot.dto.CategoryDto;
import com.github.pfrank13.presentation.boot.service.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pfrank
 */
@RestController
public class CategoryController {
  private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
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
  public CategoryDto loadCategoryById(@PathVariable("id") final int id){
    LOG.debug("Controller Going to load category id=" + id);
    return categoryService.loadCategoryById(id).orElse(null);
  }
}
