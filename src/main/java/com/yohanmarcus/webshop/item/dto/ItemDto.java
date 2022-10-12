package com.yohanmarcus.webshop.item.dto;

import lombok.Value;

@Value(staticConstructor = "from")
public class ItemDto {
  String id;
  String name;
  Integer price;
  Integer quantity;
  String description;
  String category;
}
