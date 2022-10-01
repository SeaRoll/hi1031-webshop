package com.yohanmarcus.webshop.user.dao;

import com.yohanmarcus.webshop.user.domain.User;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.HashMap;
import java.util.Map;

public class UserHandler extends BeanListHandler<User> {
  public UserHandler() {
    super(User.class, new BasicRowProcessor(new BeanProcessor(getColumnsToFieldsMap())));
  }

  private static Map<String, String> getColumnsToFieldsMap() {
    Map<String, String> columnsToFieldsMap = new HashMap<>();
    columnsToFieldsMap.put("id", "id");
    columnsToFieldsMap.put("username", "username");
    columnsToFieldsMap.put("password", "password");
    columnsToFieldsMap.put("role", "role");
    return columnsToFieldsMap;
  }
}
