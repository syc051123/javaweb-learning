package com.shyc.controller;

import com.shyc.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author shiyc
 * @date 2026/8/5
 */
@Slf4j
@RestController
public class UploadController {

    // 上传目录(application.yml 里也可配,这里先写死)
    private static final String UPLOAD_DIR = "D:/tlias-imgs/";

    /**
     * 文件上传
     * POST /upload  请求体是 multipart,字段名 file
     */

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {

        // ===== 请你在下面自己写 =====
        // 目标: 把前端传上来的 file 存到 D:/tlias-imgs/ 下,返回访问 URL
        String originalFilename=file.getOriginalFilename();
        // hint ① 拿原始文件名有现成方法: file.getOriginalFilename()
        String ext=originalFilename.substring(originalFilename.lastIndexOf("."));
        // hint ② 截取后缀用: originalFilename.substring(originalFilename.lastIndexOf("."))
        String newFileName= UUID.randomUUID()+ext;
        // hint ③ 生成唯一名用: UUID.randomUUID() 拼上后缀
        File dir=new File(UPLOAD_DIR);
        // hint ④ 保存用: file.transferTo(new File(路径))  —— 注意目录可能不存在,先 mkdirs
        if(!dir.exists()){
            dir.mkdirs();
        }

        File saveFile = new File(dir, newFileName);
        file.transferTo(saveFile);

        String url = "http://localhost:8080/images/" + newFileName;

        log.info("文件上传成功,访问地址: {}", url);

        return Result.success(url);

    }
}
