package com.yohanmarcus.webshop.item;

import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.domain.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest {

  @Test
  void testAddToCart_createsNewEntry() {
    Cart cart = new Cart();
    Item itemToInsert = Item.of("1", "test", 3, 5, "", "");
    cart.addToCart(itemToInsert);

    List<Item> itemList = cart.getCartItems();
    Item item = itemList.get(0);
    itemToInsert.setQuantity(1);
    assertEquals(itemToInsert, item);
    assertEquals(1, itemList.size());
  }

  @Test
  void testAddToCart_increasesNewEntry() {
    Cart cart = new Cart();
    Item itemToInsert = Item.of("1", "test", 3, 5, "", "");
    cart.addToCart(itemToInsert);
    cart.addToCart(itemToInsert);

    List<Item> itemList = cart.getCartItems();
    Item item = itemList.get(0);
    itemToInsert.setQuantity(2);
    assertEquals(itemToInsert, item);
    assertEquals(1, itemList.size());
  }

  @Test
  void testRemoveFromCart_decreasesExisting() {
    Cart cart = new Cart();
    Item itemToInsert = Item.of("1", "test", 3, 5, "", "");
    cart.addToCart(itemToInsert);
    cart.addToCart(itemToInsert);
    cart.removeFromCart(itemToInsert);

    List<Item> itemList = cart.getCartItems();
    Item item = itemList.get(0);
    itemToInsert.setQuantity(1);
    assertEquals(itemToInsert, item);
  }

  @Test
  void testRemoveFromCart_removesOnZero() {
    Cart cart = new Cart();
    Item itemToInsert = Item.of("1", "test", 3, 5, "", "");
    cart.addToCart(itemToInsert);
    cart.removeFromCart(itemToInsert);

    List<Item> itemList = cart.getCartItems();
    assertEquals(0, itemList.size());
  }

  @Test
  void testGetTotal_givesCorrectAmount() {
    Cart cart = new Cart();
    Item itemToInsert = Item.of("1", "test", 2, 5, "", "");
    cart.addToCart(itemToInsert);
    cart.addToCart(itemToInsert);
    cart.addToCart(itemToInsert);

    assertEquals(6, cart.getTotal());
  }
}
