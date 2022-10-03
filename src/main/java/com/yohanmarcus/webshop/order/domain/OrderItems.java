package com.yohanmarcus.webshop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class OrderItems {
  private String id;
  private String orderId;
  private String name;
  private Integer price;
  private Integer quantity;
  private String description;
  private String category;
}
