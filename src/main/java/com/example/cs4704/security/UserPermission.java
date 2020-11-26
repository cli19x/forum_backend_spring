package com.example.cs4704.security;

public enum UserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    EVENT_READ("event:read"),
    EVENT_WRITE("event:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
