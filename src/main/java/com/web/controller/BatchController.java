package com.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.UpdateFile;
import com.utils.ConvertUtil;
import com.utils.DateUtil;
import com.web.entity.Task;
import com.web.entity.Task_batch;
import com.web.entity.Task_check;
import com.web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoyang on 16/2/28.
 */
@Controller
@RequestMapping("/admin/batch")
public class BatchController {

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/showlist")
    public String showlist(HttpServletRequest request,
                                HttpServletResponse response) {
        return "/jsp/manage/listtaskbatch";
    }

    @RequestMapping(value = "/searchlist",method = RequestMethod.POST)
    @ResponseBody
    public Object searchlist(@RequestBody Map<String, String> param){

        return taskService.searchTaskBatch(param).getResult();
    }


    @RequestMapping(value = "/updatenote",method = RequestMethod.POST)
    @ResponseBody
    public Object updatenote(HttpServletRequest request,
                             HttpServletResponse response) {
        int id = ConvertUtil.safeToInteger(request.getParameter("id"), 0);
        String note = ConvertUtil.safeToString(request.getParameter("note"), "");
        if (0 != id) {
            Task_batch task_batch=taskService.getUserTask_batchById(id);
            if(null!=task_batch){
                task_batch.setRemark(note);
                UpdateFile updateFile=new UpdateFile();
                String filename=updateFile.up(request,"file","/file");
                task_batch.setFilename2(filename);
                task_batch.setUpdate_time(new Date());
                task_batch.setStatus(1);
                taskService.saveTaskBatch(task_batch);
            }
        }
        return "success";
    }
}
