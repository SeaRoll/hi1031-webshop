package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.domain.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {
  List<Item> findAll() throws SQLException;

  Item findById(String id) throws SQLException;

  void removeById(String id) throws SQLException;

  void create(Item item) throws SQLException;

  void update(Item item) throws SQLException;
}
