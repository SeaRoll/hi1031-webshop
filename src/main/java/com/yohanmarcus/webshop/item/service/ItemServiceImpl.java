package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService {
  private final ItemDao itemDao;

  public ItemServiceImpl(ItemDao itemDao) {
    this.itemDao = itemDao;
  }

  @Override
  public List<Item> findAll() throws SQLException {
    return itemDao.findAll();
  }

  @Override
  public Item findById(String id) throws SQLException {
    return itemDao.findById(id).orElseThrow(() -> new IllegalStateException("Does not exist!"));
  }
}
