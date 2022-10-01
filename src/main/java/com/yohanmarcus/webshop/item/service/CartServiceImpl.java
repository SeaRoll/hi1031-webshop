package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.dto.ItemDto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartServiceImpl implements CartService {

  private final ItemDao itemDao;

  public CartServiceImpl(ItemDao itemDao) {
    this.itemDao = itemDao;
  }

  @Override
  public List<ItemDto> addToCart(Integer clickedId, List<ItemDto> currentList) throws SQLException {
    List<ItemDto> items = new ArrayList<>(currentList);

    ItemDto newItem = ItemDto.toDto(itemDao.findById(clickedId).orElseThrow());
    items.add(newItem);
    return items;
  }

  @Override
  public List<ItemDto> removeFromCart(Integer clickedId, List<ItemDto> currentList) {
    List<ItemDto> cartItems = new ArrayList<>(currentList);
    ItemDto clickedItem =
        cartItems.stream().filter(itemDto -> itemDto.id().equals(clickedId)).findFirst().get();
    cartItems.remove(clickedItem);
    return cartItems;
  }
}
