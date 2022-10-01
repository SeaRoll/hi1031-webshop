package com.yohanmarcus.webshop.user.dto;

import com.yohanmarcus.webshop.user.domain.User;
import com.yohanmarcus.webshop.user.domain.UserRole;

public record UserDto(Integer id, String username, UserRole role) {
    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRole());
    }

    public boolean isValid() {
        if(id == null || username == null || role == null) return false;
        if(username.length() < 3) return false;
        return true;
    }
}
