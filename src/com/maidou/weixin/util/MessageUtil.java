package com.maidou.weixin.util;

import java.io.InputStream;  
import java.io.Writer;  
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  

import javax.servlet.http.HttpServletRequest;  
import javax.smartcardio.ATR;

import org.dom4j.Document;
import org.dom4j.Element;  
import org.dom4j.io.SAXReader;  

import com.maidou.weixin.bean.AccessToken;
import com.maidou.weixin.bean.Article;
import com.maidou.weixin.bean.MusicMessage;
import com.maidou.weixin.bean.NewsMessage;
import com.maidou.weixin.bean.RespTextMessage;
import com.sun.org.apache.regexp.internal.recompile;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/** 
 * 消息工具类 
 *  
 * @author liufeng 
 * @date 2013-05-19 
 */  
public class MessageUtil {  
  
    /** 
     * 返回消息类型：文本 
     */  
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 返回消息类型：音乐 
     */  
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";  
  
    /** 
     * 返回消息类型：图文 
     */  
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";  
  
    /** 
     * 请求消息类型：文本 
     */  
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 请求消息类型：图片 
     */  
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";  
  
    /** 
     * 请求消息类型：链接 
     */  
    public static final String REQ_MESSAGE_TYPE_LINK = "link";  
  
    /** 
     * 请求消息类型：地理位置 
     */  
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";  
  
    /** 
     * 请求消息类型：音频 
     */  
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";  
  
    /** 
     * 请求消息类型：推送 
     */  
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";  
  
    /** 
     * 事件类型：subscribe(订阅) 
     */  
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";  
  
    /** 
     * 事件类型：unsubscribe(取消订阅) 
     */  
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
  
    /** 
     * 事件类型：CLICK(自定义菜单点击事件) 
     */  
    public static final String EVENT_TYPE_CLICK = "CLICK";  
  
    
    /**
     * 
     *用途：获取菜单
     *方法名：initText
     *文件名：MessageUtil.java
     *返回类型：String
     *创建时间：2015年6月16日 下午5:23:02
     */
    public static String initText(String toUserName,String fromUserName,String content) {
    	RespTextMessage text = new RespTextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		text.setContent(content);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return MessageUtil.textMessageToXml(text);
	}
    
    
    /**
     * 
     *用途：主菜单
     *方法名：menuText
     *文件名：MessageUtil.java
     *返回类型：String
     *创建时间：2015年6月16日 下午5:15:39
     */
    public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单进行回复！\n\n");
		sb.append("回复1. 菜单1\n\n");
		sb.append("回复2. 图文消息的组装例子\n\n");
		sb.append("回复3. 获取发送者的基本信息\n\n");
		sb.append("回复4. 历史上的今天\n\n");
		sb.append("回复5. 人脸检测帮助\n\n");
		sb.append("可以检测人脸哦，想来试试吗？直接发送图片就哦咯\n\n");
		sb.append("回复翻译或者（翻译+文字）可以进行翻译\n\n");
		sb.append("回复：搜歌歌名(例如：搜歌存在  或者：搜歌存在@汪峰)\n\n");
		sb.append("回复 ？ 可以得到帮助。");
		
		return sb.toString();
	}
    
    /**
     * 
     *用途：第一个菜单
     *方法名：firstMenu
     *文件名：MessageUtil.java
     *返回类型：String
     *创建时间：2015年6月16日 下午5:24:28
     */
    public static String firstMenu() {
    	StringBuffer sb = new StringBuffer();
		sb.append("菜单一内容\n\n");
		
		return sb.toString();
	}
    
  /**
   * 
   *用途：第二个菜单
   *方法名：secondMenu
   *文件名：MessageUtil.java
   *返回类型：String
   *创建时间：2015年6月16日 下午5:25:51
   */
    public static String secondMenu() {
    	StringBuffer sb = new StringBuffer();
		sb.append("菜单二内容\n\n");
		
		return sb.toString();
	}
    
    /**
	 * 人脸检测帮助菜单
	 */
	public static String getFaceHelp() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("人脸检测使用指南").append("\n\n");
		buffer.append("发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息").append("\n");
		buffer.append("快来试试你是不是长得太着急");
		return buffer.toString();
	}
    
    
    /** 
     * 解析微信发来的请求（XML） 
     *  
     * @param request 
     * @return 
     * @throws Exception 
     */  
    @SuppressWarnings("unchecked")  
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
  
        // 从request中取得输入流  
        InputStream inputStream = request.getInputStream();  
        // 读取输入流  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        // 得到根元素的所有子节点  
        List<Element> elementList = root.elements();  
  
        // 遍历所有子节点  
        for (Element e : elementList)  
            map.put(e.getName(), e.getText());
  
        // 释放资源  
        inputStream.close();  
        inputStream = null;  
  
        return map;  
    }  
  
    /** 
     * 文本消息对象转换成xml 
     *  
     * @param textMessage 文本消息对象 
     * @return xml 
     */  
    public static String textMessageToXml(RespTextMessage textMessage) { 
        xstream.alias("xml", textMessage.getClass());  
        return xstream.toXML(textMessage);  
    }  
    
    
    /** 
     * 图文消息对象转换成xml 
     *  
     * @param newsMessage 图文消息对象 
     * @return xml 
     */  
    public static String newsMessageToXml(NewsMessage newsMessage) {  
        xstream.alias("xml", newsMessage.getClass());  
        xstream.alias("item", new Article().getClass());  
        return xstream.toXML(newsMessage);  
    }  
  
    /** 
     * 音乐消息对象转换成xml 
     *  
     * @param musicMessage 音乐消息对象 
     * @return xml 
     */  
    public static String musicMessageToXml(MusicMessage musicMessage) {  
        xstream.alias("xml", musicMessage.getClass());  
        return xstream.toXML(musicMessage);  
    }  
    
    
    /** 
     * 扩展xstream，使其支持CDATA块 
     *  
     * @date 2013-05-19 
     */  
    private static XStream xstream = new XStream(new XppDriver() {  
        public HierarchicalStreamWriter createWriter(Writer out) {  
            return new PrettyPrintWriter(out) {  
                // 对所有xml节点的转换都增加CDATA标记  
                boolean cdata = true;  
  
                @SuppressWarnings("unchecked")  
                public void startNode(String name, Class clazz) {  
                    super.startNode(name, clazz);  
                }  
  
                protected void writeText(QuickWriter writer, String text) {  
                    if (cdata) {  
                        writer.write("<![CDATA[");  
                        writer.write(text);  
                        writer.write("]]>");  
                    } else {  
                        writer.write(text);  
                    }  
                }  
            };  
        }  
    }); 
    
    /**
     * 
     *用途：图文消息的组装
     *方法名：initNewsMessage
     *文件名：MessageUtil.java
     *返回类型：String
     *创建时间：2015年6月18日 下午2:58:04
     */
    public static String initNewsMessage(String toUserName,String fromUserName) {
		String message = null;
		List <Article> articleList = new ArrayList<Article>();
		NewsMessage newsMessage = new NewsMessage();
		
		Article article = new Article();
		article.setTitle("这个是标题");
		article.setDescription("这个是描述");
		article.setPicUrl("http://maidou.tunnel.mobi/WeiXin/images/zongzi.jpg");
		article.setUrl("www.ynau.edu.cn");
		
		articleList.add(article);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setArticles(articleList);
		newsMessage.setArticleCount(articleList.size());
		
		message = newsMessageToXml(newsMessage);
		return message;
	}
    
}  