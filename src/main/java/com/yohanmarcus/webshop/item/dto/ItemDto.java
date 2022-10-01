package com.yohanmarcus.webshop.item.dto;

import com.yohanmarcus.webshop.item.domain.Item;

public record ItemDto(
        Integer id,
        String name,
        Integer price,
        Integer quantity,
        String description,
        String category
) {
    public static ItemDto toDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getQuantity(), item.getDescription(), item.getCategory());
    }
}
