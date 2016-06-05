package com.web.controller;

import com.common.UpdateFile;
import com.utils.ConvertUtil;
import com.web.entity.*;
import com.web.service.TaskService;
import com.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoyang on 16/2/28.
 */
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    TaskService taskService;


    @RequestMapping(value = "/task")
    public String task(HttpServletRequest request,
                        HttpServletResponse response) {

        return "/jsp/user/submitbatch";
    }


    @RequestMapping(value = "/searchtask")
    public String searchtask(HttpServletRequest request,
                       HttpServletResponse response) {
        String name= ConvertUtil.safeToString(request.getParameter("name"),"");
        String phone= ConvertUtil.safeToString(request.getParameter("phone"),"");

        if(!"".equals(name) && !"".equals(phone)){
            request.setAttribute("list",taskService.findTask_batchByname(name,phone));
        }
        return "/jsp/user/searchtask";
    }

    @RequestMapping(value = "/submit_task",method = RequestMethod.POST)
    @ResponseBody
    public String submit_task(HttpServletRequest request,
                           HttpServletResponse response) {
        String name= ConvertUtil.safeToString(request.getParameter("name"),"");
        String phone= ConvertUtil.safeToString(request.getParameter("phone"),"");
        String teamname= ConvertUtil.safeToString(request.getParameter("teamname"),"");
        String alipay= ConvertUtil.safeToString(request.getParameter("alipay"),"");
        String qq=ConvertUtil.safeToString(request.getParameter("qq"),"");
        String weixin=ConvertUtil.safeToString(request.getParameter("weixin"),"");
        String taskname=ConvertUtil.safeToString(request.getParameter("taskname"),"");
        UpdateFile updateFile=new UpdateFile();
        String filename=updateFile.up(request,"file","/file");

        if(!"".equals(filename) && !"".equals(name) && !"".equals(phone)){
            Task_batch task_batch=new Task_batch();
            task_batch.setUpdate_time(new Date());
            task_batch.setAlipay(alipay);
            task_batch.setFilename(filename);
            task_batch.setPhone(phone);
            task_batch.setQq(qq);
            task_batch.setWeixin(weixin);
            task_batch.setTeamname(teamname);
            task_batch.setTaskname(taskname);
            task_batch.setName(name);
            task_batch.setCreate_time(new Date());
            task_batch.setStatus(0);
            taskService.saveTaskBatch(task_batch);
        }
        return "success";
    }
}
