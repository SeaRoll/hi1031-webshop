package com.yohanmarcus.webshop.item.dao;

import com.yohanmarcus.webshop.item.service.Item;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.HashMap;
import java.util.Map;

public class ItemHandler extends BeanListHandler<Item> {
  public ItemHandler() {
    super(Item.class, new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));
  }

  private static Map<String, String> getColumnsToFieldsMap() {
    Map<String, String> columnsToFieldsMap = new HashMap<>();
    columnsToFieldsMap.put("id", "id");
    columnsToFieldsMap.put("name", "name");
    columnsToFieldsMap.put("price", "price");
    columnsToFieldsMap.put("quantity", "quantity");
    columnsToFieldsMap.put("description", "description");
    columnsToFieldsMap.put("category", "category");
    return columnsToFieldsMap;
  }
}
