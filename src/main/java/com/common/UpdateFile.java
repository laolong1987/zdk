package com.common;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
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

    public List<String> upload2(HttpServletRequest request) throws IllegalStateException, IOException {
        List list=new ArrayList();
        /**构建图片保存的目录**/
        String logoPathDir = "/images/taskimg";
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if(file != null){
                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if(myFileName.trim() !=""){
//                        System.out.println(myFileName);
                        //定义上传路径
                        /**获取文件的后缀**/
                        String suffix = file.getOriginalFilename().substring
                                (file.getOriginalFilename().lastIndexOf("."));
                        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
                        String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称
                        String fileName = logoRealPathDir + File.separator   + logImageName;
                        File localFile = new File(fileName);
                        file.transferTo(localFile);
                        list.add(logImageName);
                    }
                }
            }
        }
       return  list;
    }
}
