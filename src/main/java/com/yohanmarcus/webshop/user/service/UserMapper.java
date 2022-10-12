package com.yohanmarcus.webshop.user.service;

import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.dto.UserDto;

public class UserMapper {
  public static UserDto toDto(User user) {
    return UserDto.from(user.getId(), user.getUsername(), user.getRole());
  }

  public static User toEntity(UserDto userDto) {
    return User.of(userDto.getId(), userDto.getUsername(), null, userDto.getRole());
  }
}
