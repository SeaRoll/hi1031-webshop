package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.item.dto.ItemDto;

public class ItemMapper {
  public static ItemDto toDto(Item item) {
    return ItemDto.from(
        item.getId(),
        item.getName(),
        item.getPrice(),
        item.getQuantity(),
        item.getDescription(),
        item.getCategory());
  }

  public static Item toEntity(ItemDto itemDto) {
    return Item.of(
        itemDto.getId(),
        itemDto.getName(),
        itemDto.getPrice(),
        itemDto.getQuantity(),
        itemDto.getDescription(),
        itemDto.getCategory());
  }
}
