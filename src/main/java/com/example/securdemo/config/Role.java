package com.example.securdemo.config;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER_OWNER,
    USER_VET,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}