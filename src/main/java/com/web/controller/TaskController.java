package com.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.UpdateFile;
import com.utils.ConvertUtil;
import com.utils.DateUtil;
import com.utils.JxlUtil;
import com.web.entity.Patient;
import com.web.entity.Task;
import com.web.entity.Task_check;
import com.web.entity.Task_checktype;
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
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by gaoyang on 16/2/28.
 */
@Controller
@RequestMapping("/admin/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/showlist")
    public String showlist(HttpServletRequest request,
                                HttpServletResponse response) {

        List<Task_checktype> checktypeList= taskService.findTask_checktype();
        JSONArray jsonArray=new JSONArray();
        for (Task_checktype task_checktype:checktypeList) {
            JSONObject jo=new JSONObject();
            jo.put("id",task_checktype.getId());
            jo.put("text",task_checktype.getName());
            jsonArray.add(jo);
        }
        request.setAttribute("checktype",jsonArray.toJSONString());
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
        String checktypeids=ConvertUtil.safeToString(request.getParameter("checktypeids"),"");
        int imgfile= ConvertUtil.safeToInteger(request.getParameter("imgfile"),0);

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
        task.setChecktype(checktypeids);
        task.setImgfile(imgfile);
        taskService.saveTask(task);

//        taskService.removeTaskClickByTaskId(task.getId());
//        JSONArray jsonArray= JSON.parseArray(data);
//        for(int k=0;k<jsonArray.size();k++){
//            JSONObject json= JSON.parseObject(jsonArray.get(k).toString()) ;
//            Task_check taskcheck=new Task_check();
//            taskcheck.setName(json.getString("name"));
//            taskcheck.setType(json.getInteger("type"));
//            taskcheck.setCreate_time(new Date());
//            taskcheck.setUpdate_time(new Date());
//            taskcheck.setMaxlength(json.getInteger("maxlength"));
//            taskcheck.setMinlength(json.getInteger("minlength"));
//            taskcheck.setRegular(json.getString("regular"));
//            taskcheck.setTaskid(task.getId());
//            taskService.saveTaskCheck(taskcheck);
//        }
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

    @RequestMapping(value = "/getcontent",method = RequestMethod.POST,produces="application/json; charset=utf-8")
    @ResponseBody
    public Object getcontent(HttpServletRequest request,HttpServletResponse response){
        int taskid= ConvertUtil.safeToInteger(request.getParameter("taskid"),0);
        Task task=taskService.getTaskById(taskid);
        if(null!=task){
            return task.getContent();
        }else{
            return "";
        }
    }

    @RequestMapping(value = "/searchlist")
    public String searchlist(HttpServletRequest request,
                           HttpServletResponse response) {

        return "/jsp/manage/checklist";
    }

    @RequestMapping(value = "/searchchecklist",method = RequestMethod.POST)
    @ResponseBody
    public Object searchchecklist(@RequestBody Map<String, String> param){

        return taskService.searchUserTask(param).getResult();
    }

    @RequestMapping(value = "/getcheckdata",method = RequestMethod.POST)
    @ResponseBody
    public Object getcheckdata(HttpServletRequest request,HttpServletResponse response){
        int userid= ConvertUtil.safeToInteger(request.getParameter("userid"),0);
        int taskid= ConvertUtil.safeToInteger(request.getParameter("taskid"),0);
        List list=taskService.searchTaskClickData(userid,taskid);
        return list;
    }

    @RequestMapping(value = "/showexportlist")
    public String showexportlist(HttpServletRequest request,
                           HttpServletResponse response) {

        return "/jsp/manage/exporttask";
    }

    @RequestMapping("exporttaskdata")
    public void exportData(HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/")
                + "template" + "/";
        String f1 = path + "template.xls";
        String reportType = request.getParameter("type");
        String fn = "";
        if(reportType.equals("1")){
            fn = "延保人员信息表";
        }
        if(reportType.equals("2")){
            fn = "竞赛人员信息表";
        }
        String d = fn + DateUtil.getcurrentDatetime("yyyyMMdd");
        String f2 = path + d + ".xls";
        Map para = new HashMap();
        para.put("type",request.getParameter("type"));
        JxlUtil.writeExcel(f1, f2, orgService.searchshsgm(para), 0, 1);
        String fileName = d + ".xls";
        // 当前文件路径
        String nowPath = request.getSession().getServletContext().getRealPath(
                "/")
                + "template" + "/" + d + ".xls";
        response.setContentType("application/vnd.ms-excel");
        File file = new File(nowPath);

        // 清空response
        response.reset();

        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename="
                + new String(fileName.getBytes("gbk"), "iso-8859-1")); // 转码之后下载的文件不会出现中文乱码
        response.addHeader("Content-Length", "" + file.length());

        try {
            // 以流的形式下载文件
            InputStream fis = new BufferedInputStream(new FileInputStream(
                    nowPath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            OutputStream toClient = new BufferedOutputStream(response
                    .getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
