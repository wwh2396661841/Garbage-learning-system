package cn.lxsir.uniapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@TableName("CLASS")
@Data
public class Class {

    @TableId(value = "ID",type= IdType.INPUT)
    private String id;

    @TableField("CLASS_NAME")
    @NotEmpty(message = "班级名称不能为空")
    private String className;

    @TableField("CLASS_CODE")
    @NotEmpty(message = "班级编码不能为空")
    private String classCode;

    @TableField("CLASS_DESCRIBE")
    private String classDescribe;

//    @TableField("CLASS_PICTURE")
//    private String classPicture;
//
    @TableField("CLASS_USER_NUM")
    private Integer classUserNum;

    @TableField(exist = false)
    private String userId;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String nickName;
}
