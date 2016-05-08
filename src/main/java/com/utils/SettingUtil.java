package com.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

public class SettingUtil {
	private static String path = "/setting.properties";


	public static String getSetting(String name) {
		Properties p = new Properties();
		String result="";
		try {
			InputStream in = SettingUtil.class.getResourceAsStream(path);
			 p.load(in);  
			 in.close();  
			 if(p.containsKey(name)){
				 result=String.valueOf(p.get(name));
			 }
		} catch (IOException ex) {
			
		}
		return result;  
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=1#wechat_redirect";
		String requestUrl = url.replace("APPID", "wx06633d33d15adf71")
				.replace("APPSECRET", "3bad4ac79ac0c8afc7956034d7024210")
				.replace("REDIRECT_URI", URLEncoder.encode("http://114.215.206.219:8080/zdk/demo/showweixin","utf-8"))
				.replace("SCOPE", "snsapi_userinfo");
		System.out.println(requestUrl);
	}
}