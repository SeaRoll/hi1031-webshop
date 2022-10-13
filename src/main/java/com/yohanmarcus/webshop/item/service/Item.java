package com.yohanmarcus.webshop.item.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of", access = AccessLevel.PROTECTED)
public class Item {
  private String id;
  private String name;
  private Integer price;
  private Integer quantity;
  private String description;
  private String category;

  /** Increase quantity */
  public void decreaseQuantity() {
    quantity--;
  }

  /** Decrease quantity */
  public void increaseQuantity() {
    quantity++;
  }
}
