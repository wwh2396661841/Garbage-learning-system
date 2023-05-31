package cn.lxsir.uniapp.mapper;

import cn.lxsir.uniapp.entity.TaskUser;
import cn.lxsir.uniapp.vo.TaskUserDetailVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskUserMapper extends BaseMapper<TaskUser> {
    List<TaskUserDetailVo> getListByTaskId(@Param("taskId") String taskId);
}
