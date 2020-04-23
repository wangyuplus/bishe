package com.xiyou.demo.controller;

import com.xiyou.demo.utils.FileUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wangyu
 * @Date: 2020/4/18 11:39
 */
@RestController
@RequestMapping("/api")
public class fileupload {
    @RequestMapping(value = "/upload",method ={RequestMethod.POST})
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request){
        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials","true");
        String fileName=null;
        if(!file.isEmpty()) {
            fileName = file.getOriginalFilename();
            String filePath="/www/server/apache-tomcat-8.5.32/webapps/ROOT/";
            try {
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
            } catch (Exception e) {
                return "添加失败";
            }
        }

        return fileName;
    }
}
