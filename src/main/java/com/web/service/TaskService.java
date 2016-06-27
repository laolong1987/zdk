package com.web.service;

import com.common.SearchTemplate;
import com.utils.ConvertUtil;
import com.utils.SettingUtil;
import com.web.dao.PatientDao;
import com.web.dao.TaskDao;
import com.web.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by gaoyang on 16/2/29.
 */
@Service("taskService")
public class TaskService {

    @Autowired
    public TaskDao taskDao ;

    /**
     * 查询
     * @param map
     * @return
     */
    public SearchTemplate searchTask(Map map){
        return taskDao.searchTask(map);
    }

    public SearchTemplate searchTaskBatch(Map map){
        return taskDao.searchTaskBatch(map);
    }

    public SearchTemplate searchUserTask(Map map){
        return taskDao.searchUserTask(map);
    }

    public Task getTaskById(int id){
        return (Task) taskDao.getObjectById(id,Task.class);
    }

    public void saveTask(Task task){

        taskDao.save(task);
    }

    public void saveTaskCheck(Task_check task_check){
        taskDao.save(task_check);
    }

    public void removeTaskClickByTaskId(int taskid){
        taskDao.removeTaskClickByTaskId(taskid);
    }

    public List<Map> searchTaskClickByTaskId(int taskid){
        return taskDao.searchTaskClickByTaskId(taskid);
    }

    public void removeTask(List<Integer> ids) {
        taskDao.removeTask(ids);
    }

    public List<Map> searchTaskByUser(int userid,int tasktype){
        return taskDao.searchTaskByUser(userid,tasktype);
    }

    public List<Map> searchTaskByUser(int userid){
        return taskDao.searchTaskByUser(userid);
    }

    public void saveUserTask(User_task user_task){
        taskDao.save(user_task);
    }

    public void saveTaskForm(Task_form task_form){
        taskDao.save(task_form);
    }

    public void saveTaskBatch(Task_batch task_batch){
        taskDao.save(task_batch);
    }


    public User_task getUserTaskById(int id){
        return (User_task) taskDao.getObjectById(id,User_task.class);
    }

    public Task_batch getUserTask_batchById(int id){
        return (Task_batch) taskDao.getObjectById(id,Task_batch.class);
    }

    public List<Task_batch> findTask_batchByname(String name, String phone){
        return taskDao.findTask_batchByname(name,phone);
    }

    public List<Task_checktype> findTask_checktype(){
        return taskDao.findTask_checktype();
    }

    public List<Map> searchTaskClickType(String ids){
        return taskDao.searchTaskClickType(ids);
    }

    public List<Map> searchTaskClickData(int userid,int taskid){
        return taskDao.searchTaskClickData(userid,taskid);
    }

    public List<String[]> searchTaskFromByTaskId(int taskid,String checktypeids){
        int typesize=4;
        // 当前文件路径
        String nowPath = SettingUtil.getSetting("nowPath");
        List<Map> list=new ArrayList<Map>();
        if (null!=checktypeids && !"".equals(checktypeids)){
            list = taskDao.searchTaskClickType(checktypeids.replaceAll(";",","));
            typesize+=list.size();
        }

        List<String[]> resultList = new ArrayList<String[]>();
        String[] ss = new String[typesize];
        ss[0]="用户ID";
        ss[1]="用户昵称";
        ss[2]="数据状态";
        ss[3]="数据ID";
        int i=4;
        for (Map map:list) {
            ss[i]= ConvertUtil.safeToString(map.get("name"),"");
            i++;
        }
        resultList.add(ss);
        String tmpuserid="";
        int j=4;
        String[] d = new String[20];
        List<Map> list2 = taskDao.searchTaskClickData(taskid);
        for (Map map:list2) {
            String userid= ConvertUtil.safeToString(map.get("user_id"),"");
            String value= ConvertUtil.safeToString(map.get("value"),"");
            String check_id= ConvertUtil.safeToString(map.get("check_id"),"");
            String name= ConvertUtil.safeToString(map.get("name"),"");
            String nick_name= ConvertUtil.safeToString(map.get("nick_name"),"");
            String status= ConvertUtil.safeToString(map.get("status"),"");
            String id= ConvertUtil.safeToString(map.get("id"),"");
            if(!tmpuserid.equals(userid)){
                if(j>4){
                    resultList.add(d);
                }
                d = new String[100];
                j=4;
                d[0]=userid;
                d[1]=nick_name;
                d[2]=status;
                d[3]=id;
                tmpuserid=userid;
            }
            if("0".equals(check_id)){
                d[j]=nowPath+value;
            }else{
                d[j]=value;
            }
            j++;
        }
        if(j!=0){
            resultList.add(d);
        }
        return resultList;
    }



    public User_task getUserTask(int taskid,int userid){
        User_task user_task=new User_task();
        List<User_task> list= taskDao.findUser_task(taskid,userid);
        if(list.size()>0){
            user_task=list.get(0);
        }
        return user_task;
    }

    public List<Map> searchTask(int type){
        return taskDao.searchTask(type);
    }
}
