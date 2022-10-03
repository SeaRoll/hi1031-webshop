package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class ItemServiceImpl implements ItemService {
  private final ItemDao itemDao;

  @Inject
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
