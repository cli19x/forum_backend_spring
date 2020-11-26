package com.example.cs4704.service;

import com.example.cs4704.bucket.BucketName;
import com.example.cs4704.dao_user.UserDao;
import com.example.cs4704.model_reponse.ResponseObject;
import com.example.cs4704.model_user.ResetPassword;
import com.example.cs4704.model_user.UpdatePassword;
import com.example.cs4704.model_user.UpdateSignature;
import com.example.cs4704.model_user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    private final EmailSendingService emailService;
    private ResponseObject responseObject;
    private final AWSFileService fileService;

    @Autowired
    public UserService(@Qualifier("postgres-user") UserDao userDao,
                       EmailSendingService emailService,
                       AWSFileService fileService) {
        this.userDao = userDao;
        this.emailService = emailService;
        this.fileService = fileService;
    }

    public ResponseObject insertNewUser(UserInfo userInfo) {
        responseObject = new ResponseObject();
        try {
            userDao.insertNewUser(userInfo);
            responseObject.setMsg("Registration success");
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject getUserByEmail(String email) {
        responseObject = new ResponseObject();
        try {
            UserInfo res = userDao.getUserByEmail(email);
            responseObject.setMsg("User info is successfully retrieved");
            responseObject.setObjects(res);
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject createResetCase(String email) {
        responseObject = new ResponseObject();
        try {
            int vCode = userDao.createResetCase(email);
            emailService.sendmail(email, vCode);
            responseObject.setMsg("Check your email for verify code");
        } catch (MessagingException | DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject resetMyPassword(ResetPassword resetPassword) {
        responseObject = new ResponseObject();
        try {
            userDao.resetPassword(resetPassword);
            responseObject.setMsg("Password has been reset");
        } catch (IllegalStateException | DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }



    public ResponseObject updateMyPassword(UpdatePassword updatePassword) {
        responseObject = new ResponseObject();
         try {
             boolean res = userDao.updatePassword(updatePassword);
             if (res) {
                 responseObject.setMsg("Password is successfully updated");
             } else {
                 responseObject.setErrMsg("Password is incorrect");
             }
         } catch (DataAccessException e) {
             responseObject.setErrMsg(e.getLocalizedMessage());
         }
         return responseObject;
    }

    public ResponseObject updateMySignature(UpdateSignature updateSignature) {
        responseObject = new ResponseObject();
        try {
            userDao.updateSignature(updateSignature);
            responseObject.setMsg("Personal Signature is successfully updated");
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  userDao.loadUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }


    public ResponseObject getUserProfile(UUID uid) {
        responseObject = new ResponseObject();
        try {
            UserInfo res = userDao.getUserProfile(uid);
            responseObject.setMsg("User profile is successfully retrieved");
            System.out.println(res.toString());
            responseObject.setObjects(res);
        } catch (DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }


    public ResponseObject uploadUserAvatar(UUID uid, MultipartFile file) {
        responseObject = new ResponseObject();
        try {
            isFileEmpty(file);
            isImage(file);
            UserInfo userInfo = userDao.getUserProfile(uid);

            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());
            metadata.put("Content-Length", String.valueOf(file.getSize()));

            String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userInfo.getUid());
            String fileName =  String.format("%s_%s", LocalDateTime.now(), file.getOriginalFilename());
            fileService.upload(path, fileName, Optional.of(metadata), file.getInputStream());
            userDao.updateUserAvatar(userInfo.getUid(), fileName);

            responseObject.setMsg("Avatar is successfully uploaded");
        } catch (IOException | IllegalStateException | DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject downloadUserAvatar(UUID uid) {
        responseObject = new ResponseObject();
        try {
            UserInfo userInfo = userDao.getUserProfile(uid);
            String path = String.format("%s/%s",
                    BucketName.PROFILE_IMAGE.getBucketName(), userInfo.getUid());
            byte[] file =  userInfo.getAvatarKey().map(key -> fileService.download(path, key)).orElse(new byte[0]);
            responseObject.setObjects(file);
            responseObject.setMsg("Avatar is successfully received");
        } catch (IllegalStateException | DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject uploadUserBackground(UUID uid, MultipartFile file) {
        responseObject = new ResponseObject();
        try {
            isFileEmpty(file);
            isImage(file);
            UserInfo userInfo = userDao.getUserProfile(uid);

            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());
            metadata.put("Content-Length", String.valueOf(file.getSize()));

            String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), userInfo.getUid());
            String fileName =  String.format("%s_%s", LocalDateTime.now(), file.getOriginalFilename());
            fileService.upload(path, fileName, Optional.of(metadata), file.getInputStream());
            userDao.updateUserBackground(userInfo.getUid(), fileName);

            responseObject.setMsg("Background is successfully uploaded");
        } catch (IOException | IllegalStateException | DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    public ResponseObject downloadUserBackground(UUID uid) {
        responseObject = new ResponseObject();
        try {
            UserInfo userInfo = userDao.getUserProfile(uid);
            String path = String.format("%s/%s",
                    BucketName.PROFILE_IMAGE.getBucketName(), userInfo.getUid());
            byte[] file =  userInfo.getBackgroundKey().map(key -> fileService.download(path, key)).orElse(new byte[0]);
            responseObject.setObjects(file);
            responseObject.setMsg("Avatar is successfully received");
        } catch (IllegalStateException | DataAccessException e) {
            responseObject.setErrMsg(e.getLocalizedMessage());
        }
        return responseObject;
    }

    private void isImage(MultipartFile file) {
        if (Arrays.asList(IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF)
                .contains(file.getContentType())) {
            throw new IllegalStateException("File must be a image");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [" + file.getSize() + "]");
        }
    }


}
