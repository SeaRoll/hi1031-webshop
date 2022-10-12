package com.yohanmarcus.webshop.order.dto;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "from")
public class OrderWithItems {
  OrderDto order;
  List<OrderItemsDto> items;

  public Integer getTotal() {
    return items.stream().mapToInt(i -> i.getPrice() * i.getQuantity()).sum();
  }
}
