package com.yohanmarcus.webshop.order.dao;

import com.yohanmarcus.webshop.order.domain.OrderItems;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.HashMap;
import java.util.Map;

/** Maps orderItems in database to java object */
public class OrderItemsHandler extends BeanListHandler<OrderItems> {
  public OrderItemsHandler() {
    super(OrderItems.class, new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));
  }

  private static Map<String, String> getColumnsToFieldsMap() {
    Map<String, String> columnsToFieldsMap = new HashMap<>();
    columnsToFieldsMap.put("id", "id");
    columnsToFieldsMap.put("order_id", "orderId");
    columnsToFieldsMap.put("name", "name");
    columnsToFieldsMap.put("price", "price");
    columnsToFieldsMap.put("quantity", "quantity");
    columnsToFieldsMap.put("description", "description");
    columnsToFieldsMap.put("category", "category");
    return columnsToFieldsMap;
  }
}
