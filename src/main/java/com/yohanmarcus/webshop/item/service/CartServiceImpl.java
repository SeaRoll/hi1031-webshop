package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.dto.CartDto;
import com.yohanmarcus.webshop.item.dto.ItemDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;

@ApplicationScoped
public class CartServiceImpl implements CartService {

  private final ItemDao itemDao;

  @Inject
  public CartServiceImpl(ItemDao itemDao) {
    this.itemDao = itemDao;
  }

  @Override
  public CartDto addToCart(String clickedId, CartDto cart) throws SQLException {
    // convert to cart
    Cart newCart = CartMapper.toEntity(cart);
    Item newItem = itemDao.findById(clickedId, null).orElseThrow();
    newCart.addToCart(newItem);

    // cast back to dto
    return CartDto.from(
        newCart.getCartItems().stream()
            .map(
                item ->
                    ItemDto.from(
                        item.getId(),
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getDescription(),
                        item.getCategory()))
            .toList());
  }

  @Override
  public CartDto removeFromCart(String clickedId, CartDto cart) throws SQLException {
    Cart newCart = CartMapper.toEntity(cart);
    Item itemToRemove = itemDao.findById(clickedId, null).orElseThrow();
    newCart.removeFromCart(itemToRemove);
    return CartDto.from(
        newCart.getCartItems().stream()
            .map(
                item ->
                    ItemDto.from(
                        item.getId(),
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getDescription(),
                        item.getCategory()))
            .toList());
  }
}
