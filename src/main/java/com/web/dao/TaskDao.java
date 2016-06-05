package com.web.dao;


import com.common.BaseDao;
import com.common.SearchTemplate;
import com.web.entity.Task_batch;
import com.web.entity.Task_checktype;
import com.web.entity.User_info;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoyang on 16/2/29.
 */
@Repository
public class TaskDao extends BaseDao {

    /**
     * 查询
     *
     * @param map
     * @return
     */
    public SearchTemplate searchTask(Map map) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT t.id,t.name,t.status,t.description,DATE_FORMAT(t.begintime,'%Y-%m-%d %H:%i') AS begintime,DATE_FORMAT(t.endtime,'%Y-%m-%d %H:%i') AS endtime, ");
        sql.append("t.keyword,t.unitprice,t.total,t.type,t.logoimg,t.imgfile,t.checktype ");
        sql.append("FROM task t ");
        sql.append("where 1=1 ");
        Map p = new HashMap();
        if (map.containsKey("name")) {
            sql.append(" and t.name like :queryname");
            p.put("queryname", "%" + map.get("queryname") + "%");
        }
        sql.append("order by create_time desc ");
        return super.search(sql.toString(), p);
    }

    /**
     * 查询
     *
     * @param map
     * @return
     */
    public SearchTemplate searchTaskBatch(Map map) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT t.status,t.id,t.name,t.phone,t.teamname,DATE_FORMAT(t.create_time,'%Y-%m-%d %H:%i') AS create_time,DATE_FORMAT(t.update_time,'%Y-%m-%d %H:%i') AS update_time, ");
        sql.append(" t.alipay,t.qq,t.weixin,t.taskname,t.filename ");
        sql.append("FROM task_batch t ");
        sql.append("where 1=1 ");
        Map p = new HashMap();
        if (map.containsKey("name")) {
            sql.append(" and t.name like :queryname");
            p.put("queryname", "%" + map.get("queryname") + "%");
        }
        sql.append("order by create_time desc ");
        return super.search(sql.toString(), p);
    }

    /**
     * 查询
     *
     * @param map
     * @return
     */
    public SearchTemplate searchUserTask(Map map) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT t.userid,a.nick_name,t.status,t.taskid,b.name,DATE_FORMAT(t.updatetime,'%Y-%m-%d %H:%i') AS updatetime FROM user_task t");
        sql.append(" LEFT JOIN user_info a ON a.userid=t.userid LEFT JOIN task b ON b.id=t.taskid ");
        sql.append("where 1=1 ");
        Map p = new HashMap();
//        if (map.containsKey("name")) {
//            sql.append(" and t.name like :queryname");
//            p.put("queryname", "%" + map.get("queryname") + "%");
//        }
        sql.append("order by t.createtime desc ");
        return super.search(sql.toString(), p);
    }


    public void removeTaskClickByTaskId(int taskid) {
        StringBuffer sql = new StringBuffer("delete from task_check where taskid=:taskid ");
        Map p = new HashMap();
        p.put("taskid", taskid);
        super.executeSql(sql.toString(), p);
    }

    public List<Map> searchTaskClickByTaskId(int taskid) {
        StringBuffer sql = new StringBuffer("select * from task_check where taskid=:taskid ");
        Map p = new HashMap();
        p.put("taskid", taskid);
        return super.findResult(sql.toString(), p);
    }

    public void removeTask(List<Integer> ids) {
        StringBuffer sql = new StringBuffer("delete from task where id in(:ids)");
        super.removeByIds(sql.toString(), ids);
    }


    public List<Map> searchTaskByUser(int userid, int tasktype) {
        StringBuffer sql = new StringBuffer("SELECT t.id,t.name,t.unitprice,t.keyword,t.total,t.logoimg,t.description,t.content,a.status,t.imgfile,a.id as utid,t.checktype FROM task t ");
        sql.append(" LEFT JOIN user_task a ON t.id=a.taskid AND a.userid=:userid ");
        sql.append(" where 1=1 and t.type=:type and t.status!=0 ");
        Map p = new HashMap();
        p.put("userid", userid);
        p.put("type", tasktype);
        sql.append(" order by FIELD(a.status,0) DESC, t.create_time DESC ");
        return super.findResult(sql.toString(), p);
    }

    public List<Map> searchTaskByUser(int userid) {
        StringBuffer sql = new StringBuffer("SELECT t.id,t.name,t.unitprice,t.keyword,t.total,t.logoimg,t.description,t.content,a.status,t.imgfile,a.id as utid FROM user_task a ");
        sql.append(" LEFT JOIN task t ON t.id=a.taskid ");
        sql.append(" where 1=1 and a.userid=:userid  ");
        Map p = new HashMap();
        p.put("userid", userid);
        sql.append(" order by FIELD(a.status,0) DESC, a.createtime DESC ");
        return super.findResult(sql.toString(), p);
    }

    public List<Task_batch> findTask_batchByname(String name, String phone) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from task_batch where name=:name and phone=:phone ");
        Map map = new HashMap();
        map.put("name", name);
        map.put("phone", phone);
        List<Task_batch> list = super.findObjects(sql.toString(), map, Task_batch.class);
        return list;
    }

    public List<Task_checktype> findTask_checktype(){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from task_checktype where status=1 ");
        Map map = new HashMap();
        List<Task_checktype> list = super.findObjects(sql.toString(), map, Task_checktype.class);
        return list;
    }

    public List<Map> searchTaskClickType(String ids) {
        StringBuffer sql = new StringBuffer("select * from task_checktype where id in (");
        sql.append(ids).append(") ");
        Map p = new HashMap();
//        p.put("ids", ids);
        return super.findResult(sql.toString(), p);
//
    }

    public List<Map> searchTaskClickData(int userid,int taskid) {
        StringBuffer sql = new StringBuffer("SELECT  t.value,t.check_id,t.type,a.name  FROM task_form t LEFT JOIN task_checktype a ON a.id=t.check_id  ");
        sql.append(" WHERE user_id=:user_id  AND task_id=:task_id ");
        Map p = new HashMap();
        p.put("user_id", userid);
        p.put("task_id", taskid);
        return super.findResult(sql.toString(), p);
//
    }
}
