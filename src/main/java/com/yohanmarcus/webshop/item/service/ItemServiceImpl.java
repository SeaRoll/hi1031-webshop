package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService {
  private final ItemDao itemDao;

  public ItemServiceImpl(ItemDao itemDao) {
    this.itemDao = itemDao;
  }

  @Override
  public List<ItemDto> findAll() throws SQLException {
    return itemDao.findAll().stream().map(ItemDto::toDto).toList();
  }

  @Override
  public ItemDto findById(Integer id) throws SQLException {
    return ItemDto.toDto(
        itemDao.findById(id).orElseThrow(() -> new IllegalStateException("Does not exist!")));
  }
}
