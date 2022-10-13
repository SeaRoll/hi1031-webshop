package com.yohanmarcus.webshop.util;

import com.yohanmarcus.webshop.item.service.Cart;
import com.yohanmarcus.webshop.item.service.Item;

public class TestDomain {
  public static class TestCart extends Cart {
    public TestCart() {
      super();
    }
  }

  public static class TestItem extends Item {
    public TestItem(
        String id,
        String name,
        Integer price,
        Integer quantity,
        String description,
        String category) {
      Item.of(id, name, price, quantity, description, category);
    }

    public static TestItem of(
        String id,
        String name,
        Integer price,
        Integer quantity,
        String description,
        String category) {
      return new TestItem(id, name, price, quantity, description, category);
    }
  }
}
