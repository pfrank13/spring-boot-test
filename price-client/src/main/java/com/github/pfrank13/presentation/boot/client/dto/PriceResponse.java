package com.github.pfrank13.presentation.boot.client.dto;

import java.math.BigDecimal;

/**
 * @author pfrank
 */
public class PriceResponse {
  private String itemId;
  private BigDecimal price;

  public String getItemId() {
    return itemId;
  }

  public void setItemId(final String itemId) {
    this.itemId = itemId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(final BigDecimal price) {
    this.price = price;
  }
}
