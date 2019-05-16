package com.xerplus.xinfo.service;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.xerplus.xinfo.controller.NewsController;
import com.xerplus.xinfo.util.XinfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class QiniuService {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    Configuration cfg = new Configuration(Zone.zone0());
    UploadManager uploadManager = new UploadManager(cfg);
    String accessKey = "***";
    String secretKey = "***";
    String bucket = "fxx";
    Auth auth = Auth.create(accessKey, secretKey);
    String upToken = auth.uploadToken(bucket);

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }

            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!XinfoUtil.isImageFileAllowed(fileExt)) {
                return null;
            }

            String imageFileName = UUID.randomUUID().toString().replaceAll("-", "");
            Response response = uploadManager.put(file.getBytes(), imageFileName, upToken);
            if (response.isJson()) {
                return XinfoUtil.QINIU_DOMAIN + "/"
                        + JSONObject.parseObject(response.bodyString()).get("key");
            } else {
                logger.error("七牛异常:" + response.bodyString());
                return null;
            }
        } catch (Exception e) {
            logger.error("异常:" + e.getMessage());
            return null;
        }
    }
}
