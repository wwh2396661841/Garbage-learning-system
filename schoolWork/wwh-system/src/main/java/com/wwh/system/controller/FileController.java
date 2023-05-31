package com.wwh.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wwh.schoolWork.common.core.domain.Result;
import com.wwh.system.domain.SystemUser;
import com.wwh.system.service.SystemUserService;
import com.wwh.system.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/avatar")
public class FileController {
    @Autowired
    private SystemUserService systemUserService;

    @PostMapping("/save")
    public Result saveAvatar(String userId,MultipartFile file){
        String savePath = "D:/ruoyi/uploadPath/avatar";

        try {
            String filePath = FileUtil.saveFile(savePath, file);
            SystemUser systemUser = systemUserService.queryById(userId);
            systemUser.setAvatar(filePath);
            systemUserService.update(systemUser);
            return Result.success(filePath);
        } catch (Exception e) {
            return Result.fail();
        }
    }

    @GetMapping("/getByUserId")
    public void getAvatar(@RequestParam("userId") String userId, HttpServletResponse response)  {

        SystemUser systemUser = systemUserService.queryById(userId);
        String avatar = systemUser.getAvatar();
        if(StringUtils.isEmpty(avatar)){
            return;
        }
        try (InputStream inputStream = new FileInputStream(avatar);
             OutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
