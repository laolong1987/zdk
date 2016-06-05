package com.web.service;

import com.common.SearchTemplate;
import com.web.dao.PatientDao;
import com.web.dao.TaskDao;
import com.web.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
