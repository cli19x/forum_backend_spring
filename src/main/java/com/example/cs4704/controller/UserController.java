package com.example.cs4704.controller;


import com.example.cs4704.model_reponse.ResponseObject;
import com.example.cs4704.model_user.ResetPassword;
import com.example.cs4704.model_user.UpdatePassword;
import com.example.cs4704.model_user.UpdateSignature;
import com.example.cs4704.model_user.UserInfo;
import com.example.cs4704.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/email/{username}")
    public ResponseObject userInfo(@PathVariable("username") String username) {
        return userService.getUserByEmail(username);
    }

    @PostMapping("/register")
    public ResponseObject userSignUp(@RequestBody UserInfo userInfo) {
        return userService.insertNewUser(userInfo);
    }

    @GetMapping("/forget-case")
    public ResponseObject createResetCase(@RequestParam(name = "email") String email) {
        return userService.createResetCase(email);
    }

    @PostMapping("/forget-password")
    public ResponseObject forgetPassword(@RequestBody ResetPassword resetPassword) {
        return userService.resetMyPassword(resetPassword);
    }

    @PutMapping("/api/update-password")
    public ResponseObject updatePassword(@RequestBody UpdatePassword updatePassword) {
        return userService.updateMyPassword(updatePassword);
    }

    @PutMapping("/api/update-personal-signature")
    public ResponseObject updatePersonalSignature(@RequestBody UpdateSignature updateSignature) {
        return userService.updateMySignature(updateSignature);
    }

    @GetMapping("/api/{uid}")
    public ResponseObject getUserProfile(@PathVariable("uid") String uid) {
        return userService.getUserProfile(UUID.fromString(uid));
    }

    @PostMapping(
            path = "/api/avatar/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces =  MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseObject uploadUserAvatar(@RequestParam("uid") UUID uid,
                                       @RequestBody MultipartFile file) {
        return userService.uploadUserAvatar(uid, file);
    }

    @GetMapping(path = "/api/avatar/download/{uid}")
    public ResponseObject downloadUserAvatar(@PathVariable("uid") UUID uid) {
        return userService.downloadUserAvatar(uid);
    }

    @PostMapping(
            path = "/api/background/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces =  MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseObject uploadUserBackground(@RequestParam("uid") UUID uid,
                                       @RequestBody MultipartFile file) {
         return userService.uploadUserBackground(uid, file);
    }

    @GetMapping(path = "/api/background/download/{uid}")
    public ResponseObject downloadUserBackground(@PathVariable("uid") UUID uid) {
        return userService.downloadUserBackground(uid);
    }

}
