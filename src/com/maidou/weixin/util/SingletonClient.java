package com.maidou.weixin.util;

import cn.emay.sdk.client.api.Client;

public class SingletonClient {
	//软件序列号
	private static final String softwareSerialNo="0SDK-EMY-0130-JKVMP";
	//自定义关键字(key值)
	private static final String key="501283";

	private static Client client=null;
	private SingletonClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo,String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient(){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public static void main(String str[]){
		SingletonClient.getClient();
	}
}
