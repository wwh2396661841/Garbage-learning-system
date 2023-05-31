package com.wwh.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wwh.system.domain.SystemProject;

 /**
 * 项目表;(system_project)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-3-19
 */
public interface SystemProjectService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    SystemProject queryById(String id);
    
    /**
     * 分页查询
     *
     * @param systemProject 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<SystemProject> paginQuery(SystemProject systemProject, long current, long size);
    /** 
     * 新增数据
     *
     * @param systemProject 实例对象
     * @return 实例对象
     */
    int insert(SystemProject systemProject);
    /** 
     * 更新数据
     *
     * @param systemProject 实例对象
     * @return 实例对象
     */
    int update(SystemProject systemProject);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);
}