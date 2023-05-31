package cn.lxsir.uniapp.vo;

import cn.lxsir.uniapp.entity.TaskUser;
import lombok.Data;

import java.util.List;

@Data
public class TaskUserVo {
    List<TaskUserDetailVo> finishMember;
    List<TaskUserDetailVo> unFinishMember;
}
