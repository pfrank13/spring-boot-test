package com.github.pfrank13.presentation.boot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author pfrank
 */
@Entity
public class Item extends AbstractEntity<Long>{
  @Id @GeneratedValue
  private long id;

  private String name;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
