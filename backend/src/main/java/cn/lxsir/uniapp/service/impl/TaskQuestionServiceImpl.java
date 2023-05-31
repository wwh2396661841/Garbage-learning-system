package cn.lxsir.uniapp.service.impl;

import cn.lxsir.uniapp.entity.TaskQuestion;
import cn.lxsir.uniapp.mapper.TaskQuestionMapper;
import cn.lxsir.uniapp.service.TaskQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TaskQuestionServiceImpl extends ServiceImpl<TaskQuestionMapper, TaskQuestion> implements TaskQuestionService {
}
