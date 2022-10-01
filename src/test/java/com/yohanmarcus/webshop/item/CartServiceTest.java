package com.yohanmarcus.webshop.item;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Cart;
import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.item.service.CartService;
import com.yohanmarcus.webshop.item.service.CartServiceImpl;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartServiceTest {
  private final ItemDao mockDao = mock(ItemDao.class);
  private final CartService cartService = new CartServiceImpl(mockDao);

  @Test
  void addToCart_returnsSame() throws SQLException {
    Cart cart = new Cart();
    when(mockDao.findById(eq(1))).thenReturn(Optional.of(Item.of(1, "a", 2, 2, "", "")));
    Cart cartFromService = cartService.addToCart(1, cart);
    assertEquals(cart, cartFromService);
  }

  @Test
  void removeFromCart_returnsSame() throws SQLException {
    Cart cart = new Cart();
    when(mockDao.findById(eq(1))).thenReturn(Optional.of(Item.of(1, "a", 2, 2, "", "")));
    Cart cartFromService = cartService.removeFromCart(1, cart);
    assertEquals(cart, cartFromService);
  }
}
