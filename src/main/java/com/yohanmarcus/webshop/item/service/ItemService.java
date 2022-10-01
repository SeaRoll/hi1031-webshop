package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.domain.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {
  List<Item> findAll() throws SQLException;

  Item findById(Integer id) throws SQLException;
}
