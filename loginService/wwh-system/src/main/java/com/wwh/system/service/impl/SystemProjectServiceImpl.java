package com.wwh.system.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.wwh.system.domain.SystemProject;
import com.wwh.system.mapper.SystemProjectMapper;
import com.wwh.system.service.SystemProjectService;
 /**
 * 项目表;(system_project)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-3-19
 */
@Service
public class SystemProjectServiceImpl implements SystemProjectService{
    @Autowired
    private SystemProjectMapper systemProjectMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    public SystemProject queryById(String id){
        return systemProjectMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param systemProject 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<SystemProject> paginQuery(SystemProject systemProject, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<SystemProject> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(systemProject.getName())){
            queryWrapper.eq(SystemProject::getName, systemProject.getName());
        }
        if(StrUtil.isNotBlank(systemProject.getStatus())){
            queryWrapper.eq(SystemProject::getStatus, systemProject.getStatus());
        }
        if(StrUtil.isNotBlank(systemProject.getDelFlag())){
            queryWrapper.eq(SystemProject::getDelFlag, systemProject.getDelFlag());
        }
        //2. 执行分页查询
        Page<SystemProject> pagin = new Page<>(current , size , true);
        IPage<SystemProject> selectResult = systemProjectMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param systemProject 实例对象
     * @return 实例对象
     */
    public int insert(SystemProject systemProject){
        return systemProjectMapper.insert(systemProject);
    }
    
    /** 
     * 更新数据
     *
     * @param systemProject 实例对象
     * @return 实例对象
     */
    public int update(SystemProject systemProject){
        return systemProjectMapper.updateById(systemProject);
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(String id){
        int total = systemProjectMapper.deleteById(id);
        return total > 0;
    }
}