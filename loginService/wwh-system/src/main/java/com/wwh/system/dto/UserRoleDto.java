package com.wwh.system.dto;

import com.wwh.system.domain.SystemRole;
import com.wwh.system.domain.SystemUser;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserRoleDto {

    @NotEmpty(message = "用户id不能为空")
    private String userId;

    private String userName;
    @NotEmpty(message = "角色id不能为空")
    private String roleId;

    private String roleName;
}
