package com.wwh.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wwh.schoolWork.common.core.exceptions.ServiceException;
import com.wwh.system.domain.SystemUser;
import com.wwh.system.dto.UserDeptDto;
import com.wwh.system.dto.UserProjectDto;
import com.wwh.system.dto.UserRoleDto;
import com.wwh.system.mapper.SystemUserMapper;
import com.wwh.system.service.SystemUserService;
import com.wwh.system.util.CTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;



@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper,SystemUser> implements SystemUserService {

    @Autowired
    SystemUserMapper userMapper;

    @Override
    public SystemUser getUserInfo(String username) {
        if(StringUtils.isEmpty(username)){
            throw new ServiceException("用户不能为空");
        }
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SystemUser::getUsername,username);
        SystemUser systemUser = userMapper.selectOne(queryWrapper);
        return systemUser;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    public SystemUser queryById(String id){
        return userMapper.selectById(id);
    }

    /**
     * 分页查询
     *
     * @param systemUser 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<SystemUser> paginQuery(SystemUser systemUser, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getStatus, systemUser.getStatus());
        queryWrapper.eq(SystemUser::getDelFlag, systemUser.getDelFlag());

        //2. 执行分页查询
        Page<SystemUser> pagin = new Page<>(current , size , true);
        IPage<SystemUser> selectResult = userMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param systemUser 实例对象
     * @return 实例对象
     */
    public int insert(SystemUser systemUser){
        if(!systemUser.getPassword().equals(systemUser.getRePassword())){
            throw new ServiceException("密码不一致");
        }
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SystemUser::getUsername,systemUser.getUsername());
        SystemUser systemUserByUserName = userMapper.selectOne(queryWrapper);
        if(systemUserByUserName != null){
            throw new ServiceException("用户已存在");
        }
        systemUser.setId(CTools.getNewID());
        return userMapper.insert(systemUser);
    }

    /**
     * 更新数据
     *
     * @param systemUser 实例对象
     * @return 实例对象
     */
    public int update(SystemUser systemUser){
        //1. 根据条件动态更新
        return userMapper.updateById(systemUser);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(String id){
        int total = userMapper.deleteById(id);
        return total > 0;
    }

    @Override
    public boolean authorizeRole(UserRoleDto userRoleDto) {
        return userMapper.setUserRole(userRoleDto);
    }

    @Override
    public boolean authorizeProject(UserProjectDto userProjectDto) {
        return userMapper.setUserProject(userProjectDto);
    }

    @Override
    public boolean authorizeDept(UserDeptDto userDeptDto) {
        return userMapper.setUserDept(userDeptDto);
    }

}
