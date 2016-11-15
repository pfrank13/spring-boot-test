package com.github.pfrank13.presentation.boot.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pfrank13.presentation.boot.client.PriceClient;
import com.github.pfrank13.presentation.boot.client.dto.PriceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;
import org.zalando.jackson.datatype.money.MoneyModule;

import java.net.URI;

/**
 * @author pfrank
 */
@Service
public class RestOperationsPriceClient implements PriceClient{
  private final RestOperations restOperations;
  private final URI baseUri;

  @Autowired
  public RestOperationsPriceClient(
      @Value("${price.client.baseUri}") final String baseUrl,
      final RestTemplateBuilder restTemplateBuilder) {
    Assert.hasText(baseUrl, "String baseUrl cannot be empty.");
    baseUri = UriComponentsBuilder.fromUriString(baseUrl).build().toUri();
    final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                                                                 .modules(new MoneyModule())
                                                                 .build();
    final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
    this.restOperations = restTemplateBuilder.messageConverters(mappingJackson2HttpMessageConverter).build();
    afterPropertiesSet();
  }

  private void afterPropertiesSet(){
    Assert.notNull(restOperations, "RestOperations cannot be null");
    Assert.notNull(baseUri, "URI baseUri cannot be null");
  }

  @Override
  public PriceResponse findPriceByItemId(final String itemId) {
    final URI endpointUri = UriComponentsBuilder.fromUri(baseUri).pathSegment("item", itemId, "price").build().toUri();
    return restOperations.getForObject(endpointUri, PriceResponse.class);
  }
}
