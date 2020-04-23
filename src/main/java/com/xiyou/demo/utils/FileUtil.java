package com.xiyou.demo.utils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Author: wangyu
 * @Date: 2020/4/16 11:29
 */

public class FileUtil {

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

}
