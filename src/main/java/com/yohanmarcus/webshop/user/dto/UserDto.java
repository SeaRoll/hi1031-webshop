package com.yohanmarcus.webshop.user.dto;

import com.yohanmarcus.webshop.user.domain.User;

public record UserDto(Integer id, String username, String role) {
    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getRole());
    }

    public boolean isValid() {
        if(id == null || username == null || role == null) return false;
        if(username.length() < 3) return false;
        if(role.length() < 3) return false;

        return true;
    }
}
