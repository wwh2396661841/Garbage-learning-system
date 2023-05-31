package cn.lxsir.uniapp.controller;

import cn.lxsir.uniapp.dto.TaskAddDto;
import cn.lxsir.uniapp.entity.TaskQuestion;
import cn.lxsir.uniapp.service.ClassTaskService;
import cn.lxsir.uniapp.vo.ClassRankVo;
import cn.lxsir.uniapp.vo.TaskUserVo;
import cn.lxsir.uniapp.vo.TaskVo;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
@Slf4j
public class TaskController {

    @Autowired
    private ClassTaskService classTaskService;

    @PostMapping("/save")
    public R addTask(@RequestBody TaskAddDto taskAddDto){
        return R.ok(classTaskService.addTask(taskAddDto));
    }

    @GetMapping("/list")
    public R addTask(@RequestParam("classId") String classId,@RequestParam("userId")String userId){
        List<TaskVo> taskVoList = null;
        taskVoList = classTaskService.selectTaskVo(classId,userId);

        return R.ok(taskVoList);
    }

    @GetMapping("/getDetail")
    public R getDetail(@RequestParam("taskId") String taskId){
        List<TaskQuestion> taskQuestions = classTaskService.getTaskDetail(taskId);
        return R.ok(taskQuestions);
    }

    @PostMapping("/saveResult")
    public R saveTaskResult(@RequestBody Map<String,Object> map){
        return R.ok(classTaskService.saveTaskDetail(map));
    }

    @GetMapping("/getResult")
    public R getTaskResult(@RequestParam("taskId") String taskId,@RequestParam("userId") String userId){
        return R.ok(classTaskService.getTaskResult(taskId,userId));
    }

    @GetMapping("/getFinish")
    public R getTaskFinish(@RequestParam("taskId") String taskId,@RequestParam("userId") String userId){
        return R.ok(classTaskService.getTaskFinish(taskId,userId));
    }

    @GetMapping("/getMember")
    public R getTaskMember(@RequestParam("taskId") String taskId){
        TaskUserVo taskMember = classTaskService.getTaskMember(taskId);
        return R.ok(taskMember);
    }

    @GetMapping("/getRank")
    public R getRank(@RequestParam("classId") String classId){
        List<ClassRankVo> rank = classTaskService.getRank(classId);
        return R.ok(rank);
    }
}
