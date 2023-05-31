package cn.lxsir.uniapp.vo;


import cn.lxsir.uniapp.entity.TaskQuestion;
import lombok.Data;

import java.util.List;

@Data
public class TaskVo {

    private String taskTime;

    private String releaseUser;

    private String taskId;

    private List<TaskQuestion> questions;

    private String finishNum;

    private String unFinishNum;

    private boolean finishFlag;
}
