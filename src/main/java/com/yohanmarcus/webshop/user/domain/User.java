package com.yohanmarcus.webshop.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class User {
  private String id;
  private String username;
  private String password;
  private UserRole role;
}
