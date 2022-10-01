package com.yohanmarcus.webshop.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserRole {
  USER("user"),
  STAFF("staff"),
  ADMIN("admin");

  @Getter private final String name;
}
