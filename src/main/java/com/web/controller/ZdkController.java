package com.web.controller;

import com.common.UpdateFile;
import com.utils.ConvertUtil;
import com.web.entity.Task;
import com.web.entity.Task_form;
import com.web.entity.User_task;
import com.web.service.TaskService;
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
@RequestMapping("/zdk")
public class ZdkController {

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request,
                        HttpServletResponse response) {

        request.setAttribute("c1","");
        request.setAttribute("c2","");
        request.setAttribute("c3","");
        request.setAttribute("c4","");

        return "/jsp/user/index";
    }

    @RequestMapping(value = "/home")
    public String home(HttpServletRequest request,
                        HttpServletResponse response) {

        request.setAttribute("c1","");
        request.setAttribute("c2","");
        request.setAttribute("c3","");
        request.setAttribute("c4","on");

        return "/jsp/user/home";
    }


    @RequestMapping(value = "/task1")
    public String task1(HttpServletRequest request,
                        HttpServletResponse response) {
        request.setAttribute("typename", "快速任务");
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);
        List list = taskService.searchTaskByUser(userid,1);
        request.setAttribute("tasklist", list);
        request.setAttribute("c1","on");
        request.setAttribute("c2","");
        request.setAttribute("c3","");
        request.setAttribute("c4","");
        return "/jsp/user/listtask";
    }

    @RequestMapping(value = "/task2")
    public String task2(HttpServletRequest request,
                        HttpServletResponse response) {
        request.setAttribute("typename", "高分任务");
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);
        List list = taskService.searchTaskByUser(userid,2);
        request.setAttribute("tasklist", list);
        request.setAttribute("c1","");
        request.setAttribute("c2","on");
        request.setAttribute("c3","");
        request.setAttribute("c4","");
        return "/jsp/user/listtask";
    }

    @RequestMapping(value = "/getcontent", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object getcontent(HttpServletRequest request, HttpServletResponse response) {
        int taskid = ConvertUtil.safeToInteger(request.getParameter("taskid"), 0);
        Task task = taskService.getTaskById(taskid);
        if (null != task) {
            return task.getContent();
        } else {
            return "";
        }
    }

    @RequestMapping(value = "/starttask", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object starttask(HttpServletRequest request, HttpServletResponse response) {
        int taskid = ConvertUtil.safeToInteger(request.getParameter("taskid"), 0);
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);
        User_task user_task=new User_task();
        user_task.setUserid(userid);
        user_task.setStatus(0);
        user_task.setTaskid(taskid);
        user_task.setCreatetime(new Date());
        user_task.setUpdatetime(new Date());
        taskService.saveUserTask(user_task);

        //更新记录数
        Task task= taskService.getTaskById(taskid);
        task.setTotal(task.getTotal()-1);
        task.setUpdate_time(new Date());
        taskService.saveTask(task);
        return "";
    }

    @RequestMapping(value = "/gettaskcheck",method = RequestMethod.POST)
    @ResponseBody
    public Object gettaskcheck(HttpServletRequest request,HttpServletResponse response){
        int taskid= ConvertUtil.safeToInteger(request.getParameter("taskid"),0);
        List list=taskService.searchTaskClickByTaskId(taskid);
        return list;
    }

    @RequestMapping(value = "/uptask", method = RequestMethod.POST)
    public Object uptask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int taskid = ConvertUtil.safeToInteger(request.getParameter("taskid"), 0);
        int utid = ConvertUtil.safeToInteger(request.getParameter("utid"), 0);
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);
        int tasktype=ConvertUtil.safeToInteger(request.getParameter("tasktype"), 1);
        User_task user_task=taskService.getUserTaskById(utid);
        user_task.setStatus(1);
        user_task.setUpdatetime(new Date());
        taskService.saveUserTask(user_task);

        List<Map> list=taskService.searchTaskClickByTaskId(taskid);
        for (Map m:list) {
            String taskcheck = "b_"+ConvertUtil.safeToInteger(m.get("id"),0);
            String v= ConvertUtil.safeToString(request.getParameter(taskcheck), "");
            if(!"".equals(v)){
                Task_form tf=new Task_form();
                tf.setUpdatetime(new Date());
                tf.setCreatetime(new Date());
                tf.setCheck_id(ConvertUtil.safeToInteger(m.get("id"),0));
                tf.setTask_id(taskid);
                tf.setUser_id(userid);
                tf.setValue(v);
                tf.setType(1);//标示文本
                taskService.saveTaskForm(tf);
            }
        }

        UpdateFile updateFile=new UpdateFile();
        List<String> list2=updateFile.upload2(request);
        for (String s:list2) {
            Task_form tf=new Task_form();
            tf.setUpdatetime(new Date());
            tf.setCreatetime(new Date());
            tf.setTask_id(taskid);
            tf.setUser_id(userid);
            tf.setType(2);//标示图片
            tf.setValue(s);
            taskService.saveTaskForm(tf);
        }
        if (1==tasktype){
            return "redirect:/zdk/task1";
        }else{
            return "redirect:/zdk/task2";
        }
    }

}
