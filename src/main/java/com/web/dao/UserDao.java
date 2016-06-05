package com.web.dao;


import com.common.BaseDao;
import com.utils.ConvertUtil;
import com.web.entity.User;
import com.web.entity.User_info;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class UserDao extends BaseDao{

    public boolean checkUserByOpenId(String openid){
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT COUNT(id) FROM USER WHERE openid=:openid");
        Map map=new HashMap();
        map.put("openid",openid);
        int result=(Integer) super.getUniqueResult(sql.toString(),map);
        if(result>0){
            return true;
        }else{
            return false;
        }
    }


    public boolean checkAdminBypwd(String username,String pwd){
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT COUNT(id) FROM admin WHERE username=:username and pwd=:pwd ");
        Map map=new HashMap();
        map.put("username",username);
        map.put("pwd",pwd);
        int result= ConvertUtil.safeToInteger(super.getUniqueResult(sql.toString(),map),0);
        if(result>0){
            return true;
        }else{
            return false;
        }
    }

    public List<Map> getUserByOpenId(String openid){
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT t.id,t.openid,b.nick_name,b.headimgurl,a.total_money,a.task_money,a.current_money,a.master_money ");
        sql.append(" FROM zdk.user t LEFT JOIN user_account a ON t.id=a.user_id ");
        sql.append(" LEFT JOIN user_info b ON t.id=b.userid WHERE 1=1 AND t.openid=:openid ");
        Map map=new HashMap();
        map.put("openid",openid);
        return super.findResult(sql.toString(),map);
    }

    public User_info getUserInfoByUserId(int userid){
        StringBuffer sql=new StringBuffer();
        sql.append("select * from user_info where userid=:userid  ");
        Map map=new HashMap();
        map.put("userid",userid);
        List<User_info> list= super.findObjects(sql.toString(),map, User_info.class);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public List<User_info> findUserInfoByReferee_id(int referee_id){
        StringBuffer sql=new StringBuffer();
        sql.append("select * from user_info where referee_id=:referee_id  ");
        Map map=new HashMap();
        map.put("referee_id",referee_id);
        List<User_info> list= super.findObjects(sql.toString(),map, User_info.class);
        return  list;
    }
}
