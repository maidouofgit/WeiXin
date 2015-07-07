package com.maidou.weixin.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maidou.weixin.bean.Express;
import com.maidou.weixin.bean.ExpressSingle;
import com.maidou.weixin.bean.ExpressSingleList;

import net.sf.json.JSONObject;

/**
 * 快递查询的工具类
 * @author maidou
 *
 */
public class ExpressService {

	/*
	 * 解析json
	 */
	private static String jsonToString(String jo) {
		JSONObject joObj = JSONObject.fromObject(jo);
		StringBuffer result = new StringBuffer();
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("result", ExpressSingle.class);
		classMap.put("list", ExpressSingleList.class);
		
		Express express = (Express) JSONObject.toBean(joObj,Express.class);
		
		result.append(express.getReason());
		result.append("\n");
		ExpressSingle expressSingle = express.getExpressSingle();
		if(expressSingle != null){
			result.append("所属快递："+expressSingle.getCompany()+"\n");
			result.append("快递单号："+expressSingle.getNo()+"\n");
		}
		List<ExpressSingleList> expressSingleList = expressSingle.getExpressSingleList();
		for(ExpressSingleList list :expressSingleList){
			result.append("时间："+list.getDatetime()+"\n");
			result.append("物流状态："+list.getRemark()+"\n");
			result.append("所在地："+list.getZone()+"\n");
		}
		
		return result.toString();
	}
	
	
	public static void main(String[] args) throws IOException {
		String apiKey = "16942d9d43ca81008a38b4a6d1e761e2";
		String apiUrl = "http://v.juhe.cn/exp/index?key="+apiKey+"&com=sf&no=575677355677";
		
		//HTTP客户端请求对象
		URL httpUrl = new URL(apiUrl);
		HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
		conn.connect();
		
		//取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer sb = new StringBuffer();
		
		String line = "";
		while((line = reader.readLine()) != null){
			sb.append(line);
		}
		reader.close();
		
		//断开连接
		conn.disconnect();
		System.out.println(jsonToString(sb.toString()));
	}

}
