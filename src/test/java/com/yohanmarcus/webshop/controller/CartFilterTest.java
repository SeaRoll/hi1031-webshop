package com.yohanmarcus.webshop.controller;

import com.yohanmarcus.webshop.item.domain.Cart;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartFilterTest {
  private final CartFilter cartFilter = new CartFilter();

  @Test
  void testDoFilter_createsNewCart() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute(eq("cart"))).thenReturn(null);

    cartFilter.doFilter(req, res, filterChain);

    verify(filterChain).doFilter(req, res);
    verify(session).setAttribute(eq("cart"), any());
  }

  @Test
  void testDoFilter_useExisting() throws ServletException, IOException {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    FilterChain filterChain = mock(FilterChain.class);
    HttpSession session = mock(HttpSession.class);
    Cart cart = new Cart();

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute(eq("cart"))).thenReturn(cart);

    cartFilter.doFilter(req, res, filterChain);

    verify(filterChain).doFilter(req, res);
    verify(session, times(0)).setAttribute(eq("cart"), any());
  }
}
