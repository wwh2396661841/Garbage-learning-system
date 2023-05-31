package cn.lxsir.uniapp.service;

import cn.lxsir.uniapp.entity.TaskUser;
import cn.lxsir.uniapp.vo.TaskUserDetailVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface TaskUserService extends IService<TaskUser> {
    List<TaskUserDetailVo> getUserList(String taskId);
}
