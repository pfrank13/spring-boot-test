package com.github.pfrank13.presentation.boot;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

import com.github.pfrank13.presentation.boot.annotation.MyServiceTest;
import com.github.pfrank13.presentation.boot.client.PersistenceBootstrapClient;
import com.github.pfrank13.presentation.boot.dto.CategoryDto;
import com.github.pfrank13.presentation.boot.dto.ItemDto;
import com.github.pfrank13.presentation.boot.model.AbstractEntity;
import com.github.pfrank13.presentation.boot.model.Category;
import com.github.pfrank13.presentation.boot.model.Item;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @author pfrank
 */
@RunWith(SpringRunner.class)
@MyServiceTest
public class ApplicationTest {
  private static final Logger LOG = LoggerFactory.getLogger(ApplicationTest.class);
  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private MockRestServiceServer mockRestServiceServer;

  @Autowired
  private PersistenceBootstrapClient persistenceBootstrapClient;

  @Test
  public void loadCategoryById() throws IOException{
    //GIVEN
    LOG.info("MockRestServiceServer in test={}", mockRestServiceServer);
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

    //WHEN
    final CategoryDto categoryDto = testRestTemplate.getForObject("/category/2", CategoryDto.class);

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
      itemsBuilder.add(item);
    }

    return persistenceBootstrapClient.persistItemsForCategory(itemsBuilder.build());
  }

  Category persistCategory(){
    final Category category = new Category();
    category.setName("category");
    AbstractEntity.setDates(category);

    return persistenceBootstrapClient.persistCategory(category);
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
