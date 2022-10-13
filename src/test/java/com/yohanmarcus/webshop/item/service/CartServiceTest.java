package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.dto.CartDto;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartServiceTest {
  private final ItemDao mockDao = mock(ItemDao.class);
  private final CartService cartService = new CartServiceImpl(mockDao);

  @Test
  void addToCart_returnsDto() throws SQLException {
    CartDto cart = CartDto.from(new ArrayList<>());
    when(mockDao.findById(eq("1"), eq(null)))
        .thenReturn(Optional.of(Item.of("1", "a", 2, 2, "", "")));
    CartDto cartFromService = cartService.addToCart("1", cart);
    assertEquals(1, cartFromService.getItems().size());
  }

  @Test
  void removeFromCart_returnsSame() throws SQLException {
    CartDto cart = CartDto.from(new ArrayList<>());
    when(mockDao.findById(eq("1"), eq(null)))
        .thenReturn(Optional.of(Item.of("1", "a", 2, 2, "", "")));
    CartDto cartFromService = cartService.removeFromCart("1", cart);
    assertEquals(cart, cartFromService);
  }
}
