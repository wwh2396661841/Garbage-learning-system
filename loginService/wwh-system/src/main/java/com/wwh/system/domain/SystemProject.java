package com.wwh.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

 /**
 * 项目表;
 * @author : http://www.chiner.pro
 * @date : 2023-3-19
 */
@ApiModel(value = "项目表",description = "")
@TableName("system_project")
public class SystemProject implements Serializable,Cloneable{
    /** ID */
    @ApiModelProperty(name = "ID",notes = "")
    @TableId
    private String id ;
    /** 项目名称 */
    @ApiModelProperty(name = "项目名称",notes = "")
    private String name ;
    /** 状态 0停用 1正常 */
    @ApiModelProperty(name = "状态 0停用 1正常",notes = "")
    private String status ;
    /** 是否删除 0以删除 1未删除 */
    @ApiModelProperty(name = "是否删除 0以删除 1未删除",notes = "")
    private String delFlag ;
    /** 创建人 */
    @ApiModelProperty(name = "创建人",notes = "")
    private String createdBy ;
    /** 创建时间 */
    @ApiModelProperty(name = "创建时间",notes = "")
    private Date createdTime ;
    /** 更新人 */
    @ApiModelProperty(name = "更新人",notes = "")
    private String updatedBy ;
    /** 更新时间 */
    @ApiModelProperty(name = "更新时间",notes = "")
    private Date updatedTime ;

    /** ID */
    public String getId(){
        return this.id;
    }
    /** ID */
    public void setId(String id){
        this.id=id;
    }
    /** 项目名称 */
    public String getName(){
        return this.name;
    }
    /** 项目名称 */
    public void setName(String name){
        this.name=name;
    }
    /** 状态 0停用 1正常 */
    public String getStatus(){
        return this.status;
    }
    /** 状态 0停用 1正常 */
    public void setStatus(String status){
        this.status=status;
    }
    /** 是否删除 0以删除 1未删除 */
    public String getDelFlag(){
        return this.delFlag;
    }
    /** 是否删除 0以删除 1未删除 */
    public void setDelFlag(String delFlag){
        this.delFlag=delFlag;
    }
    /** 创建人 */
    public String getCreatedBy(){
        return this.createdBy;
    }
    /** 创建人 */
    public void setCreatedBy(String createdBy){
        this.createdBy=createdBy;
    }
    /** 创建时间 */
    public Date getCreatedTime(){
        return this.createdTime;
    }
    /** 创建时间 */
    public void setCreatedTime(Date createdTime){
        this.createdTime=createdTime;
    }
    /** 更新人 */
    public String getUpdatedBy(){
        return this.updatedBy;
    }
    /** 更新人 */
    public void setUpdatedBy(String updatedBy){
        this.updatedBy=updatedBy;
    }
    /** 更新时间 */
    public Date getUpdatedTime(){
        return this.updatedTime;
    }
    /** 更新时间 */
    public void setUpdatedTime(Date updatedTime){
        this.updatedTime=updatedTime;
    }
}