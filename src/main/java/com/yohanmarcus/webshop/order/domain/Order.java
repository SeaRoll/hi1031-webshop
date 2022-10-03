package com.yohanmarcus.webshop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Order {
  private String id;
  private String userId;
  private OrderStatus status;
}
