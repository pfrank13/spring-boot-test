package com.github.pfrank13.presentation.boot.client.dto;

import javax.money.MonetaryAmount;

/**
 * @author pfrank
 */
public class PriceResponse {
  private String itemId;
  private MonetaryAmount price;

  public String getItemId() {
    return itemId;
  }

  public void setItemId(final String itemId) {
    this.itemId = itemId;
  }

  public void setPrice(MonetaryAmount price) {
    this.price = price;
  }

  public MonetaryAmount getPrice() {
    return price;
  }
}
