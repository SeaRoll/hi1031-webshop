package com.yohanmarcus.webshop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class OrderItemsId {
  private String orderId;
  private String itemId;
}
