package com.github.pfrank13.presentation.boot.client;

import com.github.pfrank13.presentation.boot.client.dto.PriceResponse;
import com.github.pfrank13.presentation.boot.client.impl.RestOperationsPriceClient;
import com.github.pfrank13.presentation.boot.conf.PriceClientConfig;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.sql.DataSource;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

/**
 * @author pfrank
 */
@RunWith(SpringRunner.class)
@RestClientTest(RestOperationsPriceClient.class)
@TestPropertySource(locations="classpath:test.properties")
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {DataSource.class})})
public class PriceClientTest {
  @Autowired
  private PriceClient priceClient;

  @Autowired
  private MockRestServiceServer server;

  @Test
  public void findPriceByItemId(){
    //GIVEN
    this.server.expect(requestTo("/item/1/price"))
               .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(new ClassPathResource("/json/PriceResponse.json")));

    //WHEN
    final PriceResponse retVal = priceClient.findPriceByItemId("1");

    //THEN
    Assertions.assertThat(retVal.getItemId()).isEqualTo("1");
    final MonetaryAmount monetaryAmount = retVal.getPrice();
    Assertions.assertThat(monetaryAmount).isNotNull();
    Assertions.assertThat(monetaryAmount.getCurrency().getCurrencyCode()).isEqualTo("USD");
    final BigDecimal bigDecimal = new BigDecimal("12.34");
    Assertions.assertThat(monetaryAmount.getNumber().numberValue(BigDecimal.class)).isEqualTo(bigDecimal);

  }
}
