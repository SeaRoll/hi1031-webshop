package com.yohanmarcus.webshop.dao;

import com.yohanmarcus.webshop.model.Hello;

import java.util.List;

public interface HelloDao {
  List<Hello> findAll();
}
