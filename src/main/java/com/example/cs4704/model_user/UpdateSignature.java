package com.example.cs4704.model_user;

import java.util.UUID;

public class UpdateSignature {
    private final UUID uid;
    private final String email;
    private final String pSignature;


    public UpdateSignature(UUID uid, String email, String pSignature) {
        this.uid = uid;
        this.email = email;
        this.pSignature = pSignature;
    }


    public UUID getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getPSignature() {
        return pSignature;
    }

    @Override
    public String toString() {
        return "UpdateSignature{" +
                "uid=" + uid +
                ", email='" + email + '\'' +
                ", pSignature='" + pSignature + '\'' +
                '}';
    }
}
