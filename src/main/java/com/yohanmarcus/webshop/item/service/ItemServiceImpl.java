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
    return itemDao.findAll(null);
  }

  @Override
  public Item findById(String id) throws SQLException {
    return itemDao
        .findById(id, null)
        .orElseThrow(() -> new IllegalStateException("Does not exist!"));
  }

  @Override
  public void removeById(String id) throws SQLException {
    itemDao.removeById(id, null);
  }

  @Override
  public void create(Item item) throws SQLException {
    itemDao.create(item, null);
  }

  @Override
  public void update(Item item) throws SQLException {
    itemDao.update(item, null);
  }
}
