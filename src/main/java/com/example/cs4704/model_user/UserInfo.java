package com.example.cs4704.model_user;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class UserInfo {

    private final UUID uid;
    private final LocalDateTime accessTime;
    private final LocalDateTime createTime;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String nickname;
    private final String signature;
    private final String password;
    private final String role;
    private final String avatarKey;
    private final String backgroundKey;
    private final Boolean isAccountNonExpired;
    private final Boolean isAccountNonLocked;
    private final Boolean isCredentialsNonExpired;
    private final Boolean isEnabled;


    public UserInfo(UUID uid, LocalDateTime accessTime, LocalDateTime createTime, String email, String firstName,
                    String lastName, String nickname, String signature, String password, String userRole,
                    String avatarKey, String backgroundKey, Boolean isAccountNonExpired, Boolean isAccountNonLocked,
                    Boolean isCredentialsNonExpired, Boolean isEnabled) {
        this.uid = uid;
        this.accessTime = accessTime;
        this.createTime = createTime;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.signature = signature;

        this.password = password;
        this.role = userRole;
        this.avatarKey = avatarKey;
        this.backgroundKey = backgroundKey;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public UUID getUid() {
        return uid;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSignature() {
        return signature;
    }

    public String getPassword() {
        return password;
    }


    public String getRole() {
        return role;
    }

    public Optional<String> getAvatarKey() {
        return Optional.ofNullable(avatarKey);
    }

    public Optional<String> getBackgroundKey() {
        return Optional.ofNullable(backgroundKey);
    }

    public Boolean getAccountNonExpired() {
        return isAccountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return isAccountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid=" + uid +
                ", accessTime=" + accessTime +
                ", createTime=" + createTime +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", avatarKey='" + avatarKey + '\'' +
                ", backgroundKey='" + backgroundKey + '\'' +
                ", isAccountNonExpired=" + isAccountNonExpired +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
