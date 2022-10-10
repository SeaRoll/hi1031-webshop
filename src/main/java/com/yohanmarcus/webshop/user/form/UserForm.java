package com.yohanmarcus.webshop.user.form;

/**
 * Form for the user when registering/logging in
 */
public record UserForm(String username, String password) {
    public boolean isValid() {
        if(username == null || password == null) return false;
        if(username.length() < 3) return false;
        if(password.length() < 8) return false;

        return true;
    }
}
