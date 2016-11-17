package com.github.pfrank13.presentation.boot;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

import com.github.pfrank13.presentation.boot.annotation.MyCustomCompsiteAnnotation;
import com.github.pfrank13.presentation.boot.client.PersistenceDbClient;
import com.github.pfrank13.presentation.boot.client.impl.RestOperationsPriceClient;
import com.github.pfrank13.presentation.boot.dto.CategoryDto;
import com.github.pfrank13.presentation.boot.dto.ItemDto;
import com.github.pfrank13.presentation.boot.model.AbstractEntity;
import com.github.pfrank13.presentation.boot.model.Category;
import com.github.pfrank13.presentation.boot.model.Item;
import com.github.pfrank13.presentation.boot.repository.CategoryRepository;
import com.github.pfrank13.presentation.boot.service.CategoryService;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transaction;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author pfrank
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@AutoConfigureWebClient
@Transactional
//@MyCustomCompsiteAnnotation
public class ApplicationTest {
  @Autowired
  private TestRestTemplate testRestTemplate;

  @PersistenceContext(name = "persistenceDb")
  private EntityManager entityManager;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private MockRestServiceServer mockRestServiceServer;

  @Test
  public void loadCategoryById() throws IOException{
    //GIVEN
    final Category category = persistCategory();
    final List<Item> persistedItems = persistItems(category);

    final String priceResponseTemplate = StreamUtils.copyToString(new ClassPathResource("/json/PriceResponseTemplate.json").getInputStream(),
        StandardCharsets.UTF_8);
    int counter = 1;
    String priceResponse;
    for(Item persistedItem : persistedItems) {
      priceResponse = String.format(priceResponseTemplate, category.getId(), counter + ".23");
      this.mockRestServiceServer.expect(requestTo("/item/" + persistedItem.getId().toString() + "/price"))
                                .andRespond(withSuccess(priceResponse, MediaType.APPLICATION_JSON));
    }

    //categoryRepository.findOne(1);
    //categoryService.loadCategoryById(1).get();

    //WHEN
    final CategoryDto categoryDto = testRestTemplate.getForObject("/category/1", CategoryDto.class);

    //THEN
    Assertions.assertThat(categoryDto).isEqualToComparingOnlyGivenFields(category, "id", "name");
    final List<ItemDto> itemDtos = categoryDto.getItems();
    Assertions.assertThat(itemDtos).hasSize(persistedItems.size());
    final Map<Integer, ItemDto> itemDtoMap = FluentIterable.from(itemDtos).uniqueIndex(ItemDto::getId);
    ItemDto itemDto;
    for(Item persistedItem : persistedItems){
      itemDto = itemDtoMap.get(persistedItem.getId());
      Assertions.assertThat(persistedItem).isEqualToComparingOnlyGivenFields(itemDto, "id", "name");
      Assertions.assertThat(itemDto.getPrice()).isNotNull();
    }
  }

  List<Item> persistItems(final Category category){
    int itemCount = 4;
    Item item;
    final ImmutableList.Builder<Item> itemsBuilder = ImmutableList.builder();
    for(int counter = 0; counter < itemCount; counter++){
      item = createItemForCategory(category);
      entityManager.persist(item);
      itemsBuilder.add(item);
    }

    return itemsBuilder.build();
  }

  Category persistCategory(){
    final Category category = new Category();
    category.setName("category");
    AbstractEntity.setDates(category);

    entityManager.persist(category);

    return category;
  }

  Item createItemForCategory(final Category category){
    final long currentTimeMillis = System.currentTimeMillis();
    final Item item = new Item();
    AbstractEntity.setDates(item);
    item.setName("itemName" + currentTimeMillis);

    item.setCategory(category);

    return item;
  }
}
