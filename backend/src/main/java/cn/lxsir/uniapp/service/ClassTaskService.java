package cn.lxsir.uniapp.service;

import cn.lxsir.uniapp.dto.TaskAddDto;
import cn.lxsir.uniapp.entity.ClassTask;
import cn.lxsir.uniapp.entity.TaskQuestion;
import cn.lxsir.uniapp.entity.TaskResult;
import cn.lxsir.uniapp.vo.ClassRankVo;
import cn.lxsir.uniapp.vo.TaskUserVo;
import cn.lxsir.uniapp.vo.TaskVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface ClassTaskService extends IService<ClassTask> {

    boolean addTask(TaskAddDto taskAddDto);

    List<TaskVo> selectTaskVo(String classId,String userId);

    List<TaskQuestion> getTaskDetail(String taskId);

    boolean saveTaskDetail(Map<String, Object> map);

    String getTaskFinish(String taskId, String userId);

    List<TaskResult> getTaskResult(String taskId, String userId);

    TaskUserVo getTaskMember(String taskId);

    List<ClassRankVo> getRank(String classId);
}
