package com.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.UpdateFile;
import com.utils.ConvertUtil;
import com.utils.DateUtil;
import com.web.entity.Patient;
import com.web.entity.Task;
import com.web.entity.Task_check;
import com.web.service.PatientService;
import com.web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by gaoyang on 16/2/28.
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/showlist")
    public String showlist(HttpServletRequest request,
                                HttpServletResponse response) {
        return "/jsp/manage/listtask";
    }

    @RequestMapping(value = "/searchlist",method = RequestMethod.POST)
    @ResponseBody
    public Object searchlist(@RequestBody Map<String, String> param){

        return taskService.searchTask(param).getResult();
    }


    @RequestMapping(value = "/savetask",method = RequestMethod.POST)
    @ResponseBody
    public String savetask(HttpServletRequest request,
                              HttpServletResponse response) {
        int taskid= ConvertUtil.safeToInteger(request.getParameter("taskid"),0);
        String name=ConvertUtil.safeToString(request.getParameter("name"),"");
        int type= ConvertUtil.safeToInteger(request.getParameter("type"),0);
        int unitprice= ConvertUtil.safeToInteger(request.getParameter("unitprice"),0);
        int total= ConvertUtil.safeToInteger(request.getParameter("total"),0);
        int status= ConvertUtil.safeToInteger(request.getParameter("status"),0);
        String begintime= ConvertUtil.safeToString(request.getParameter("begintime"),"");
        String endtime= ConvertUtil.safeToString(request.getParameter("endtime"),"");
        String keyword= ConvertUtil.safeToString(request.getParameter("keyword"),"");
        String description= ConvertUtil.safeToString(request.getParameter("description"),"");
        String content=ConvertUtil.safeToString(request.getParameter("content"),"");
        String data=ConvertUtil.safeToString(request.getParameter("tabledata"),"");

        Task task=new Task();
        if(0!=taskid) {
            task = (Task) taskService.getTaskById(taskid);
        }else{
            task.setCreate_time(new Date());
        }
        UpdateFile updateFile=new UpdateFile();
        String filename=updateFile.up(request,"file");
        if(!"null".equals(filename)){
            task.setLogoimg(filename);
        }
        task.setName(name);
        task.setType(type);
        task.setBegintime(DateUtil.String2Date(begintime,"yyyy-MM-dd HH:mm"));
        task.setEndtime(DateUtil.String2Date(endtime,"yyyy-MM-dd HH:mm"));
        task.setUnitprice(unitprice);
        task.setTotal(total);
        task.setStatus(status);
        task.setKeyword(keyword);
        task.setDescription(description);
        task.setContent(content);
        task.setUpdate_time(new Date());
        taskService.saveTask(task);

        taskService.removeTaskClickByTaskId(task.getId());
        JSONArray jsonArray= JSON.parseArray(data);
        for(int k=0;k<jsonArray.size();k++){
            JSONObject json= JSON.parseObject(jsonArray.get(k).toString()) ;
            Task_check taskcheck=new Task_check();
            taskcheck.setName(json.getString("name"));
            taskcheck.setType(json.getInteger("type"));
            taskcheck.setCreate_time(new Date());
            taskcheck.setUpdate_time(new Date());
            taskcheck.setMaxlength(json.getInteger("maxlength"));
            taskcheck.setMinlength(json.getInteger("minlength"));
            taskcheck.setRegular(json.getString("regular"));
            taskcheck.setTaskid(task.getId());
            taskService.saveTaskCheck(taskcheck);
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

    @RequestMapping(value = "/gettaskcheck",method = RequestMethod.POST)
    @ResponseBody
    public Object gettaskcheck(HttpServletRequest request,HttpServletResponse response){
        int taskid= ConvertUtil.safeToInteger(request.getParameter("taskid"),0);
        List list=taskService.searchTaskClickByTaskId(taskid);
        return list;
    }

}
