package com.yohanmarcus.webshop.user.dto;

import com.yohanmarcus.webshop.user.domain.UserRole;
import lombok.Value;

@Value(staticConstructor = "from")
public class UserDto {
  String id;
  String username;
  UserRole role;
}
