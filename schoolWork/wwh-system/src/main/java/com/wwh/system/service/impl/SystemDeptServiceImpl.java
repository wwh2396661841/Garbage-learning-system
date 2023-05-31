package com.wwh.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wwh.system.domain.SystemDept;
import com.wwh.system.mapper.SystemDeptMapper;
import com.wwh.system.service.SystemDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
 /**
 * 部门表;(system_dept)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-3-19
 */
@Service
public class SystemDeptServiceImpl implements SystemDeptService {
    @Autowired
    private SystemDeptMapper systemDeptMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    public SystemDept queryById(String id){
        return systemDeptMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param systemDept 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<SystemDept> paginQuery(SystemDept systemDept, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<SystemDept> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(systemDept.getName())){
            queryWrapper.eq(SystemDept::getName, systemDept.getName());
        }
        if(StrUtil.isNotBlank(systemDept.getStatus())){
            queryWrapper.eq(SystemDept::getStatus, systemDept.getStatus());
        }
        if(StrUtil.isNotBlank(systemDept.getDelFlag())){
            queryWrapper.eq(SystemDept::getDelFlag, systemDept.getDelFlag());
        }
        //2. 执行分页查询
        Page<SystemDept> pagin = new Page<>(current , size , true);
        IPage<SystemDept> selectResult = systemDeptMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param systemDept 实例对象
     * @return 实例对象
     */
    public int insert(SystemDept systemDept){
        return systemDeptMapper.insert(systemDept);
    }
    
    /** 
     * 更新数据
     *
     * @param systemDept 实例对象
     * @return 实例对象
     */
    public int update(SystemDept systemDept){
        //1. 根据条件动态更新
        return systemDeptMapper.updateById(systemDept);
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(String id){
        int total = systemDeptMapper.deleteById(id);
        return total > 0;
    }
}