package com.github.pfrank13.presentation.boot.client.impl;

import com.github.pfrank13.presentation.boot.client.PriceClient;
import com.github.pfrank13.presentation.boot.client.dto.PriceResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author pfrank
 */
public class RestOperationsPriceClient implements PriceClient{
  private static final Logger LOG = LoggerFactory.getLogger(RestOperationsPriceClient.class);
  private final RestOperations restOperations;

  public RestOperationsPriceClient(final RestOperations restOperations) {
    this.restOperations =restOperations;
    afterPropertiesSet();
  }

  private void afterPropertiesSet(){
    Assert.notNull(restOperations, "RestOperations cannot be null");
  }

  @Override
  public PriceResponse findPriceByItemId(final int itemId) {
    final String uriString = UriComponentsBuilder.newInstance().pathSegment("item", Integer.toString(itemId), "price").build().toUriString();
    return restOperations.getForObject(uriString, PriceResponse.class);
  }
}
