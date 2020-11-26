package com.example.cs4704.dao_user;

import com.example.cs4704.model_user.ResetPassword;
import com.example.cs4704.model_user.UpdatePassword;
import com.example.cs4704.model_user.UpdateSignature;
import com.example.cs4704.model_user.UserInfo;
import org.springframework.dao.DataAccessException;

import javax.xml.crypto.Data;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    void insertNewUser(UserInfo userInfo);

    UserInfo getUserByEmail(String email) throws DataAccessException;

    Optional<ApplicationUser> loadUserByUsername(String username) throws DataAccessException;

    String getPasswordByEmail(String email) throws DataAccessException;

    boolean updatePassword(UpdatePassword updatePassword) throws DataAccessException;

    int createResetCase(String email) throws DataAccessException;

    void resetPassword(ResetPassword resetPassword) throws IllegalStateException, DataAccessException;

    void updateSignature(UpdateSignature updateSignature) throws DataAccessException;

    UserInfo getUserProfile(UUID uid) throws DataAccessException;

    void updateUserAvatar(UUID uid, String fileName);

    void updateUserBackground(UUID uid, String fileName);
}
