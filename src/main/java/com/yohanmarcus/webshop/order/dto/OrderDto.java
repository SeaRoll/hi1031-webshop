package com.yohanmarcus.webshop.order.dto;

import com.yohanmarcus.webshop.order.domain.OrderStatus;
import lombok.Value;

@Value(staticConstructor = "from")
public class OrderDto {
  String id;
  String userId;
  OrderStatus status;
}
