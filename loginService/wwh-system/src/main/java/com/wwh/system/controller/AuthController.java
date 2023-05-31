package com.wwh.system.controller;


import com.wwh.schoolWork.common.core.domain.Result;
import com.wwh.system.domain.LoginInfo;
import com.wwh.system.domain.SystemUser;
import com.wwh.system.dto.LoginForm;
import com.wwh.system.service.AuthService;
import com.wwh.system.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    SystemUserService userService;

    @PostMapping("/login")
    public Result<LoginInfo> login(@RequestBody LoginForm loginForm){
        LoginInfo loginInfo=authService.login(loginForm);
        authService.createToken(loginInfo);
        return Result.success(loginInfo);
    }
    @PostMapping("/logout")
    public Result logout(String token){
        authService.logout(token);
        return Result.success();
    }

    @PostMapping("/regist")
    public Result logout(@RequestBody SystemUser systemUser){
        return Result.success(userService.insert(systemUser));
    }

    @PostMapping("/update")
    public Result update(@RequestBody SystemUser systemUser){
        return Result.success(userService.update(systemUser));
    }



}
