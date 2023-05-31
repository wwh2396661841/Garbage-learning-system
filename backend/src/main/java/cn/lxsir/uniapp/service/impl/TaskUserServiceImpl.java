package cn.lxsir.uniapp.service.impl;

import cn.lxsir.uniapp.entity.TaskUser;
import cn.lxsir.uniapp.mapper.TaskUserMapper;
import cn.lxsir.uniapp.service.TaskUserService;
import cn.lxsir.uniapp.vo.TaskUserDetailVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskUserServiceImpl extends ServiceImpl<TaskUserMapper, TaskUser> implements TaskUserService {

    @Autowired
    private TaskUserMapper taskUserMapper;

    @Override
    public List<TaskUserDetailVo> getUserList(String taskId) {
        return taskUserMapper.getListByTaskId(taskId);
    }
}
