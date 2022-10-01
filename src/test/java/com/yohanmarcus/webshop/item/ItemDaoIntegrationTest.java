package com.yohanmarcus.webshop.item;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.dao.ItemDaoImpl;
import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemDaoIntegrationTest extends IntegrationTest {

  private final ItemDao itemDao = new ItemDaoImpl();

  @BeforeEach
  void beforeEach() throws SQLException {
    itemDao.removeAll();
  }

  @Test
  void testItemDaoCreate_savesNewItem() throws SQLException {
    Item item = generateNewItem();
    itemDao.create(item);
    Item gotItem = itemDao.findAll().get(0);
    assertEquals(item.getName(), gotItem.getName());
    assertEquals(item.getPrice(), gotItem.getPrice());
    assertEquals(item.getQuantity(), gotItem.getQuantity());
    assertEquals(item.getDescription(), gotItem.getDescription());
    assertEquals(item.getCategory(), gotItem.getCategory());
  }

  @Test
  void testItemDaoUpdate_updatesExistingItem() throws SQLException {
    Item item = generateNewItem();
    itemDao.create(item);
    Item gotItem = itemDao.findAll().get(0);
    gotItem.setCategory("bruv");
    itemDao.update(gotItem);
    Item gotItem2 = itemDao.findById(gotItem.getId()).get();
    assertEquals("bruv", gotItem2.getCategory());
  }

  @Test
  void testItemDaoRemove_deletesItem() throws SQLException {
    Item item = generateNewItem();
    itemDao.create(item);
    Item gotItem = itemDao.findAll().get(0);
    itemDao.removeById(gotItem.getId());
    Optional<Item> gotItem2 = itemDao.findById(gotItem.getId());
    assertTrue(gotItem2.isEmpty());
  }

  private Item generateNewItem() {
    return Item.of(null, "test-item", 10, 10, "hello", "cat");
  }
}
