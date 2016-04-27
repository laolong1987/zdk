package com.web.controller;

import com.utils.StringUtil;
import com.web.entity.Demo;
import com.web.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyang on 16/2/28.
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    DemoService demoService;

    @RequestMapping(value = "/show")
    public String show(HttpServletRequest request,
                                HttpServletResponse response) {

        request.setAttribute("list",demoService.searchList(new HashMap()));

        return "/jsp/demo";
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request,
                                         HttpServletResponse response) {

        String id= StringUtil.safeToString(request.getParameter("id"),"");
        String name= StringUtil.safeToString(request.getParameter("name"),"");

        Demo demo=new Demo();
        if(!"".equals(id)){
            demo=demoService.get(Integer.parseInt(id));
        }
        demo.setName(name);
        demoService.save(demo);
        return show(request,response);
    }

    @RequestMapping(value = "/del")
    public void del(HttpServletRequest request,
                      HttpServletResponse response) {

        String id= StringUtil.safeToString(request.getParameter("id"),"");

        if(!"".equals(id)){
            demoService.del(Integer.parseInt(id));
        }

    }


    @RequestMapping(value = "/showupfile")
    public String demo(HttpServletRequest request,
                       HttpServletResponse response) {

        return "/jsp/demoupfile";

    }

    /**
     * 上传文件测试
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(method= RequestMethod.POST,value = "/upload")
    public String upload(HttpServletRequest request,HttpServletResponse response) {

        String name=request.getParameter("name");
        System.out.println(name);

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        /**构建图片保存的目录**/
        String logoPathDir = "/images/empimg";
        /**得到图片保存目录的真实路径**/
        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
        /**根据真实路径创建目录**/
        File logoSaveFile = new File(logoRealPathDir);
        if(!logoSaveFile.exists())
            logoSaveFile.mkdirs();
        /**页面控件的文件流**/
        MultipartFile multipartFile = multipartRequest.getFile("file");
        /**获取文件的后缀**/
        String suffix = multipartFile.getOriginalFilename().substring
                (multipartFile.getOriginalFilename().lastIndexOf("."));
//        /**使用UUID生成文件名称**/
//        String logImageName = UUID.randomUUID().toString()+ suffix;//构建文件名称
        String logImageName = multipartFile.getOriginalFilename();
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


        return "ok";

    }
}
