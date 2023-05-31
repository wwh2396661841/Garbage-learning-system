package cn.lxsir.uniapp.service.impl;

import cn.lxsir.uniapp.dto.TaskAddDto;
import cn.lxsir.uniapp.entity.*;
import cn.lxsir.uniapp.mapper.ClassTaskMapper;
import cn.lxsir.uniapp.mapper.ClassUserMapper;
import cn.lxsir.uniapp.service.ClassTaskService;
import cn.lxsir.uniapp.service.TaskQuestionService;
import cn.lxsir.uniapp.service.TaskResultService;
import cn.lxsir.uniapp.service.TaskUserService;
import cn.lxsir.uniapp.util.CTools;
import cn.lxsir.uniapp.vo.ClassRankVo;
import cn.lxsir.uniapp.vo.TaskUserDetailVo;
import cn.lxsir.uniapp.vo.TaskUserVo;
import cn.lxsir.uniapp.vo.TaskVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class ClassTaskServiceImpl extends ServiceImpl<ClassTaskMapper, ClassTask> implements ClassTaskService {
    @Autowired
    private TaskQuestionService taskQuestionService;

    @Autowired
    private TaskUserService taskUserService;

    @Autowired
    private ClassUserMapper classUserMapper;

    @Autowired
    private ClassTaskMapper classTaskMapper;

    @Autowired
    private TaskResultService taskResultService;

    @Override
    public boolean addTask(TaskAddDto taskAddDto) {
        if(StringUtils.isEmpty(taskAddDto.getClassId()) || CollectionUtils.isEmpty(taskAddDto.getQuestions())){
            return false;
        }
        List<QuestionBank> questionBankList = taskAddDto.getQuestions()
                .stream().filter(questionBank -> questionBank != null).collect(Collectors.toList());
        ClassTask classTask = new ClassTask();
        String taskId = CTools.getNewID();
        classTask.setTaskId(taskId);
        classTask.setClassId(taskAddDto.getClassId());
        classTask.setCreatedTime(new Date());
        classTask.setQuestionNum(questionBankList.size());
        classTask.setLimitDay(1);
        classTaskMapper.insert(classTask);

        QueryWrapper<ClassUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClassUser::getClassId,taskAddDto.getClassId());
        List<ClassUser> classUsers = classUserMapper.selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(classUsers)){
            List<TaskUser> taskUsers = classUsers.stream().map(classUser -> {
                TaskUser taskUser = new TaskUser();
                taskUser.setId(CTools.getNewID());
                taskUser.setTaskId(taskId);
                taskUser.setUserId(classUser.getUserId());
                taskUser.setFinishFlag("0");
                return taskUser;
            }).collect(Collectors.toList());
            taskUserService.saveBatch(taskUsers);
        }
        List<QuestionBank> questions = questionBankList;
        if(CollectionUtils.isNotEmpty(questions)){
            List<TaskQuestion> taskQuestions= questions.stream().map(question ->{
                TaskQuestion taskQuestion = new TaskQuestion();
                taskQuestion.setTaskId(taskId);
                taskQuestion.setQuestionId(question.getQuestionId());
                taskQuestion.setId(CTools.getNewID());
                taskQuestion.setGarbageType(question.getGarbageType());
                taskQuestion.setGarbageName(question.getGarbageName());
                return taskQuestion;
            }).collect(Collectors.toList());
            taskQuestionService.saveBatch(taskQuestions);
        }
        return true;
    }

    @Override
    public List<TaskVo> selectTaskVo(String classId,String userId) {
        QueryWrapper<ClassTask> classTaskQueryWrapper = new QueryWrapper<>();
        classTaskQueryWrapper.lambda().eq(ClassTask::getClassId,classId)
                .orderByDesc(ClassTask::getCreatedTime);
        List<ClassTask> classTasks = classTaskMapper.selectList(classTaskQueryWrapper);
        List<TaskVo> taskVoList = null;
        if(CollectionUtils.isNotEmpty(classTasks)){
            taskVoList = classTasks.stream().map(classTask -> {
                TaskVo taskVo = new TaskVo();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(classTask.getCreatedTime());
                // 获取当前年
                Integer year = calendar.get(Calendar.YEAR);
                Integer month = calendar.get(Calendar.MONTH) + 1;
                Integer day = calendar.get(Calendar.DATE);
                Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
                Integer minute = calendar.get(Calendar.MINUTE);
                taskVo.setTaskId(classTask.getTaskId());
                taskVo.setTaskTime(year + "-" + month + "-" + day);
                return taskVo;
            }).collect(Collectors.toList());
            
            //TODO
            List<String> taskIds = classTasks.stream().map(classTask -> classTask.getTaskId()).collect(Collectors.toList());
            QueryWrapper<TaskQuestion> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(TaskQuestion::getTaskId,taskIds);
            List<TaskQuestion> taskQuestions = taskQuestionService.list(queryWrapper);
            Map<String, List<TaskQuestion>> mapByTaskId = taskQuestions.stream()
                    .collect(Collectors.groupingBy(TaskQuestion::getTaskId));

            QueryWrapper<ClassUser> queryWrapperClassUser = new QueryWrapper<>();
            queryWrapperClassUser.lambda().eq(ClassUser::getClassId,classId)
                            .eq(ClassUser::getUserType,"1");
            ClassUser classUser = classUserMapper.selectOne(queryWrapperClassUser);

            taskVoList.forEach( taskVo -> {
                taskVo.setQuestions(mapByTaskId.get(taskVo.getTaskId()));
                QueryWrapper<TaskUser> queryWrapperUser = new QueryWrapper<>();
                queryWrapperUser.lambda().eq(TaskUser::getTaskId,taskVo.getTaskId());
                List<TaskUser> taskUsers = taskUserService.list(queryWrapperUser);
                List<TaskUser> unFinishUserList = null;
                Integer userNum = 0;
                Integer unFinishUserNum = 0;
                AtomicBoolean finishFlag = new AtomicBoolean(false);
                if(CollectionUtils.isNotEmpty(taskUsers)){
                    userNum = taskUsers.size();
                    unFinishUserList = taskUsers.stream()
                            .filter(taskUser -> "0".equals(taskUser.getFinishFlag())).collect(Collectors.toList());
                    taskUsers.forEach( taskUser -> {
                            if(taskUser.getUserId().equals(userId) && "1".equals(taskUser.getFinishFlag())) {
                                finishFlag.set(true);
                            }
                        }
                    );
                }
                if(CollectionUtils.isNotEmpty(unFinishUserList)){
                    unFinishUserNum = unFinishUserList.size();
                }
                taskVo.setFinishNum("已完成"+(userNum-unFinishUserNum)+"人");
                taskVo.setUnFinishNum("未完成"+unFinishUserNum+"人");
                taskVo.setFinishFlag(finishFlag.get());
                taskVo.setReleaseUser(classUser.getNickName());
            });

        }
        return taskVoList;
    }

    @Override
    public List<TaskQuestion> getTaskDetail(String taskId) {
        QueryWrapper<TaskQuestion> queryWrapper  = new QueryWrapper<>();
        queryWrapper.lambda().eq(TaskQuestion::getTaskId,taskId);
        return taskQuestionService.list(queryWrapper);
    }

    @Override
    public boolean saveTaskDetail(Map<String, Object> map) {
        List<LinkedHashMap> list = (List<LinkedHashMap>) map.get("list");
        Object classId = map.get("classId");
        Object userId = map.get("userId");
        if(CollectionUtils.isNotEmpty(list) && classId != null && userId != null){
            List<TaskResult> taskResults = list.stream().map(element -> {
                TaskResult taskResult = new TaskResult();
                taskResult.setId(CTools.getNewID());
                taskResult.setTaskId(element.get("taskId").toString());
                taskResult.setClassId(classId.toString());
                taskResult.setUserId(userId.toString());
                taskResult.setGarbageName(element.get("garbageName").toString());
                taskResult.setGarbageType(element.get("garbageType").toString());
                taskResult.setSelectedType(element.get("selectedType").toString());
                taskResult.setQuestionId(element.get("questionId").toString());
                taskResult.setWhether(taskResult.getGarbageType().equals(taskResult.getSelectedType()) ? "1" : "0");
                return taskResult;
            }).collect(Collectors.toList());
            taskResultService.saveBatch(taskResults);

            QueryWrapper<TaskUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(TaskUser::getTaskId,list.get(0).get("taskId").toString())
                    .eq(TaskUser::getUserId,userId);
            TaskUser taskUser = taskUserService.getOne(queryWrapper);
            taskUser.setFinishFlag("1");
            taskUserService.updateById(taskUser);
        }
        return true;
    }

    @Override
    public String getTaskFinish(String taskId, String userId) {
        QueryWrapper<TaskUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TaskUser::getTaskId,taskId)
                        .eq(TaskUser::getUserId,userId);
        TaskUser taskUser = taskUserService.getOne(queryWrapper);
        return taskUser.getFinishFlag();
    }

    @Override
    public List<TaskResult> getTaskResult(String taskId, String userId) {
        QueryWrapper<TaskResult> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TaskResult::getTaskId,taskId)
                .eq(TaskResult::getUserId,userId);
        return taskResultService.list(queryWrapper);
    }

    @Override
    public TaskUserVo getTaskMember(String taskId) {
        List<TaskUserDetailVo> taskUsers = taskUserService.getUserList(taskId);
        TaskUserVo taskUserVo = new TaskUserVo();
        if(CollectionUtils.isNotEmpty(taskUsers)){
            Map<String, List<TaskUserDetailVo>> listMap = taskUsers.stream().collect(Collectors.groupingBy(TaskUserDetailVo::getFinishFlag));
            taskUserVo.setFinishMember(listMap.get("1"));
            taskUserVo.setUnFinishMember(listMap.get("0"));
        }
        return taskUserVo;
    }

    @Override
    public List<ClassRankVo> getRank(String classId) {
        return classUserMapper.getRank(classId);
    }
}
