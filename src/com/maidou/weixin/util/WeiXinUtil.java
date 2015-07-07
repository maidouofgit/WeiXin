package com.maidou.weixin.util;


import java.io.IOException;

import com.maidou.weixin.bean.AccessToken;
import com.maidou.weixin.bean.UserInfo;

import ytx.org.apache.http.HttpEntity;
import ytx.org.apache.http.HttpResponse;
import ytx.org.apache.http.client.ClientProtocolException;
import ytx.org.apache.http.client.methods.HttpGet;
import ytx.org.apache.http.client.methods.HttpPost;
import ytx.org.apache.http.entity.StringEntity;
import ytx.org.apache.http.impl.client.DefaultHttpClient;
import ytx.org.apache.http.util.EntityUtils;
import net.sf.json.JSONObject;

public class WeiXinUtil {
	
	private static final String  AppID = "wxa84f56a503d0ddfc";
	private static final String AppSecret = "d137b34ac09baf1066862b91e8f94390 ";
	private static final String Access_Token_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String OpenID_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	/**
	 * 
	 *用途：get请求
	 *方法名：doGetStr
	 *文件名：WeiXinUtil.java
	 *返回类型：JSONObject
	 *创建时间：2015年6月18日 下午3:56:47
	 */
	public static JSONObject doGetStr(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jo = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity= response.getEntity();
			if(httpEntity != null){
				String result = EntityUtils.toString(httpEntity,"UTF-8");
				jo = JSONObject.fromObject(result); 
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	/**
	 * 
	 *用途：post请求
	 *方法名：doPustStr
	 *文件名：WeiXinUtil.java
	 *返回类型：JSONObject
	 *创建时间：2015年6月18日 下午3:56:33
	 */
	public static JSONObject doPostStr(String url,String outStr) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jo = null;
		try {
			httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			String result =  EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
			jo = JSONObject.fromObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo;
	}
	
	/**
	 * 
	 *用途：获取AccessToken
	 *方法名：getAccessToken
	 *文件名：WeiXinUtil.java
	 *返回类型：AccessToken
	 *创建时间：2015年6月18日 下午4:38:04
	 */
	public static AccessToken getAccessToken() {
		AccessToken accessToken = new AccessToken();
		String url = Access_Token_URL.replace("APPID",AppID).replace("APPSECRET",AppSecret);
		JSONObject jo = doGetStr(url);
		if(jo != null){
			accessToken.setToken(jo.getString("access_token"));
			accessToken.setExpiresIn(jo.getInt("expires_in"));
		}
		return accessToken;
	}
	
	/**
	 * 
	 *用途：通过access_token和OpenID获取用户基本信息
	 *方法名：getUserInfo
	 *文件名：WeiXinUtil.java
	 *返回类型：UserInfo
	 *创建时间：2015年6月19日 上午9:45:37
	 */
	public static UserInfo getUserInfo(String access_token,String openId) {
		
		UserInfo userInfo = new UserInfo();
		String url = OpenID_URL.replace("ACCESS_TOKEN", access_token).replace("OPENID",openId);
		JSONObject jo = doGetStr(url);
		if(jo != null){
			//userInfo.setSubscribe(jo.getString("subscribe"));
			userInfo.setNickname(jo.getString("headimgurl"));
		}
		return userInfo;
	}
}
