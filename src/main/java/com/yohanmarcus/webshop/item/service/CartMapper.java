package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.dto.CartDto;

public class CartMapper {
  public static Cart toEntity(CartDto dto) {
    Cart newCart = new Cart();
    dto.getItems().forEach(itemDto -> newCart.addToCart(ItemMapper.toEntity(itemDto)));
    return newCart;
  }

  public static CartDto toDto(Cart cart) {
    return CartDto.from(cart.getCartItems().stream().map(ItemMapper::toDto).toList());
  }
}
