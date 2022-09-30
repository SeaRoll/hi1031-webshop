package com.yohanmarcus.webshop.hello;

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
