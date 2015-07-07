package com.maidou.weixin.util;

import java.text.MessageFormat;

public class SendMessageEmay {
	//软件序列号
//	private static final String softwareSerialNo="0SDK-EMY-0130-JKVMP";
	//自定义关键字(key值)
//	private static final String key="501283";
	
//	private static Client client=null;
	
	/**
	 *发送验证短信
	 *@param phone 接收短信的手机号码
	 *@param code 短信验证码 
	 * @return true/false  是否发送成功
	 */
	public static Boolean SendPhoneValidateCode(String phone,String code) {
		try {
			String regMsg = Util.getPropByPropName("regMsg", "cheduoduo");
			
			String content = MessageFormat.format (regMsg,new Object[]{code});
			byte[] bytes;
			try {
				bytes = content.getBytes("ISO-8859-1");
				content=new String(bytes,"utf-8");
			} catch (Exception e) {
				return false;
			}
			
			int i=SingletonClient.getClient().sendSMS(new String[] {phone}, content,"",5);//带扩展码
			if(i == 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
