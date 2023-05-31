package cn.lxsir.uniapp.controller;

import cn.lxsir.uniapp.dto.DeleteUserDto;
import cn.lxsir.uniapp.dto.EnterDto;
import cn.lxsir.uniapp.entity.Class;
import cn.lxsir.uniapp.entity.ClassUser;
import cn.lxsir.uniapp.service.ClassService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/class")
@Slf4j
public class ClassController {

    @Autowired
    ClassService classService;

    @PostMapping("/save")
    public R save(@RequestBody Class clazz){
        return R.ok(classService.save(clazz));
    }

    @PostMapping("/update")
    public R update(@RequestBody Class clazz){
        return R.ok(classService.updateById(clazz));
    }

    @PostMapping("/enter")
    public R enterClass(@RequestBody EnterDto enterDto){
        try {
            return R.ok(classService.enterClass(enterDto));
        }catch (Exception e){
            log.info(e.getMessage());
            return R.failed("加入班级失败，请联系客服");
        }
    }

    @GetMapping("/list")
    public R getList( @RequestParam(required = false,name = "pageNo") Integer pageNo,
                      @RequestParam(required = false,name = "pageSize") Integer pageSize,
                      @RequestParam(required = false,name = "userId") String userId){
        if( pageSize == null ) {
            pageSize=5;
        }
        List<Class> list = null;
        if(userId != null) {
            list = classService.list(pageNo, pageSize, userId);
        }
        return R.ok(list);
    }

    @GetMapping("/listNotEnter")
    public R getListNotEnter( @RequestParam(required = false,name = "pageNo") Integer pageNo,
                      @RequestParam(required = false,name = "pageSize") Integer pageSize,
                      @RequestParam(required = false,name = "userId") String userId){
        if( pageSize == null ) {
            pageSize=50;
        }
        List<Class> list = null;
        if(userId != null) {
            list = classService.listNotIn(pageNo, pageSize, userId);
        }
        return R.ok(list);
    }

    @GetMapping("/infoByCode")
    public R getInfo(@RequestParam("code") String code,@RequestParam(name = "userId") String userId){
        List<Class> list = classService.listNotIn(1, 5, userId);
        List<Class> listByCode = null;
        if(CollectionUtils.isNotEmpty(list)){
            listByCode = list.stream().filter(aClass -> aClass.getClassCode().equals(code)||aClass.getClassName().equals(code)).collect(Collectors.toList());
        }
        if (listByCode == null){
            listByCode = new ArrayList<>(1);
        }
        return R.ok(listByCode);
    }

    @GetMapping("/infoById")
    public R getInfoById(@RequestParam("classId") String classId, @RequestParam("userId") String userId){
        Class aClass = null;
        ClassUser classUser = null;
        if(StringUtils.isNotEmpty(classId) && userId != null){
            aClass = classService.getById(classId);
            if(aClass == null){
                return R.failed("未找到");
            }
            classUser = classService.getClassUser(classId, userId);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("classInfo",aClass);
        map.put("classUser",classUser);
        return R.ok(map);
    }

    @GetMapping("/quit")
    public R deleteUser(DeleteUserDto deleteUserDto){
        return R.ok(classService.deleteUser(deleteUserDto));
    }

    @GetMapping("/memberList")
    public R getMemberList(@RequestParam("classId") String classId){
        return R.ok(classService.getMemberList(classId));
    }

}
