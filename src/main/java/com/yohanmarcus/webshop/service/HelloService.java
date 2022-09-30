package com.yohanmarcus.webshop.service;

import com.yohanmarcus.webshop.dao.HelloDao;
import com.yohanmarcus.webshop.dao.HelloDaoImpl;
import com.yohanmarcus.webshop.model.Hello;

import java.util.List;

public class HelloService {

  private final HelloDao helloDao;

  public HelloService() {
    helloDao = new HelloDaoImpl();
  }

  public List<Hello> getHellos() {
    return helloDao.findAll();
  }
}
