package com.yohanmarcus.webshop.order.service;

import com.yohanmarcus.webshop.order.domain.Order;
import com.yohanmarcus.webshop.order.dto.OrderDto;

public class OrderMapper {
  public static OrderDto toDto(Order order) {
    return OrderDto.from(order.getId(), order.getUserId(), order.getStatus());
  }

  public static Order toEntity(OrderDto dto) {
    return Order.of(dto.getId(), dto.getUserId(), dto.getStatus());
  }
}
