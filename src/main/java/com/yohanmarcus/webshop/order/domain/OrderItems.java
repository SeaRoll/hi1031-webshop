package com.yohanmarcus.webshop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class OrderItems {
  private OrderItemsId id;
  private Integer quantity;
}
