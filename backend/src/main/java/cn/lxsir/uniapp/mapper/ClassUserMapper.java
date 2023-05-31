package cn.lxsir.uniapp.mapper;

import cn.lxsir.uniapp.dto.DeleteUserDto;
import cn.lxsir.uniapp.entity.ClassUser;
import cn.lxsir.uniapp.vo.ClassRankVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassUserMapper extends BaseMapper<ClassUser> {
    List<ClassUser> getListNotEnter(@Param("userId") String userId);

    boolean deleteUser(DeleteUserDto deleteUserDto);

    boolean deleteUserBatch(@Param("classId") String classId);

    List<ClassRankVo> getRank(@Param("classId")String classId);
}
