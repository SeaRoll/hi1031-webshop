package com.yohanmarcus.webshop.order.dto;

import lombok.Value;

@Value(staticConstructor = "from")
public class OrderItemsDto {
  String id;
  String orderId;
  String name;
  Integer price;
  Integer quantity;
  String description;
  String category;
}
