package com.wwh.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName("mobile_user")
@Data
public class SystemUser implements Serializable {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    @TableField("user_name")
    private String username;
    @TableField("password")
    private String password;
    @TableField(exist = false)
    private String rePassword;
    @TableField("nick_name")
    private String nickname;
    @TableField("avatar")
    private String avatar;
    @TableField("sex")
    private Byte sex;
    @TableField("email")
    private String email;
    @TableField("phone")
    private String phone;
    @TableField("del_flag")
    private Byte delFlag;
    @TableField("status")
    private Byte status;
    @TableField("update_by")
    private String updateBy;
    @TableField("update_time")
    private Date updateTime;
    @TableField("create_by")
    private String createBy;
    @TableField("create_time")
    private Date createTime;

}
