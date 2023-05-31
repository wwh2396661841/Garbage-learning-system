package com.wwh.system.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wwh.system.domain.SystemRole;
import com.wwh.system.mapper.SystemRoleMapper;
import com.wwh.system.service.SystemRoleService;
 /**
 * 角色表;(system_role)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-3-19
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService{
    @Autowired
    private SystemRoleMapper systemRoleMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    public SystemRole queryById(String id){
        return systemRoleMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param systemRole 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<SystemRole> paginQuery(SystemRole systemRole, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<SystemRole> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(systemRole.getName())){
            queryWrapper.eq(SystemRole::getName, systemRole.getName());
        }
        if(StrUtil.isNotBlank(systemRole.getStatus())){
            queryWrapper.eq(SystemRole::getStatus, systemRole.getStatus());
        }
        if(StrUtil.isNotBlank(systemRole.getDelFlag())){
            queryWrapper.eq(SystemRole::getDelFlag, systemRole.getDelFlag());
        }
        //2. 执行分页查询
        Page<SystemRole> pagin = new Page<>(current , size , true);
        IPage<SystemRole> selectResult = systemRoleMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param systemRole 实例对象
     * @return 实例对象
     */
    public int insert(SystemRole systemRole){
        return systemRoleMapper.insert(systemRole);
    }
    
    /** 
     * 更新数据
     *
     * @param systemRole 实例对象
     * @return 实例对象
     */
    public int update(SystemRole systemRole){
        return systemRoleMapper.updateById(systemRole);
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(String id){
        int total = systemRoleMapper.deleteById(id);
        return total > 0;
    }
}