package com.github.pfrank13.presentation.boot.client;

import com.github.pfrank13.presentation.boot.model.Category;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pfrank
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PersistenceDbClientTest {
  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private PersistenceDbClient persistenceDbClient;

  @Test
  public void loadCategoryById() {
    //GIVEN
    final Category parentCategory = new Category();
    parentCategory.setName("parentCategory");

    final Category category = new Category();
    category.setName("category");
    category.setParent(parentCategory);

    entityManager.persist(parentCategory);
    entityManager.persist(category);

    //WHEN
    final Category found = persistenceDbClient.loadCategoryById(category.getId());

    //THEN
    Assertions.assertThat(found).isEqualTo(category);
    Assertions.assertThat(found).isNotSameAs(category);
  }
}