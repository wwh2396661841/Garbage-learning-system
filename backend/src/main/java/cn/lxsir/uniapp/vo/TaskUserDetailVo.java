package cn.lxsir.uniapp.vo;

import cn.lxsir.uniapp.entity.TaskUser;
import lombok.Data;

@Data
public class TaskUserDetailVo extends TaskUser {


    private String nickName;

    private String phone;

}
