package com.github.pfrank13.presentation.boot.client.impl;

import com.google.common.net.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pfrank13.presentation.boot.client.PriceClient;
import com.github.pfrank13.presentation.boot.client.dto.PriceResponse;
import com.github.pfrank13.presentation.boot.conf.PriceClientConfig;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.zalando.jackson.datatype.money.MoneyModule;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import javax.money.MonetaryAmount;

/**
 * @author pfrank
 */
public class RestOperationsPriceClientTest {
  @ClassRule
  public static WireMockClassRule wireMockRule = new WireMockClassRule(0);

  @Rule
  public WireMockClassRule instanceRule = wireMockRule;

  private PriceClient priceClient;

  @Before
  public void setUp() {
    final PriceClientConfig priceClientConfig = new PriceClientConfig("http://localhost:" + instanceRule.port());
    priceClient = priceClientConfig.priceClient();
  }

  @Test
  public void findPriceByItemId() throws IOException{
    //GIVEN
    final MappingBuilder mappingBuilder = WireMock.get(WireMock.urlEqualTo("/item/1/price"));

    final ResponseDefinitionBuilder responseDefinitionBuilder = WireMock.aResponse().withStatus(200);
    responseDefinitionBuilder.withBody(StreamUtils.copyToByteArray(new ClassPathResource("/json/PriceResponse.json").getInputStream()));
    responseDefinitionBuilder.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    mappingBuilder.willReturn(responseDefinitionBuilder);

    WireMock.stubFor(mappingBuilder);

    //WHEN
    final PriceResponse retVal = priceClient.findPriceByItemId(1);

    //THEN
    Assertions.assertThat(retVal.getItemId()).isEqualTo("1");
    final MonetaryAmount monetaryAmount = retVal.getPrice();
    Assertions.assertThat(monetaryAmount).isNotNull();
    Assertions.assertThat(monetaryAmount.getCurrency().getCurrencyCode()).isEqualTo("USD");
    final BigDecimal bigDecimal = new BigDecimal("12.34");
    Assertions.assertThat(monetaryAmount.getNumber().numberValue(BigDecimal.class)).isEqualTo(bigDecimal);

  }

  @Test
  public void deserializeWithMoney() throws IOException{
    //GIVEN
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new MoneyModule());
    final String priceResponseString = new String(StreamUtils.copyToByteArray(new ClassPathResource("/json/PriceResponse.json").getInputStream()),
        StandardCharsets.UTF_8);

    //WHEN
    final PriceResponse priceResponse = objectMapper.readValue(priceResponseString, PriceResponse.class);

    //THEN
    Assertions.assertThat(priceResponse.getItemId()).isEqualTo("1");
    final MonetaryAmount monetaryAmount = priceResponse.getPrice();
    Assertions.assertThat(monetaryAmount).isNotNull();
    Assertions.assertThat(monetaryAmount.getCurrency().getCurrencyCode()).isEqualTo("USD");
    final BigDecimal bigDecimal = new BigDecimal("12.34");
    Assertions.assertThat(monetaryAmount.getNumber().numberValue(BigDecimal.class)).isEqualTo(bigDecimal);
  }
}