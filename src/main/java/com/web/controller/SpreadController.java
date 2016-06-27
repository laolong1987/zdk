package com.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.UpdateFile;
import com.utils.*;
import com.web.entity.Spread;
import com.web.entity.Task;
import com.web.entity.Task_checktype;
import com.web.entity.User_task;
import com.web.service.TaskService;
import com.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * Created by gaoyang on 16/2/28.
 */
@Controller
@RequestMapping("/admin/spread")
public class SpreadController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/showlist")
    public String showlist(HttpServletRequest request,
                                HttpServletResponse response) {

        return "/jsp/manage/listspread";
    }

    @RequestMapping(value = "/searchlist",method = RequestMethod.POST)
    @ResponseBody
    public Object searchlist(@RequestBody Map<String, String> param){
        return userService.searchSpread(param).getResult();
    }


    @RequestMapping(value = "/updatespread",method = RequestMethod.POST)
    @ResponseBody
    public String updatespread(HttpServletRequest request,
                              HttpServletResponse response) {
        int upid = ConvertUtil.safeToInteger(request.getParameter("upid"), 0);
        int status = ConvertUtil.safeToInteger(request.getParameter("status"), 0);
        String remark=ConvertUtil.safeToString(request.getParameter("remark"),"");

        Spread spread=userService.getSpreadById(upid);
        if(null!=spread){
            spread.setRemark(remark);
            spread.setUpdate_time(new Date());
            spread.setStatus(status);
            if(1==status){
                spread.setLoginname(spread.getPhone());
                spread.setPwd("123456");
            }
            userService.saveSpread(spread);
        }


        return "success";
    }

    @RequestMapping(value = "/remove",method = RequestMethod.POST)
    @ResponseBody
    public Object removetask(@RequestBody List<Map> params){
        List<Integer> ids = new ArrayList<Integer>();
        for (Map map : params) {
            ids.add(Integer.parseInt(map.get("id").toString()));
        }
        taskService.removeTask(ids);
        return "success";
    }
}
