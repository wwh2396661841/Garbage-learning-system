package cn.lxsir.uniapp.service;

import cn.lxsir.uniapp.dto.DeleteUserDto;
import cn.lxsir.uniapp.dto.EnterDto;
import cn.lxsir.uniapp.entity.Class;
import cn.lxsir.uniapp.entity.ClassUser;
import cn.lxsir.uniapp.vo.MembersVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ClassService extends IService<Class> {
    boolean enterClass(EnterDto enterDto);

    List<Class> list(Integer pageNo, Integer pageSize, String userId);

    List<Class> listNotIn(Integer pageNo, Integer pageSize, String userId);

    boolean deleteUser(DeleteUserDto deleteUserDto);

    MembersVo getMemberList(String classId);

    ClassUser getClassUser(String classId, String userId);

}
