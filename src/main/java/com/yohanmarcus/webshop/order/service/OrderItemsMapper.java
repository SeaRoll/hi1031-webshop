package com.yohanmarcus.webshop.order.service;

import com.yohanmarcus.webshop.order.domain.OrderItems;
import com.yohanmarcus.webshop.order.dto.OrderItemsDto;

public class OrderItemsMapper {
  public static OrderItemsDto toDto(OrderItems orderItems) {
    return OrderItemsDto.from(
        orderItems.getId(),
        orderItems.getOrderId(),
        orderItems.getName(),
        orderItems.getPrice(),
        orderItems.getQuantity(),
        orderItems.getDescription(),
        orderItems.getCategory());
  }

  public static OrderItems toEntity(OrderItemsDto dto) {
    return OrderItems.of(
        dto.getId(),
        dto.getOrderId(),
        dto.getName(),
        dto.getPrice(),
        dto.getQuantity(),
        dto.getDescription(),
        dto.getCategory());
  }
}
