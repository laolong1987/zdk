package com.common.weixin;


import com.utils.SettingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by gaoyang on 2016/4/28.
 */
public class WeiXinInit {
    private static Logger log = LoggerFactory.getLogger(WeiXinInit.class);
    @PostConstruct
    public void  init(){
        // 获取web.xml中配置的参数
        TokenThread.appid = SettingUtil.getSetting("appid");
        TokenThread.appsecret = SettingUtil.getSetting("appsecret");

        log.info("weixin api appid:{}", TokenThread.appid);
        log.info("weixin api appsecret:{}", TokenThread.appsecret);

        // 未配置appid、appsecret时给出提示
        if ("".equals(TokenThread.appid) || "".equals(TokenThread.appsecret)) {
            log.error("appid and appsecret configuration error, please check carefully.");
        } else {
            // 启动定时获取access_token的线程
            new Thread(new TokenThread()).start();
        }
    }

    @PreDestroy
    public void  dostory(){
        System.out.println("I'm  destory method  using  @PreDestroy.....");
    }
}
