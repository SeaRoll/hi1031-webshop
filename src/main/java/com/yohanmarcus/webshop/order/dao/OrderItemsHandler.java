package com.yohanmarcus.webshop.order.dao;

import com.yohanmarcus.webshop.order.domain.OrderItems;
import com.yohanmarcus.webshop.order.domain.OrderItemsId;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsHandler implements ResultSetHandler<List<OrderItems>> {

  @Override
  public List<OrderItems> handle(ResultSet resultSet) throws SQLException {
    List<OrderItems> items = new ArrayList<>();
    while (resultSet.next()) {
      OrderItemsId id =
          OrderItemsId.of(resultSet.getString("order_id"), resultSet.getString("item_id"));
      OrderItems item = OrderItems.of(id, resultSet.getInt("quantity"));
      items.add(item);
    }
    return items;
  }
}
