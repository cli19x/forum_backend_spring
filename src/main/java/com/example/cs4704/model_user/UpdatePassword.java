package com.example.cs4704.model_user;

import java.util.UUID;

public class UpdatePassword {
    private final UUID uid;
    private final String oldPassword;
    private final String newPassword;

    public UpdatePassword(UUID uid, String oldPassword, String newPassword) {
        this.uid = uid;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public UUID getUid() {
        return uid;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public String toString() {
        return "NewPassword{" +
                "email='" + uid + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
