package com.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.utils.ConvertUtil;
import com.utils.SettingUtil;
import com.web.service.UserService;
import com.web.service.WeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by gaoyang on 16/3/1.
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    WeiXinService weiXinService;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/userlogin")
    public String userlogin(HttpServletRequest request,
                                HttpServletResponse response) {

        //授权登陆
        String url=weiXinService.getAuthorizeURL(SettingUtil.getSetting("redirectURL"));
        return "redirect:"+url;
    }

    @RequestMapping(value = "/userlogin2")
    public String userlogin2(HttpServletRequest request,
                            HttpServletResponse response) {
        String code= ConvertUtil.safeToString(request.getParameter("code"),"");
        String state= ConvertUtil.safeToString(request.getParameter("state"),"");
        JSONObject jsonObject=weiXinService.getAccessTokenByCode(code);
//        System.out.println("--1------------"+code);
        if(null!=jsonObject){
//            System.out.println("--2------------"+jsonObject.toJSONString());
            String openid=ConvertUtil.safeToString(jsonObject.getString("openid"),"");
            String access_token=ConvertUtil.safeToString(jsonObject.getString("access_token"),"");
            if(!"".equals(openid)){
//                System.out.println("--3------------"+openid);
                Map user= userService.getUserByOpenId(openid);
                if(null==user){
                    //新增用户
//                    System.out.println("--4------------"+access_token);
                    user = userService.saveUser(openid,0,access_token);
                }

                request.getSession().setAttribute("user",user);
                request.getSession().setAttribute("username",openid);
                request.getSession().setAttribute("userid",user.get("id"));
                return "redirect:/zdk/index";

            }else{
                return "/jsp/manage/error";
            }
        }
        return "/jsp/manage/login";
    }

    @RequestMapping(value = "/adminlogin")
    public String adminlogin(HttpServletRequest request,
                        HttpServletResponse response) {

        return "/jsp/manage/login";
    }

    @RequestMapping(value = "/adminlogin2")
    public String adminlogin2(HttpServletRequest request,
                             HttpServletResponse response) {

        String username=ConvertUtil.safeToString(request.getParameter("username"),"");
        String pwd=ConvertUtil.safeToString(request.getParameter("password"),"");

        if(userService.checkAdminBypwd(username,pwd)){
            request.getSession().setAttribute("adminname",username);
            return "redirect:/admin/index";
        }else{
            return "/jsp/manage/login";
        }
    }
}
