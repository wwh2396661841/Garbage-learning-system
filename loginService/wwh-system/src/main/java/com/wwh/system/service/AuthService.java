package com.wwh.system.service;


import com.wwh.system.domain.LoginInfo;
import com.wwh.system.dto.LoginForm;

public interface AuthService {
    LoginInfo login(LoginForm loginForm);

    void logout(String token);

    void createToken(LoginInfo loginInfo);
}
