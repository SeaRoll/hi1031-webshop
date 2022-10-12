package com.yohanmarcus.webshop.item.service;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.item.dto.ItemDto;

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
  public List<ItemDto> findAll() throws SQLException {
    return itemDao.findAll(null).stream().map(ItemMapper::toDto).toList();
  }

  @Override
  public ItemDto findById(String id) throws SQLException {
    Item item =
        itemDao.findById(id, null).orElseThrow(() -> new IllegalStateException("Does not exist!"));
    return ItemMapper.toDto(item);
  }

  @Override
  public void removeById(String id) throws SQLException {
    itemDao.removeById(id, null);
  }

  @Override
  public void create(ItemDto item) throws SQLException {
    itemDao.create(ItemMapper.toEntity(item), null);
  }

  @Override
  public void update(ItemDto item) throws SQLException {
    itemDao.update(ItemMapper.toEntity(item), null);
  }
}
