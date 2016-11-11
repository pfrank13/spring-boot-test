package com.github.pfrank13.presentation.boot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author pfrank
 */
@Entity
public class Item extends AbstractEntity<Integer>{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @Override
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
