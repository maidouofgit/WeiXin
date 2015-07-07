package com.maidou.weixin.servlet;

import com.maidou.weixin.bean.AccessToken;
import com.maidou.weixin.util.WeiXinUtil;

public class WeiXinTest {
	public static void main(String[] args) {
		AccessToken token = WeiXinUtil.getAccessToken();
		
		System.out.print("票据："+token.getToken()+"\n");
		System.out.print("超时时间："+token.getExpiresIn());
	}
}
