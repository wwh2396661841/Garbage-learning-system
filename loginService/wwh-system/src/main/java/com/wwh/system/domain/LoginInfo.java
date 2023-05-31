package com.wwh.system.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginInfo implements Serializable {

    private String token;

    private Long loginTime;

    private Long expireTime;

    private String userId;

    private SystemUser systemUser;
}
