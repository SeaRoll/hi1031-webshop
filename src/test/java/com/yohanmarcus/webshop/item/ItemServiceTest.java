package com.yohanmarcus.webshop.item;

import com.yohanmarcus.webshop.item.dao.ItemDao;
import com.yohanmarcus.webshop.item.domain.Item;
import com.yohanmarcus.webshop.item.dto.ItemDto;
import com.yohanmarcus.webshop.item.service.ItemService;
import com.yohanmarcus.webshop.item.service.ItemServiceImpl;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemServiceTest {
  private final ItemDao mockDao = mock(ItemDao.class);
  private final ItemService itemService = new ItemServiceImpl(mockDao);

  @Test
  void testFindAll_givesCorrectList() throws SQLException {
    when(mockDao.findAll(null))
        .thenReturn(
            List.of(Item.of("1", "test", 2, 3, "", ""), Item.of("2", "tes2", 2, 3, "", "")));
    List<ItemDto> items = itemService.findAll();
    assertEquals(2, items.size());
  }

  @Test
  void testFindOne_givesOnCorrectParameter() throws SQLException {
    Item returningItem = Item.of("1", "test", 2, 3, "", "");
    ItemDto expectingItem =
        ItemDto.from(
            returningItem.getId(),
            returningItem.getName(),
            returningItem.getPrice(),
            returningItem.getQuantity(),
            returningItem.getDescription(),
            returningItem.getCategory());
    when(mockDao.findById(eq("1"), eq(null))).thenReturn(Optional.of(returningItem));
    ItemDto item = itemService.findById("1");
    assertEquals(expectingItem, item);
  }

  @Test
  void testFindOne_givesErrorOnWrongParameter() throws SQLException {
    when(mockDao.findById(eq("1"), eq(null))).thenReturn(Optional.empty());
    assertThrows(IllegalStateException.class, () -> itemService.findById("1"));
  }

  @Test
  void testSaveCallsDao() throws SQLException {
    ItemDto save = ItemDto.from("1", "test", 2, 3, "", "");
    itemService.create(save);
    verify(mockDao).create(any(), any());
  }

  @Test
  void testUpdateCallsDao() throws SQLException {
    ItemDto save = ItemDto.from("1", "test", 2, 3, "", "");
    itemService.update(save);
    verify(mockDao).update(any(), any());
  }

  @Test
  void testRemoveByIdCallsDao() throws SQLException {
    itemService.removeById("2");
    verify(mockDao).removeById(eq("2"), any());
  }
}
