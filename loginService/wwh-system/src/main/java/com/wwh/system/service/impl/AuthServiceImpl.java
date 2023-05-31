package com.wwh.system.service.impl;

import com.wwh.schoolWork.common.core.exceptions.ServiceException;
import com.wwh.schoolWork.common.core.util.IdUtils;
import com.wwh.system.domain.LoginInfo;
import com.wwh.system.domain.SystemUser;
import com.wwh.system.dto.LoginForm;
import com.wwh.system.service.AuthService;
import com.wwh.system.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    //登录有效时间
    private final static long expireTime = 90 ;
    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    //redis缓存前缀
    public final static String LOGIN_TOKEN_KEY = "login_tokens:";

    @Autowired
    private SystemUserService userService;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public LoginInfo login(LoginForm loginForm) {
        //校验用户名密码
        //根据用户名获取用户信息
        SystemUser systemUser=userService.getUserInfo(loginForm.getUsername());
        if(systemUser == null){
            throw new ServiceException("用户名不存在");
        }
        //校验密码是否正确,可以在此处设置超过密码错误次数 锁定用户
        if(!loginForm.getPassword().equals(systemUser.getPassword())){
            throw new ServiceException("密码错误");
        }
        LoginInfo loginInfo=new LoginInfo();
        loginInfo.setSystemUser(systemUser);

        return loginInfo;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(getToken(token));
    }

    public void createToken(LoginInfo loginInfo) {
        //可以考虑在此检查用户是否已经登录
        loginInfo.setToken(IdUtils.simpleUUID());
        loginInfo.setLoginTime(System.currentTimeMillis());
        loginInfo.setExpireTime(loginInfo.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey =getToken(loginInfo.getToken());
        //缓存用户信息
        redisTemplate.opsForValue().set(userKey, loginInfo, expireTime, TimeUnit.MINUTES);
    }

    public String getToken(String token){
        return LOGIN_TOKEN_KEY+token;
    }
}
