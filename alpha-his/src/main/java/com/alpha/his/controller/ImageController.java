package com.alpha.his.controller;

import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Value("${imgUpload.imgRoot}")
    private String imgRoot;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("rawtypes")
    @PostMapping("/upload")
    public ResponseMessage springUpload(HttpServletRequest request) throws IllegalStateException, IOException {
        //获取根路径路径
        String imgPath = "img" + getSeparator();
        //创建文件夹
        isExistDir(imgRoot + imgPath);
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();
            List<String> pathList = new ArrayList<String>();
            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    String uuid = getUUID();
                    String name = file.getOriginalFilename();
                    String path = imgPath + uuid + name.substring(name.lastIndexOf("."));
                    String fullPath = imgRoot + path;
                    //上传
                    file.transferTo(new File(fullPath));
                    pathList.add(fullPath);
                }
            }
            return WebUtils.buildSuccessResponseMessage(pathList);
        }
        return WebUtils.buildResponseMessage(ResponseStatus.FAIL);
    }

    protected String getSeparator() {
        return System.getProperty("file.separator");
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 判断多级路径是否存在，不存在就创建
     *
     * @param filePath 支持带文件名的Path：如：D:\news\2014\12\abc.text，和不带文件名的Path：如：D:\news\2014\12
     */
    public boolean isExistDir(String filePath) {
        boolean isExist = false;
        String paths[] = {""};
        //切割路径
        try {
            String tempPath = new File(filePath).getCanonicalPath();//File对象转换为标准路径并进行切割，有两种windows和linux
            paths = tempPath.split("\\\\");//windows
            if (paths.length == 1) {
                paths = tempPath.split("/");
            } //linux
        } catch (IOException e) {
            logger.error("切割路径失败！");
        }
        //判断是否有后缀
        boolean hasType = false;
        if (paths.length > 0) {
            String tempPath = paths[paths.length - 1];
            if (tempPath.length() > 0) {
                if (tempPath.indexOf(".") > 0) {
                    hasType = true;
                }
            }
        }
        //创建文件夹
        String dir = paths[0];
        for (int i = 0; i < paths.length - (hasType ? 2 : 1); i++) {// 注意此处循环的长度，有后缀的就是文件路径，没有则文件夹路径
            try {
                dir = dir + "/" + paths[i + 1];//采用linux下的标准写法进行拼接，由于windows可以识别这样的路径，所以这里采用警容的写法
                File dirFile = new File(dir);
                if (!dirFile.exists()) {
                    dirFile.mkdir();
                    isExist = true;
                    logger.info("成功创建目录：{}" + dirFile.getCanonicalFile());
                }
            } catch (Exception e) {
                logger.error("文件夹创建发生异常", e);
            }
        }

        return isExist;
    }
}
