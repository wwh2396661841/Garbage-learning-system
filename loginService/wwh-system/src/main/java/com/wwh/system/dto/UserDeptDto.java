package com.wwh.system.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDeptDto {
    @NotEmpty(message = "用户id不能为空")
    private String userId;

    private String userName;
    @NotEmpty(message = "角色id不能为空")
    private String deptId;

    private String deptName;
}
