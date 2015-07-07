package com.maidou.weixin.util;

import java.util.Date;

import com.maidou.weixin.bean.Music;
import com.maidou.weixin.bean.MusicMessage;
import com.maidou.weixin.bean.RespBaseMessage;
import com.maidou.weixin.bean.RespTextMessage;
import com.maidou.weixin.bean.TextMessage;
import com.maidou.weixin.service.BaiduMusicService;
import com.maidou.weixin.service.BaiduTranslateService;
import com.maidou.weixin.service.TodayInHistoryService;

public class ToolsUtil {

	/***
	 * 
	 *用途：历史上今天
	 *方法名：getTodayInHistoryInfo
	 *文件名：ToolsUtil.java
	 *返回类型：String
	 *创建时间：2015年6月19日 上午10:29:37
	 */
	public static String getTodayInHistoryInfo(String fromUserName,String toUserName) {
	    // 组装文本消息（历史上的今天）  
	    RespTextMessage textMessage = new RespTextMessage();  
	    textMessage.setToUserName(fromUserName);  
	    textMessage.setFromUserName(toUserName);  
	    textMessage.setCreateTime(new Date().getTime());  
	    textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	    textMessage.setFuncFlag(0);
	    textMessage.setContent(TodayInHistoryService.getTodayInHistoryInfo());
	    
	    return MessageUtil.textMessageToXml(textMessage);
	}
	
	/**
	 * 百度翻译
	 */
	public static String translate(String fromUserName,String toUserName,String content) {
		String keyWord = content.replaceAll("^翻译", "").trim();
		RespTextMessage textMessage = new RespTextMessage();
		textMessage.setToUserName(fromUserName);  
	    textMessage.setFromUserName(toUserName);  
	    textMessage.setCreateTime(new Date().getTime());  
	    textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	    textMessage.setFuncFlag(0);
		
		if ("".equals(keyWord)) {
			textMessage.setContent(BaiduTranslateService.getTranslateUsage());
		} else {
			textMessage.setContent(BaiduTranslateService.translate(keyWord));
		}
		//System.out.print(MessageUtil.textMessageToXml(textMessage));
		// 翻译文本
	    
	    return MessageUtil.textMessageToXml(textMessage);
	}
	
	public static String searchMusic(String fromUserName,String toUserName,String content) {
		// 文本消息内容
		String respContent = null;
		// 音乐消息
		MusicMessage musicMessage = new MusicMessage();
		
		
		// 将搜歌2个字及搜歌后面的+、空格、-等特殊符号去掉  
        String keyWord = content.replaceAll("^搜歌[\\+ ~!@#%^-_=]?", "");
        
		RespTextMessage textMessage = new RespTextMessage();
		textMessage.setToUserName(fromUserName);  
	    textMessage.setFromUserName(toUserName);  
	    textMessage.setCreateTime(new Date().getTime());  
	    textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	    textMessage.setFuncFlag(0);
		//歌曲名为空时
		if ("".equals(keyWord)) {
			textMessage.setContent(BaiduTranslateService.getTranslateUsage());
		} else {
			String[] kwArr = keyWord.split("@");
			// 歌曲名称
			String musicTitle = kwArr[0];
			// 演唱者默认为空
			String musicAuthor = "";
			if (2 == kwArr.length)
				musicAuthor = kwArr[1];

			// 搜索音乐
			Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);
			// 未搜索到音乐
			if (null == music) {
				respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";
				textMessage.setContent(respContent);
			} else {
				musicMessage.setToUserName(fromUserName);
				musicMessage.setFromUserName(toUserName);
				musicMessage.setCreateTime(new Date().getTime());
				musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
				musicMessage.setMusic(music);
			}
		}
	    return MessageUtil.musicMessageToXml(musicMessage);
	}

}
