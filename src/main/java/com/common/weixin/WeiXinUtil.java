package com.common.weixin;

/**
 * Created by gaoyang on 2016/4/27.
 */
public class WeiXinUtil {
    /**
     * 获取access_token
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;

//        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
//        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
//        // 如果请求成功
//        if (null != jsonObject) {
//            try {
//                accessToken = new AccessToken();
//                accessToken.setToken(jsonObject.getString("access_token"));
//                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
//            } catch (JSONException e) {
//                accessToken = null;
//                // 获取token失败
//                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
//            }
//        }
        return accessToken;
    }
}
