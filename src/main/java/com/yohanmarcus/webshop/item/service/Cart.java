package com.yohanmarcus.webshop.item.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart {
  private final HashMap<String, Item> cartItems;

  protected Cart() {
    cartItems = new HashMap<>();
  }

  /**
   * Adds an item to cart
   *
   * @param item item to add
   */
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

  /**
   * Removes an item from cart
   *
   * @param item item to remove
   */
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

  /**
   * Get list of unique items and quantity inside the cart
   *
   * @return items
   */
  public List<Item> getCartItems() {
    return new ArrayList<>(cartItems.values());
  }

  /**
   * Get total price inside the cart
   *
   * @return items
   */
  public Integer getTotal() {
    return getCartItems().stream().mapToInt(i -> i.getQuantity() * i.getPrice()).sum();
  }
}
