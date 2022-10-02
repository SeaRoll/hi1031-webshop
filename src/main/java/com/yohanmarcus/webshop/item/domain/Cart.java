package com.yohanmarcus.webshop.item.domain;

import java.util.HashMap;
import java.util.List;

public class Cart {
  private final HashMap<String, Item> cartItems;

  public Cart() {
    cartItems = new HashMap<>();
  }

  public void addToCart(Item item) {
    Item existingItem = cartItems.get(item.getId());
    if (existingItem == null) {
      cartItems.put(
          item.getId(),
          Item.of(
              item.getId(),
              item.getName(),
              item.getPrice(),
              1,
              item.getDescription(),
              item.getCategory()));
      return;
    }
    existingItem.increaseQuantity();
    cartItems.put(item.getId(), existingItem);
  }

  public void removeFromCart(Item item) {
    Item existingItem = cartItems.get(item.getId());

    if (existingItem == null) return;

    existingItem.decreaseQuantity();
    if (existingItem.getQuantity() < 1) {
      cartItems.remove(item.getId());
      return;
    }
    cartItems.put(item.getId(), existingItem);
  }

  public List<Item> getCartItems() {
    return cartItems.values().stream().toList();
  }

  public Integer getTotal() {
    return getCartItems().stream().mapToInt(i -> i.getQuantity() * i.getPrice()).sum();
  }
}
