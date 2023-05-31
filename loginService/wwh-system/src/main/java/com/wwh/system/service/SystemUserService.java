package com.wwh.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wwh.system.domain.SystemUser;
import com.wwh.system.dto.UserDeptDto;
import com.wwh.system.dto.UserProjectDto;
import com.wwh.system.dto.UserRoleDto;

public interface SystemUserService {
    SystemUser getUserInfo(String username);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SystemUser queryById(String id);

    /**
     * 分页查询
     *
     * @param systemUser 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<SystemUser> paginQuery(SystemUser systemUser, long current, long size);
    /**
     * 新增数据
     *
     * @param systemUser 实例对象
     * @return 实例对象
     */
    int insert(SystemUser systemUser);
    /**
     * 更新数据
     *
     * @param systemUser 实例对象
     * @return 实例对象
     */
    int update(SystemUser systemUser);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    boolean authorizeRole(UserRoleDto userRoleDto);

    boolean authorizeProject(UserProjectDto userRoleDto);

    boolean authorizeDept(UserDeptDto userRoleDto);
}
