package com.github.pfrank13.presentation.boot.client;

import com.github.pfrank13.presentation.boot.model.AbstractEntity;
import com.github.pfrank13.presentation.boot.model.Category;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author pfrank
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations="classpath:test.properties")
public class PersistenceDbClientTest {
  @PersistenceContext(name = "persistenceDb")
  private EntityManager entityManager;
  @Autowired
  private PersistenceDbClient persistenceDbClient;

  @Test
  public void loadCategoryById() {
    //GIVEN
    final Category parentCategory = new Category();
    parentCategory.setName("parentCategory");
    AbstractEntity.setDates(parentCategory);

    final Category category = new Category();
    category.setName("category");
    category.setParent(parentCategory);
    AbstractEntity.setDates(category);

    entityManager.persist(parentCategory);
    entityManager.persist(category);

    //WHEN
    final Optional<Category> found = persistenceDbClient.loadCategoryById(category.getId());

    //THEN
    Assertions.assertThat(found.isPresent()).isTrue();
    Assertions.assertThat(found.get()).isEqualTo(category);
    Assertions.assertThat(found.get()).isSameAs(category);
  }
}