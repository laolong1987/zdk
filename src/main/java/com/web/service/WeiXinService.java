package com.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.utils.HttpHelper;
import com.utils.SettingUtil;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyang on 2016/5/4.
 */
@Service("weiXinService")
public class WeiXinService {

    public static String ACCESSTOKENBYCODE = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public static String USERINFOBYTOKEN = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    public static String AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=1#wechat_redirect";


    public static String appid = SettingUtil.getSetting("appid");

    public static String appsecret = SettingUtil.getSetting("appsecret");

    /**
     * 通过code换取网页授权access_token
     *
     * @param code
     * @return
     */
    public JSONObject getAccessTokenByCode(String code) {
        String requestUrl = ACCESSTOKENBYCODE.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
//        System.out.println(requestUrl);
        Map map = new HashMap();
        String res = HttpHelper.fetchUTF8StringByGet(requestUrl, map, null, 0, 0);
        JSONObject jsonObject = JSON.parseObject(res);
        return jsonObject;
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     *
     * @param access_token
     * @param openid
     * @return
     */
    public JSONObject getUserInfoByToken(String access_token, String openid) {
        String requestUrl = USERINFOBYTOKEN.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
//        System.out.println(requestUrl);
        Map map = new HashMap();
        String res = HttpHelper.fetchUTF8StringByGet(requestUrl, map, null, 0, 0);
        JSONObject jsonObject = JSON.parseObject(res);
        return jsonObject;
    }

    /**
     * 获取授权URL
     */
    public String getAuthorizeURL(String redirect) {
        String requestUrl = null;
        try {
            requestUrl = AUTHORIZE.replace("APPID", appid)
                    .replace("APPSECRET", appsecret)
                    .replace("REDIRECT_URI", URLEncoder.encode(redirect, "utf-8"))
                    .replace("SCOPE", "snsapi_userinfo");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return requestUrl;
    }
}
