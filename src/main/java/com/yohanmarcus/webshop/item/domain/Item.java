package com.yohanmarcus.webshop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
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
