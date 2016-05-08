package com.web.dao;


import com.common.BaseDao;
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

    public List<Map> getUserByOpenId(String openid){
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT t.id,t.openid,b.nick_name,b.headimgurl,a.total_money,a.task_money,a.current_money,a.master_money ");
        sql.append(" FROM zdk.user t LEFT JOIN user_account a ON t.id=a.user_id ");
        sql.append(" LEFT JOIN user_info b ON t.id=b.userid WHERE 1=1 AND t.openid=:openid ");
        Map map=new HashMap();
        map.put("openid",openid);
        return super.findResult(sql.toString(),map);
    }
}
