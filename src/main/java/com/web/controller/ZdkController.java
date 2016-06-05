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
@RequestMapping("/zdk")
public class ZdkController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request,
                        HttpServletResponse response) {

        return "/jsp/user/index";
    }

    @RequestMapping(value = "/home")
    public String home(HttpServletRequest request,
                        HttpServletResponse response) {


        request.setAttribute("c4","on");

        return "/jsp/user/home";
    }

    @RequestMapping(value = "/share")
    public String share(HttpServletRequest request,
                       HttpServletResponse response) {
        request.setAttribute("c3","on");
        return "/jsp/user/share";
    }

    @RequestMapping(value = "/referee")
    public String referee(HttpServletRequest request,
                       HttpServletResponse response) {
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);
        int isref= 0;
        //判断是否有推荐人
        User_info user_info= userService.getUserInfoByUserId(userid);
        if(null!=user_info){
            int rid= ConvertUtil.safeToInteger(user_info.getReferee_id(),0);
            if(0!=rid){
                User_info user_info2= userService.getUserInfoByUserId(rid);
                if(null!=user_info2){
                    request.setAttribute("name",user_info2.getNick_name());
                    request.setAttribute("rid",rid);
                    isref=1;
                }
            }
            request.setAttribute("infoid",user_info.getId());
        }
        request.setAttribute("isref",isref);
        request.setAttribute("c4","on");
        return "/jsp/user/referee";
    }

    @RequestMapping(value = "/addreferee")
    public String addreferee(HttpServletRequest request,
                       HttpServletResponse response) {
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);
        int referee_id = ConvertUtil.safeToInteger(request.getParameter("referee_id"), 0);
        int infoid = ConvertUtil.safeToInteger(request.getParameter("infoid"), 0);
        if(userid!=referee_id){
            User_info user_info= userService.getUserInfoById(infoid);
            if(null!=user_info){
                if(0!=referee_id){
                    User user= userService.getUserById(referee_id);
                    if(null!=user){
                        user_info.setReferee_id(referee_id);
                        userService.saveUserInfo(user_info);
                    }
                }
            }
        }
        return "redirect:/zdk/referee";
    }

    @RequestMapping(value = "/taskdetail")
    public String taskdetail(HttpServletRequest request,
                        HttpServletResponse response) {
        request.setAttribute("c4","on");
        return "/jsp/user/taskdetail";
    }

    @RequestMapping(value = "/mydisciple")
    public String mydisciple(HttpServletRequest request,
                        HttpServletResponse response) {
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);

        List<User_info> user_info= userService.findUserInfoByReferee_id(userid);
        request.setAttribute("c4","on");
        request.setAttribute("list",user_info);
        return "/jsp/user/mydisciple";
    }
    @RequestMapping(value = "/cashup")
    public String cashup(HttpServletRequest request,
                        HttpServletResponse response) {

        request.setAttribute("c4","on");
        return "/jsp/user/cashup";
    }


    @RequestMapping(value = "/mytask")
    public String mytask(HttpServletRequest request,
                       HttpServletResponse response) {
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);
        List list = taskService.searchTaskByUser(userid);
        request.setAttribute("tasklist", list);
        request.setAttribute("typename", "我的任务");
        request.setAttribute("c4","on");
        request.setAttribute("tasktype","3");
        return "/jsp/user/mytask";
    }



    @RequestMapping(value = "/task1")
    public String task1(HttpServletRequest request,
                        HttpServletResponse response) {
        request.setAttribute("typename", "快速任务");
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);
        List list = taskService.searchTaskByUser(userid,1);
        request.setAttribute("tasklist", list);
        request.setAttribute("c1","on");
        request.setAttribute("tasktype","1");
        return "/jsp/user/listtask";
    }

    @RequestMapping(value = "/task2")
    public String task2(HttpServletRequest request,
                        HttpServletResponse response) {
        request.setAttribute("typename", "推广任务");
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);
        List list = taskService.searchTaskByUser(userid,2);
        request.setAttribute("tasklist", list);
        request.setAttribute("c2","on");
        request.setAttribute("tasktype","2");
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
//        int taskid= ConvertUtil.safeToInteger(request.getParameter("taskid"),0);
        String checktype= ConvertUtil.safeToString(request.getParameter("checktype"),"");
//        List list=taskService.searchTaskClickByTaskId(taskid);
        List list=taskService.searchTaskClickType(checktype.replaceAll(";",","));
        return list;
    }

    @RequestMapping(value = "/uptask", method = RequestMethod.POST)
    public Object uptask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int taskid = ConvertUtil.safeToInteger(request.getParameter("taskid"), 0);
        int utid = ConvertUtil.safeToInteger(request.getParameter("utid"), 0);
        int userid = ConvertUtil.safeToInteger(request.getSession().getAttribute("userid"), 0);
        int tasktype=ConvertUtil.safeToInteger(request.getParameter("tasktype"), 1);
        String taskchecktype=ConvertUtil.safeToString(request.getParameter("taskchecktype"), "");
        User_task user_task=taskService.getUserTaskById(utid);
        user_task.setStatus(1);
        user_task.setUpdatetime(new Date());
        taskService.saveUserTask(user_task);

        if(!"".equals(taskchecktype)){
//        List<Map> list=taskService.searchTaskClickByTaskId(taskid);
         String[] list=taskchecktype.split(";");
        for (String m:list) {
            String taskcheck = "b_"+ConvertUtil.safeToInteger(m,0);
            String v= ConvertUtil.safeToString(request.getParameter(taskcheck), "");
            if(!"".equals(v)){
                Task_form tf=new Task_form();
                tf.setUpdatetime(new Date());
                tf.setCreatetime(new Date());
                tf.setCheck_id(ConvertUtil.safeToInteger(m,0));
                tf.setTask_id(taskid);
                tf.setUser_id(userid);
                tf.setValue(v);
                tf.setType(1);//标示文本
                taskService.saveTaskForm(tf);
            }
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
        }else if(2==tasktype){
            return "redirect:/zdk/task2";
        }else{
            return "redirect:/zdk/mytask";
        }
    }

}
