package com.maidou.weixin.bean;

public class AccessToken {
	private String token;
	private int expiresIn ;
	public String getToken() {
		return token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
