package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {
  List<ItemDto> findAll() throws SQLException;

  ItemDto findById(Integer id) throws SQLException;
}
