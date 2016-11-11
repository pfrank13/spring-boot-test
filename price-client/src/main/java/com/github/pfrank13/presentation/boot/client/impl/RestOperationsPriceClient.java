package com.github.pfrank13.presentation.boot.client.impl;

import com.github.pfrank13.presentation.boot.client.PriceClient;
import com.github.pfrank13.presentation.boot.client.dto.PriceResponse;

import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author pfrank
 */
public class RestOperationsPriceClient implements PriceClient{
  private final RestOperations restOperations;
  private final URI baseUri;

  public RestOperationsPriceClient(
      final String baseUrl,
      final RestOperations restOperations) {
    Assert.hasText(baseUrl, "String baseUrl cannot be empty.");
    baseUri = UriComponentsBuilder.fromUriString(baseUrl).build().toUri();
    this.restOperations = restOperations;
    afterPropertiesSet();
  }

  private void afterPropertiesSet(){
    Assert.notNull(restOperations, "RestOperations cannot be null");
    Assert.notNull(baseUri, "URI baseUri cannot be null");
  }

  @Override
  public PriceResponse findPriceByItemId(final String itemId) {
    final URI endpointUri = UriComponentsBuilder.fromUri(baseUri).pathSegment("price", itemId).build().toUri();
    return restOperations.getForObject(endpointUri, PriceResponse.class);
  }
}
