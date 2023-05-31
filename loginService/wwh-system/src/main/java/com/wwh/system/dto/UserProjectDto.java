package com.wwh.system.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserProjectDto {
    @NotEmpty(message = "用户id不能为空")
    private String userId;

    private String userName;
    @NotEmpty(message = "角色id不能为空")
    private String projectId;

    private String projectName;
}
