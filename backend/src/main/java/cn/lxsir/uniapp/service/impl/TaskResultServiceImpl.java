package cn.lxsir.uniapp.service.impl;

import cn.lxsir.uniapp.entity.TaskResult;
import cn.lxsir.uniapp.mapper.TaskResultMapper;
import cn.lxsir.uniapp.service.TaskResultService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaskResultServiceImpl extends ServiceImpl<TaskResultMapper, TaskResult> implements TaskResultService {
}
