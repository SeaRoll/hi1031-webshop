package com.yohanmarcus.webshop.user.dto;

public record UserFormDto(String username, String password) {
    public boolean isValid() {
        if(username == null || password == null) return false;
        if(username.length() < 3) return false;
        if(password.length() < 8) return false;

        return true;
    }
}
