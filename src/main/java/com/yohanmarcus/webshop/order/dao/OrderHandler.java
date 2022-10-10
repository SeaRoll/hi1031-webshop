package com.yohanmarcus.webshop.order.dao;

import com.yohanmarcus.webshop.order.domain.Order;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.HashMap;
import java.util.Map;

/** Order handler to map orders in database to java object */
public class OrderHandler extends BeanListHandler<Order> {
  public OrderHandler() {
    super(Order.class, new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));
  }

  private static Map<String, String> getColumnsToFieldsMap() {
    Map<String, String> columnsToFieldsMap = new HashMap<>();
    columnsToFieldsMap.put("id", "id");
    columnsToFieldsMap.put("user_id", "userId");
    columnsToFieldsMap.put("status", "status");
    return columnsToFieldsMap;
  }
}
