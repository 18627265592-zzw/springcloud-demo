package com.eastday.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
public class UploadUtils {

    /**
     * 所有文件上传
     *
     * @param filePath 文件上传路径
     * @param fileName 文件名
     * @param visitUrl 访问域名
     * @param file     文件
     * @return 文件访问url
     */
    public static String uploadAll(String filePath, String fileName, String visitUrl, MultipartFile file) {
        String returnstr = null;
        try {
            File files = new File(filePath + fileName);
            if (!files.getParentFile().exists()) {
                boolean result = files.getParentFile().mkdirs();
                if (!result) {
                    log.debug("创建文件失败");
                }
            }
            if (!files.exists()) {
                files.createNewFile();
            }
            file.transferTo(files);
            returnstr = visitUrl + fileName;
            log.debug("上传成功");
        } catch (Exception e) {
            returnstr = "false";
            e.printStackTrace();
        }
        return returnstr;
    }
}
