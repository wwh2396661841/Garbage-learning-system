package cn.lxsir.uniapp.service.impl;

import cn.lxsir.uniapp.dto.DeleteUserDto;
import cn.lxsir.uniapp.dto.EnterDto;
import cn.lxsir.uniapp.entity.Class;
import cn.lxsir.uniapp.entity.ClassUser;
import cn.lxsir.uniapp.mapper.ClassMapper;
import cn.lxsir.uniapp.mapper.ClassUserMapper;
import cn.lxsir.uniapp.service.ClassService;
import cn.lxsir.uniapp.util.CTools;
import cn.lxsir.uniapp.vo.MembersVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    @Autowired
    ClassMapper classMapper;

    @Autowired
    ClassUserMapper classUserMapper;

    @Transactional
    public boolean save(Class entity) {
        entity.setId(CTools.getNewID());
        entity.setClassUserNum(1);
        classMapper.insert(entity);
        ClassUser classUser = new ClassUser();
        classUser.setId(CTools.getNewID());
        classUser.setClassId(entity.getId());
        classUser.setClassName(entity.getClassName());
        classUser.setUserId(entity.getUserId());
        classUser.setUserType("1");
        classUser.setUserName(entity.getUserName());
        classUser.setNickName(entity.getNickName());
        classUserMapper.insert(classUser);
        return true;
    }

    @Override
    public boolean enterClass(EnterDto enterDto) {
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Class::getId,enterDto.getClassId());
        Class aClass = this.getOne(queryWrapper);
        if(aClass == null){
            throw new RuntimeException("班级不存在");
        }
        ClassUser classUser = new ClassUser();
        classUser.setId(CTools.getNewID());
        classUser.setClassId(aClass.getId());
        classUser.setUserName(enterDto.getUserName());
        classUser.setNickName(enterDto.getNickName());
        classUser.setUserType("0");
        classUser.setClassName(aClass.getClassName());
        classUser.setUserId(enterDto.getUserId());
        classUserMapper.insert(classUser);

        aClass.setClassUserNum(aClass.getClassUserNum()+1);
        save(aClass);
        return true;
    }

    @Override
    public List<Class> list(Integer pageNo, Integer pageSize, String userId) {
        Page page = new Page();
        page.setSize(pageSize);
        page.setCurrent(pageNo);

        QueryWrapper<ClassUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClassUser::getUserId,userId);
        List<ClassUser> classUsers = classUserMapper.selectList(queryWrapper);
        List<Class> classList = null;
        if(classUsers != null){
            List<String> classIds = classUsers.stream().map(classUser -> classUser.getClassId()).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(classIds)){
                QueryWrapper<Class> queryWrapperClass = new QueryWrapper<>();
                queryWrapperClass.lambda().in(Class::getId,classIds);
                IPage iPage= classMapper.selectPage(page,queryWrapperClass);
                classList = iPage.getRecords();
            }
        }

        return classList;
    }

    @Override
    public List<Class> listNotIn(Integer pageNo, Integer pageSize, String userId) {
        Page page = new Page();
        page.setSize(pageSize);
        page.setCurrent(pageNo);
        //查询为加入班级
        List<ClassUser> classUsers = classUserMapper.getListNotEnter(userId);

        List<Class> classList = null;
        if(classUsers != null){
            List<String> classIds = classUsers.stream().map(classUser -> classUser.getClassId()).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(classIds)){
                QueryWrapper<Class> queryWrapperClass = new QueryWrapper<>();
                queryWrapperClass.lambda().in(Class::getId,classIds);
                IPage iPage= classMapper.selectPage(page,queryWrapperClass);
                classList = iPage.getRecords();
            }
        }

        return classList;
    }

    @Override
    public boolean deleteUser(DeleteUserDto deleteUserDto) {
        Class aClass = classMapper.selectById(deleteUserDto.getClassId());

        QueryWrapper<ClassUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClassUser::getUserId,deleteUserDto.getUserId())
                        .eq(ClassUser::getClassId,deleteUserDto.getClassId());
        ClassUser classUser = classUserMapper.selectOne(queryWrapper);

        if("1".equals(classUser.getUserType())){
            classMapper.deleteById(deleteUserDto.getClassId());
            return classUserMapper.deleteUserBatch(deleteUserDto.getClassId());
        }else {
            aClass.setClassUserNum(aClass.getClassUserNum()-1);
            save(aClass);
            return classUserMapper.deleteUser(deleteUserDto);
        }


    }

    @Override
    public MembersVo getMemberList(String classId) {
        QueryWrapper<ClassUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClassUser::getClassId,classId);
        List<ClassUser> classUserList = classUserMapper.selectList(queryWrapper);
        MembersVo membersVo = new MembersVo();
        if(CollectionUtils.isNotEmpty(classUserList)){
            Map<String, List<ClassUser>> map = classUserList.stream().collect(Collectors.groupingBy(ClassUser::getFirstName));
            Set<String> keySet = map.keySet();
            List<String> keyList = new ArrayList<>(keySet.size());
            List<List> memberList = new ArrayList<>(classUserList.size());
            keySet.forEach( key ->{
                keyList.add(key);
                memberList.add(map.get(key));
            });
            membersVo.setMemberList(memberList);
            membersVo.setKeyList(keyList);
        }
        return membersVo;
    }

    @Override
    public ClassUser getClassUser(String classId, String userId) {
        QueryWrapper<ClassUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ClassUser::getClassId,classId)
                .eq(ClassUser::getUserId,userId);
        return classUserMapper.selectOne(queryWrapper);
    }
}
