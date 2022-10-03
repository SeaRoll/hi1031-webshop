package com.yohanmarcus.webshop.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
public class OrderWithItems {
  private Order order;
  private List<OrderItems> items;

  public Integer getTotal() {
    return items.stream().mapToInt(i -> i.getPrice() * i.getQuantity()).sum();
  }
}
