package com.github.pfrank13.presentation.boot.dto;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author pfrank
 */
public class CategoryDto {
  private int id;
  private String name;
  private List<ItemDto> items;

  public CategoryDto(){
    items = Collections.emptyList();
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public List<ItemDto> getItems() {
    return items;
  }

  public void setItems(final List<ItemDto> items) {
    this.items = items;
  }

  public boolean addItem(final ItemDto itemDto){
    if(items == Collections.<ItemDto>emptyList()){
      items = new LinkedList<>();
    }
    return items.add(itemDto);
  }
}
