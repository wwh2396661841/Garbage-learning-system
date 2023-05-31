package com.wwh.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wwh.system.domain.SystemDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

 /**
 * 部门表;(system_dept)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-3-19
 */
@Mapper
public interface SystemDeptMapper  extends BaseMapper<SystemDept>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<SystemDept> selectByPage(IPage<SystemDept> page , @Param(Constants.WRAPPER) Wrapper<SystemDept> wrapper);
}