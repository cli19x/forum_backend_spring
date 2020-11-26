package com.example.cs4704.model_user;

import java.time.LocalDateTime;

public class ResetPassword {
    private String email;
    private Integer vCode;
    private String password;
    private LocalDateTime vCodeCreateTime;

    public ResetPassword(String email, Integer vCode, String password, LocalDateTime vCodeCreateTime) {
        this.email = email;
        this.vCode = vCode;
        this.password = password;
        this.vCodeCreateTime = vCodeCreateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getVCode() {
        return vCode;
    }

    public void setVCode(Integer vCode) {
        this.vCode = vCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getVCodeCreateTime() {
        return vCodeCreateTime;
    }

    public void setVCodeCreateTime(LocalDateTime vCodeCreateTime) {
        this.vCodeCreateTime = vCodeCreateTime;
    }
}
