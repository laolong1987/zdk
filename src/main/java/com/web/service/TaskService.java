package com.web.service;

import com.common.SearchTemplate;
import com.web.dao.PatientDao;
import com.web.dao.TaskDao;
import com.web.entity.Patient;
import com.web.entity.Task;
import com.web.entity.Task_check;
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
}
