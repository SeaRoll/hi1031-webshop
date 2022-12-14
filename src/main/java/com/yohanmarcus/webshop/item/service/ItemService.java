package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {

  /**
   * Finds all items
   *
   * @return list of all items
   * @throws SQLException sql error
   */
  List<ItemDto> findAll() throws SQLException;

  /**
   * Finds an item by id
   *
   * @param id item id
   * @return found item
   * @throws SQLException sql error
   */
  ItemDto findById(String id) throws SQLException;

  /**
   * Removes an object by id
   *
   * @param id item id
   * @throws SQLException sql error
   */
  void removeById(String id) throws SQLException;

  /**
   * Creates an item
   *
   * @param item item to create (id can be null)
   * @throws SQLException sql error
   */
  void create(ItemDto item) throws SQLException;

  /**
   * Updates an item
   *
   * @param item item to update (id needs to exist)
   * @throws SQLException sql error
   */
  void update(ItemDto item) throws SQLException;
}
