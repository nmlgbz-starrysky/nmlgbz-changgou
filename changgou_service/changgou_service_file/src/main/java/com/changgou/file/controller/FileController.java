package com.changgou.file.controller;

import com.changgou.entity.Result;
import com.changgou.entity.StatusCode;
import com.changgou.file.util.FastDFSClient;
import com.changgou.file.util.FastDFSFile;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping("/uploadFile")
    public Result uploadFile(MultipartFile file) {
        try {
            //判断文件是否存在
            if (file == null) {
                throw new RuntimeException("文件不存在");
            }
            //判断文件名是否完整
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isEmpty(originalFilename)) {
                throw new RuntimeException("文件不存在");
            }
            //获取文件的扩展名
            String exctFile = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //获取文件的内容
            byte[] bytes = file.getBytes();
            //创建文件上传的实体类
            FastDFSFile fastDFSFile = new FastDFSFile(originalFilename, bytes, exctFile);
            //基于工具进行文件上传并返回参数
            String[] uploadResult = FastDFSClient.upload(fastDFSFile);
            //封装返回结果
            String url = FastDFSClient.getTrackerUrl() + uploadResult[0] + "/" + uploadResult[1];
            return new Result(true, StatusCode.OK, "文件上传成功", url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "文件上窜失败");
        }
    }
}
