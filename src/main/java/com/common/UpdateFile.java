package com.common;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by gaoyang on 2016/4/21.
 */
public class UpdateFile {

    public String up(HttpServletRequest request,String pname){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        /**构建图片保存的目录**/
        String logoPathDir = "/images/taskimg";
        /**得到图片保存目录的真实路径**/
        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
        /**根据真实路径创建目录**/
        File logoSaveFile = new File(logoRealPathDir);
        if(!logoSaveFile.exists())
            logoSaveFile.mkdirs();
        /**页面控件的文件流**/
        MultipartFile multipartFile = multipartRequest.getFile(pname);

        if("".equals(multipartFile.getOriginalFilename())){
            return "null";
        }

        /**获取文件的后缀**/
        String suffix = multipartFile.getOriginalFilename().substring
                (multipartFile.getOriginalFilename().lastIndexOf("."));
//        /**使用UUID生成文件名称**/
        String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称
//        String logImageName = multipartFile.getOriginalFilename();
        /**拼成完整的文件保存路径加文件**/
        String fileName = logoRealPathDir + File.separator   + logImageName;
        File file = new File(fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logImageName;
    }
}
