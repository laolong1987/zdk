package com.web.service;

import com.alibaba.fastjson.JSONObject;
import com.common.SearchTemplate;
import com.web.dao.UserDao;
import com.web.entity.Spread;
import com.web.entity.User;
import com.web.entity.User_account;
import com.web.entity.User_info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gaoyang on 16/2/29.
 */
@Service("userService")
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    WeiXinService weiXinService;

    /**
     * 根据openID判断用户是否存在
     *
     * @param openid
     * @return
     */
    public boolean checkUserByOpenId(String openid) {
        return userDao.checkUserByOpenId(openid);
    }
    /**
     * 根据admin是否存在
     *
     * @param
     * @return
     */
    public boolean checkAdminBypwd(String username,String pwd) {
        return userDao.checkAdminBypwd(username,pwd);
    }
    /**
     * 根据Spread是否存在
     *
     * @param
     * @return
     */
    public boolean checkSpreadBypwd(String username,String pwd) {
        return userDao.checkSpreadBypwd(username,pwd);
    }

    /**
     * 根据Spread是否存在
     *
     * @param
     * @return
     */
    public Spread getSpreadBypwd(String username,String pwd) {
        return userDao.getSpreadBypwd(username,pwd);
    }

    /**
     * 添加用户
     *
     * @param openid
     * @param source
     */
    public Map saveUser(String openid, int source, String access_token) {
        Map usermap=new HashMap();

        User user = new User();
        user.setOpenid(openid);
        user.setSource(source);
        user.setCreate_time(new Date());
        user.setUpdate_time(new Date());
        userDao.save(user);

        //添加用户账户
        User_account userAccount = new User_account();
        userAccount.setUser_id(user.getId());
        userAccount.setCreate_time(new Date());
        userAccount.setUpdate_time(new Date());
        userAccount.setCurrent_money(0);
        userAccount.setTask_money(0);
        userAccount.setTotal_money(0);
        userAccount.setMaster_money(0);
        userDao.save(userAccount);

        //添加用户信息
        User_info userInfo = new User_info();
        userInfo.setUserid(user.getId());
        userInfo.setCreate_time(new Date());
        userInfo.setUpdate_time(new Date());

        JSONObject info = weiXinService.getUserInfoByToken(access_token, openid);
        if(null!=info){
            userInfo.setNick_name(info.getString("nickname"));
            userInfo.setSex(info.getInteger("sex"));
            userInfo.setProvince(info.getString("province"));
            userInfo.setCity(info.getString("city"));
            userInfo.setCountry(info.getString("country"));
            userInfo.setHeadimgurl(info.getString("headimgurl"));
            userInfo.setPrivilege(info.getString("privilege"));
            userInfo.setUnionid(info.getString("unionid"));
        }
        userDao.save(userInfo);

        usermap.put("id",user.getId());
        usermap.put("openid",user.getOpenid());
        usermap.put("nick_name",userInfo.getNick_name());
        usermap.put("headimgurl",userInfo.getHeadimgurl());
        usermap.put("total_money",userAccount.getTotal_money());
        usermap.put("task_money",userAccount.getTask_money());
        usermap.put("current_money",userAccount.getCurrent_money());
        usermap.put("master_money",userAccount.getMaster_money());
        return usermap;
    }

    /**
     * 获取用户信息
     *
     * @param openid
     * @return
     */
    public Map getUserByOpenId(String openid) {
        Map resultmap=null;
        List<Map> list=userDao.getUserByOpenId(openid);
        if(list.size()>0){
            resultmap=list.get(0);
        }
        return resultmap;
    }

    /**
     * 获取用户信息
     *
     * @param userid
     * @return
     */
    public User_info getUserInfoByUserId(int userid) {
         return userDao.getUserInfoByUserId(userid);
    }

    /**
     * 获取用户信息
     *
     * @param userid
     * @return
     */
    public User_info getUserInfoById(int userid) {
        return (User_info)userDao.getObjectById(userid,User_info.class);
    }

    /**
     * 获取用户
     *
     * @param userid
     * @return
     */
    public User getUserById(int userid) {
        return (User) userDao.getObjectById(userid,User.class);
    }

    public void saveUserInfo(User_info user_info){
        userDao.save(user_info);
    }

    /**
     * 获取用户信息
     *
     * @param referee_id
     * @return
     */
    public List<User_info> findUserInfoByReferee_id(int referee_id) {
        return userDao.findUserInfoByReferee_id(referee_id);
    }

    /**
     * 添加推广员
     * @param spread
     */
    public void saveSpread(Spread spread){
        userDao.save(spread);
    }


    /**
     * 查询推广员
     *
     * @param map
     * @return
     */
    public SearchTemplate searchSpread(Map map){
        return userDao.searchSpread(map);
    }

    /**
     * 获取推广员信息
     *
     * @param id
     * @return
     */
    public Spread getSpreadById(int id) {
        return (Spread) userDao.getObjectById(id,Spread.class);
    }

}
