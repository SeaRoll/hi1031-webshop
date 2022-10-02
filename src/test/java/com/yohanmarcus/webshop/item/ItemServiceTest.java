package com.yohanmarcus.webshop.item;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.item.service.ItemService;
import com.yohanmarcus.webshop.item.service.ItemServiceImpl;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemServiceTest {
  private final ItemDao mockDao = mock(ItemDao.class);
  private final ItemService itemService = new ItemServiceImpl(mockDao);

  @Test
  void testFindAll_givesCorrectList() throws SQLException {
    when(mockDao.findAll())
        .thenReturn(
            List.of(Item.of("1", "test", 2, 3, "", ""), Item.of("2", "tes2", 2, 3, "", "")));
    List<Item> items = itemService.findAll();
    assertEquals(2, items.size());
  }

  @Test
  void testFindOne_givesOnCorrectParameter() throws SQLException {
    Item returningItem = Item.of("1", "test", 2, 3, "", "");
    when(mockDao.findById(eq("1"))).thenReturn(Optional.of(returningItem));
    Item item = itemService.findById("1");
    assertEquals(returningItem, item);
  }

  @Test
  void testFindOne_givesErrorOnWrongParameter() throws SQLException {
    when(mockDao.findById(eq("1"))).thenReturn(Optional.empty());
    assertThrows(IllegalStateException.class, () -> itemService.findById("1"));
  }
}
