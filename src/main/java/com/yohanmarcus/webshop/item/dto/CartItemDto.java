package com.yohanmarcus.webshop.item.dto;

import com.yohanmarcus.webshop.item.domain.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CartItemDto {
  private Integer itemId;
  private String name;
  private Integer price;
  private Integer amount;
  private String description;
  private String category;

  public Integer getTotal() {
    return price * amount;
  }

  public void increment() {
    amount++;
  }

  private void decrement() {
    amount--;
  }

  public static CartItemDto toCartItem(Item item) {
    return CartItemDto.builder()
        .itemId(item.getId())
        .name(item.getName())
        .amount(1)
        .description(item.getDescription())
        .category(item.getCategory())
        .build();
  }
}
