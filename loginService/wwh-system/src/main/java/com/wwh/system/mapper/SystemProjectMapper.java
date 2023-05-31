package com.wwh.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.wwh.system.domain.SystemProject;

 /**
 * 项目表;(system_project)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-3-19
 */
@Mapper
public interface SystemProjectMapper  extends BaseMapper<SystemProject>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<SystemProject> selectByPage(IPage<SystemProject> page , @Param(Constants.WRAPPER) Wrapper<SystemProject> wrapper);
}