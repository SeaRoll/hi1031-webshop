package com.yohanmarcus.webshop.item.dto;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "from")
public class CartDto {
  List<ItemDto> items;

  /**
   * Get total price inside the cart
   *
   * @return items
   */
  public Integer getTotal() {
    return items.stream().mapToInt(i -> i.getQuantity() * i.getPrice()).sum();
  }
}
