package com.example.cs4704.dao_user;


import com.example.cs4704.model_user.ResetPassword;
import com.example.cs4704.model_user.UpdatePassword;
import com.example.cs4704.model_user.UpdateSignature;
import com.example.cs4704.model_user.UserInfo;
import com.example.cs4704.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


// accessTime DATE NULL,
//    createTime DATE NOT NULL,
//    eMail VARCHAR(200) NOT NULL UNIQUE,
//    firstName VARCHAR(200) NOT NULL,
//    lastName VARCHAR(200) NOT NULL,
//    nickName VARCHAR(200) NOT NULL,
//    pSignature VARCHAR(600) NULL,
//    id INTEGER Not NULL AUTO_INCREMENT PRIMARY KEY,


@Repository("postgres-user")
public class PostgresUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private static final int TEN_MINUTES = 10 * 60 * 1000;
    @Autowired
    public PostgresUserDao(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void insertNewUser(UserInfo userInfo) throws DataAccessException {
        final String sql =
                "INSERT INTO UserInfo (uid, access_time, create_time, email, first_name, last_name, nickname, " +
                        "signature, password, role, is_account_non_expired, is_account_non_locked, " +
                        "is_credentials_non_expired, is_enabled) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        UUID uuid = UUID.randomUUID();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Object[] params = {uuid, userInfo.getAccessTime(), timestamp, userInfo.getEmail(),
                userInfo.getFirstName(), userInfo.getLastName(),userInfo.getNickname(), userInfo.getSignature(),
                passwordEncoder.encode(userInfo.getPassword()), "user", true, true, true, true};
        jdbcTemplate.update(sql, params);
    }

    @Override
    public UserInfo getUserByEmail(String email) throws DataAccessException {
        final String sql = "SELECT * FROM UserInfo WHERE email = ?";
        Object[] params = {email};
        return jdbcTemplate.query(sql, params, (resultSet, i) ->
                new UserInfo(UUID.fromString(resultSet.getString("uid")),
                        null,
                        null,
                        resultSet.getString("email"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("nickname"),
                        resultSet.getString("signature"),
                        null,
                        resultSet.getString("role"),
                        resultSet.getString("avatar_key"),
                        resultSet.getString("background_key"),
                        null,
                        null,
                        null,
                        null)
        ).get(0);
    }

    @Override
    public Optional<ApplicationUser> loadUserByUsername(String username) {
        try {
            return getAllUsers()
                    .stream().filter(applicationUser -> username.equals(applicationUser.getUsername()))
                    .findFirst();
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    @Override
    public String getPasswordByEmail(String email) throws DataAccessException {
        final String sql = "SELECT password FROM UserInfo WHERE email = ?";
        Object[] params = {email};
        return jdbcTemplate.queryForObject(sql, params, String.class);
    }

    @Override
    public boolean updatePassword(UpdatePassword updatePassword) throws DataAccessException {
        UUID uid = updatePassword.getUid();
        String currentPassword = updatePassword.getOldPassword();
        String updatedPassword =updatePassword.getNewPassword();
        final String sql = "SELECT password FROM UserInfo WHERE uid = ?";
        Object[] params = {uid};
        String encodedPassword = jdbcTemplate.queryForObject(sql, params, String.class);
        if (this.passwordEncoder.matches(currentPassword, encodedPassword)) {
            final String sql2 = "UPDATE UserInfo SET password = ? WHERE email = ?";
            Object[] params2 = {passwordEncoder.encode(updatedPassword), uid};
            jdbcTemplate.update(sql2, params2);
            return true;
        }
        return false;
    }

    @Override
    public int createResetCase(String email) throws DataAccessException {
        Random rand = new Random();
        int num = rand.nextInt(9000000) + 1000000;
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        final String sql =
                "INSERT INTO Reset (email, v_code, v_code_create_time) " +
                        "VALUES (?, ?, ?);";
        Object[] params = {email, num, timestamp};
        jdbcTemplate.update(sql, params);
        return num;
    }

    @Override
    public void resetPassword(ResetPassword resetPassword) throws IllegalStateException, DataAccessException {
        final String sql = "SELECT * FROM Reset WHERE email = ? AND v_code = ?";
        Object[] params = {resetPassword.getEmail(), resetPassword.getVCode()};
        ResetPassword reset =  jdbcTemplate.query(sql, params, (resultSet, i) ->
                new ResetPassword(resultSet.getString("email"),
                        resultSet.getInt("v_code"),
                        null,
                        resultSet.getTimestamp("v_code_create_time").toLocalDateTime())
        ).get(0);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime create = reset.getVCodeCreateTime();
        if (now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
                create.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() > TEN_MINUTES) {
            throw new IllegalStateException("Verify code has expired");
        }

        final String sql2 = "UPDATE UserInfo SET password = ? WHERE email = ?";
        Object[] params2 = {passwordEncoder.encode(resetPassword.getPassword()), reset.getEmail()};
        jdbcTemplate.update(sql2, params2);

        final String sql3 = "DELETE FROM Reset WHERE email = ? AND v_code = ?";
        Object[] params3 = {resetPassword.getEmail(), resetPassword.getVCode()};
        jdbcTemplate.update(sql3, params3);
    }

    @Override
    public void updateSignature(UpdateSignature updateSignature) throws DataAccessException {
        final String sql = "UPDATE UserInfo SET signature = ? WHERE uid = ? AND email = ?";
        System.out.println(updateSignature.toString());
        Object[] params = {updateSignature.getPSignature(), updateSignature.getUid(), updateSignature.getEmail()};
        jdbcTemplate.update(sql, params);
    }

//    UUID uid, String accessTime, Date createTime, String email, String firstName,
//    String lastName, String nickName, String pSignature, String password, String userRole,
//    Boolean isAccountNonExpired, Boolean isAccountNonLocked, Boolean isCredentialsNonExpired, Boolean isEnabled

    @Override
    public UserInfo getUserProfile(UUID uid) throws DataAccessException {
        final String sql = "SELECT * FROM UserInfo WHERE uid = ?";
        Object[] params = {uid};
        return jdbcTemplate.query(sql, params, (resultSet, i) ->
                    new UserInfo(UUID.fromString(resultSet.getString("uid")),
                            null,
                            resultSet.getTimestamp("create_time").toLocalDateTime(),
                            resultSet.getString("email"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("nickname"),
                            resultSet.getString("signature"),
                            null,
                            resultSet.getString("role"),
                            resultSet.getString("avatar_key"),
                            resultSet.getString("background_key"),
                            null,
                            null,
                            null,
                            null)
                ).get(0);
    }

    @Override
    public void updateUserAvatar(UUID uid, String fileName) {
        final String sql = "UPDATE UserInfo SET avatar_key = ? WHERE uid = ?";
        Object[] params = {fileName, uid};
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateUserBackground(UUID uid, String fileName) {
        final String sql = "UPDATE UserInfo SET background_key = ? WHERE uid = ?";
        Object[] params = {fileName, uid};
        jdbcTemplate.update(sql, params);
    }

    private List<ApplicationUser> getAllUsers() throws DataAccessException {
        final String sql = "SELECT email, password, role, is_account_non_expired, " +
                "is_account_non_locked, is_credentials_non_expired, is_enabled  FROM UserInfo ORDER By email";

        return jdbcTemplate.query(sql, (resultSet, i) ->
                new ApplicationUser(resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getString("role").equals("user") ?
                                UserRole.USER.grantedAuthorities() : UserRole.ADMIN.grantedAuthorities(),
                        resultSet.getBoolean("is_account_non_expired"),
                        resultSet.getBoolean("is_account_non_locked"),
                        resultSet.getBoolean("is_credentials_non_expired"),
                        resultSet.getBoolean("is_enabled")
                ));
    }
}
