package com.maidou.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.maidou.weixin.bean.AccessToken;
import com.maidou.weixin.bean.RespTextMessage;
import com.maidou.weixin.bean.UserInfo;
import com.maidou.weixin.service.BaiduTranslateService;
import com.maidou.weixin.service.FaceService;
import com.maidou.weixin.util.MessageUtil;
import com.maidou.weixin.util.SignUtil;
import com.maidou.weixin.util.ToolsUtil;
import com.maidou.weixin.util.WeiXinUtil;



public class WeiXinServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 /**
     * @see HttpServlet#HttpServlet()
     */
    public WeiXinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	
	//微信验证
	 /** 
     * 确认请求来自微信服务器 
     */  
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // 微信加密签名  
        String signature = request.getParameter("signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");  
  
        PrintWriter out = response.getWriter();  
        System.out.println(signature + "***" + timestamp + "***" + nonce + "***" + echostr);
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
            out.print(echostr);  
        }  
        out.close();  
        out = null;  
    }  
  
    /** 
     * 处理微信服务器发来的消息 
     */  
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//设置编码格式，防止乱码
    	response.setCharacterEncoding("UTF-8");
    	request.setCharacterEncoding("UTF-8");
    	//获取微信服务响应
    	PrintWriter out = response.getWriter();
    	try {
			Map<String, String> map =MessageUtil.parseXml(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
	
			String message = null;
			//菜单判断
			//文本菜单
			if(MessageUtil.RESP_MESSAGE_TYPE_TEXT.equals(msgType)){
				//菜单例子1
				if("1".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}
				//图文消息的组装例子
				else if("2".equals(content)){
					message = MessageUtil.initNewsMessage(toUserName,fromUserName);
				}
				//获取用户基本信息例子(api权限不够)
				else if("3".equals(content) || "3".equals(content)){
					 AccessToken ac = WeiXinUtil.getAccessToken();
					 UserInfo ui = WeiXinUtil.getUserInfo(ac.getToken(), fromUserName);
					 message = "你好！  "+ui.getNickname();
				}
				//菜单4 历史上的今天
				else if("4".equals(content) || "4".equals(content)){
					message = ToolsUtil.getTodayInHistoryInfo(fromUserName, toUserName);
				}
				//菜单5 人脸检测帮助
				else if("5".equals(content) || "5".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.getFaceHelp());
				}
				//菜单 百度智能翻译
				else if(content.startsWith("翻译")){
					message = ToolsUtil.translate(fromUserName, toUserName,content);
				}
				//菜单6 百度音乐搜索
				else if(content.startsWith("搜歌")){
					message = ToolsUtil.searchMusic(fromUserName, toUserName,content);
				}
				else if(content.startsWith("token")){
					message = WeiXinUtil.getAccessToken().getToken();
				}
				//显示提示菜单
				else if("?".equals(content) || "？".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
				else{
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}
			//事件
			else if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)){
				String eventType = map.get("Event");
				if(MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
				System.out.print(message);
			}
			//接受的信息为图片
			else if(MessageUtil.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)){
				// 取得图片地址
				String picUrl = map.get("PicUrl");
				// 人脸检测
				String detectResult = FaceService.detect(picUrl);
				message = MessageUtil.initText(toUserName, fromUserName,detectResult);
			}
			
			System.out.print(message);
			out.print(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	finally{
    		out.close();
    	} 
    }  
}