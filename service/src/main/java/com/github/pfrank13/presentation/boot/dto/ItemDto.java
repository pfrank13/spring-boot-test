package com.github.pfrank13.presentation.boot.dto;

import javax.money.MonetaryAmount;

/**
 * @author pfrank
 */
public class ItemDto {
  private int id;
  private String name;
  private MonetaryAmount price;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MonetaryAmount getPrice() {
    return price;
  }

  public void setPrice(MonetaryAmount price) {
    this.price = price;
  }
}
