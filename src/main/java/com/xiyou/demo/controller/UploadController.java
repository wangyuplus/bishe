package com.xiyou.demo.controller;

import com.xiyou.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: wangyu
 * @Date: 2020/1/28 12:45
 */
@Controller
@CrossOrigin
@ResponseBody
public class UploadController {

    @Autowired
    GoodsService goodsService;

    @PostMapping("upload")

    public String fileUpload(@RequestParam("file") MultipartFile srcFile, RedirectAttributes redirectAttributes) {
//前端没有选择文件，srcFile为空
       /* if(srcFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "请选择一个文件");
            return "redirect:upload_status";
        }*/
//选择了文件，开始上传操作
        try {
//构建上传目标路径，找到了项目的target的classes目录
            File destFile = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!destFile.exists()) {
                destFile = new File("");
            }
//输出目标文件的绝对路径
            System.out.println("file path:" + destFile.getAbsolutePath());
//拼接子路径
            SimpleDateFormat sf_ = new SimpleDateFormat("yyyyMMddHHmmss");
            String times = sf_.format(new Date());
            File upload = new File(destFile.getAbsolutePath(), "picture/");
//若目标文件夹不存在，则创建
            if (!upload.exists()) {
                upload.mkdirs();
            }
            System.out.println("完整的上传路径：" + upload.getAbsolutePath() + "/" + srcFile);
            //goodsService.addPath(upload.getAbsolutePath()+"/"+srcFile);
//根据srcFile大小，准备一个字节数组
            byte[] bytes = srcFile.getBytes();
//拼接上传路径
//Path path = Paths.get(UPLOAD_FOLDER + srcFile.getOriginalFilename());
//通过项目路径，拼接上传路径

            Path path = Paths.get(upload.getAbsolutePath() + "/" + srcFile.getOriginalFilename());
            String aaa = String.valueOf(path);
            goodsService.addPath(aaa);

            //** 开始将源文件写入目标地址
            Files.write(path, bytes);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
// 获得文件原始名称
            String fileName = srcFile.getOriginalFilename();
// 获得文件后缀名称
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
// 生成最新的uuid文件名称
            String newFileName = uuid + "." + suffixName;
            redirectAttributes.addFlashAttribute("message", "文件上传成功" + newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "111";
    }

    //根据gid获取path
    @RequestMapping("getPath")
    public String aa(int gid) {

        String path = goodsService.findPathByGid(gid);

        return path;
    }

}
