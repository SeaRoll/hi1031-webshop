package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface CartService {
  List<ItemDto> addToCart(Integer clickedId, List<ItemDto> currentList) throws SQLException;

  List<ItemDto> removeFromCart(Integer clickedId, List<ItemDto> currentList);
}
