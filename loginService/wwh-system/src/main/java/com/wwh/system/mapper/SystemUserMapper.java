package com.wwh.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wwh.system.domain.SystemRole;
import com.wwh.system.domain.SystemUser;
import com.wwh.system.dto.UserDeptDto;
import com.wwh.system.dto.UserProjectDto;
import com.wwh.system.dto.UserRoleDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

 /**
 * ;(system_user)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-3-19
 */
@Mapper
public interface SystemUserMapper  extends BaseMapper<SystemUser>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<SystemUser> selectByPage(IPage<SystemUser> page , @Param(Constants.WRAPPER) Wrapper<SystemUser> wrapper);

     boolean setUserRole(@Param("userRole") UserRoleDto userRoleDto);

     boolean setUserProject(@Param("userProject")UserProjectDto userProjectDto);

     boolean setUserDept(@Param("userDept")UserDeptDto userDeptDto);
 }