package com.maidou.weixin.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

public class Util {
     
	public static String Md5(String plainText) {

		String result = null;
		
		try {
				MessageDigest md = MessageDigest.getInstance("MD5"); 
	    		md.update(plainText.getBytes()); 
	    		byte b[] = md.digest(); 
	   		    int i; 
	    		StringBuffer buf = new StringBuffer(""); 
	    		for (int offset = 0; offset < b.length; offset++) { 
	    		    i = b[offset]; 
	    		    if(i < 0) i += 256; 
	    		    if(i < 16) buf.append("0"); 
	    		    buf.append(Integer.toHexString(i)); 
                } 

    		 result = buf.toString(); 
   		 
    	 }  catch (NoSuchAlgorithmException e) { 
    	    e.printStackTrace(); 
    	 }
		
		return result; 
	}    
	
	public static JsonConfig GetFormartDateConfig(){
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			
			@Override
			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d=(Date) arg1;
				return sdf.format(d);
			}
			
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		return config;
	}
	
	public static JsonConfig GetShortFormartDateConfig(){
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			
			@Override
			public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date d=(Date) arg1;
				return sdf.format(d);
			}
			
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		return config;
	}
	
	public static int dateCompare(Date date1, Date date2){
		Calendar cal1 = Calendar.getInstance(); 
		Calendar cal2 = Calendar.getInstance(); 
		cal1.setTime(date1); 
		cal2.setTime(date2); 
		cal1.set(Calendar.SECOND, 0); 
		cal1.set(Calendar.MILLISECOND, 0);
		cal1.set(Calendar.HOUR_OF_DAY, 0); 
		cal1.set(Calendar.MINUTE,0);
		cal2.set(Calendar.SECOND, 0); 
		cal2.set(Calendar.MILLISECOND, 0);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE,0);
		return cal1.compareTo(cal2);
	}
	
	public static Date getStartDate(Date startDate){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		cal1.set(Calendar.SECOND, 0); 
		cal1.set(Calendar.MILLISECOND, 0);
		cal1.set(Calendar.HOUR_OF_DAY, 0); 
		cal1.set(Calendar.MINUTE,0);
		return cal1.getTime();
	}
	
	public static Date getEndDate(Date endDate){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(endDate);
		cal1.set(Calendar.SECOND, 59); 
		cal1.set(Calendar.MILLISECOND, 59);
		cal1.set(Calendar.HOUR_OF_DAY, 23); 
		cal1.set(Calendar.MINUTE,59);
		return cal1.getTime();
	}
	
	public static JSONObject getReturnJson(Integer state,String content,JSONArray result){
		JSONObject returnResult = new JSONObject();
		returnResult.put("state", state);
		returnResult.put("content", content);
		returnResult.put("result", result);
		return returnResult;
	}
	
	public static JSONObject getReturnJson(Integer state,String content){
		JSONObject returnResult = new JSONObject();
		returnResult.put("state", state);
		returnResult.put("content", content);
		return returnResult;
	}
	
	public static Date formatStringToDate(String strFormat, String dateString){
		
		if (strFormat == null) strFormat = "yyyy-MM-dd";

		SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
		Date newDate = null;

		try {
			newDate = dateFormat.parse(dateString);
		} catch (ParseException pe) {
			newDate = null;
		}

		return newDate;
	}
	
	
	/**
	 * 通过属性名在配置文件中获取属性值
	 * @param propName
	 * @param fileName 配置文件名
	 * @return
	 */
	public static String getPropByPropName(String propName,String fileName){
		String prop ="";
		Properties props = new Properties();
		String filePath = buildPath(fileName);
		try {
			filePath=URLDecoder.decode(filePath,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePath));
			props.load(in);
			prop = props.getProperty (propName);
			in.close();
			return prop;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 修改Properties配置文件中指定键的值
	 * @param propName 指定要修改的 键名
	 * @param fileName 指定要修改的配置文件名
	 * @param propValue 指定要设置的键值
	 * @return true/false
	 */
    public static Boolean setPropertiesValue(String propName,String fileName,String propValue)  
    {  
        Properties properties = new Properties(); 
        
        String filePath = buildPath(fileName);
        try  
        {  
        	InputStream in = new BufferedInputStream (new FileInputStream(filePath));
        	properties.load(in);
        	in.close();
			if(properties.containsKey(propName)){
	            OutputStream outputStream = new FileOutputStream(filePath);  
	            properties.setProperty(propName, propValue);
	            properties.store(outputStream, "Update '" + propName + "' value");  
	            outputStream.close();  
	            return true;
			}else{
				return false;
			}
        }  
        catch (IOException e)  
        {  
            e.printStackTrace(); 
            return false;
        }  
    }  
	
	/**
	 * 获取配置文件中所有Key
	 * @param fileName 配置文件名
	 * @return List<String> ["keyName1","keyName2","keyName3"]
	 */
	public static List<String> getAllPropName(String fileName){
		Set<String> propSet;
		List<String> propList = new ArrayList<String>();
		Properties props = new Properties();
		String filePath = buildPath(fileName);
		try {
			filePath=URLDecoder.decode(filePath,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePath));
			props.load(in);
			propSet = props.stringPropertyNames();
			propList.addAll(propSet);
			in.close();
			return propList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String buildPath(String fileName){
		String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		File dir = new File(filePath);
		filePath = dir.getParentFile().getPath();
		filePath += File.separator + fileName + ".properties"; 
		return filePath;
	}
	
	/**
	 * 修改配置文件（如果键存在则更新其value，键不存在则新建）
	 * @param key 键
	 * @param value 值
	 * @param fileName 配置文件名
	 * @return true/false
	 */
	public static Boolean writePropertyFile(String key, String value,String fileName) {
		Properties props = new Properties();
		String filePath = buildPath(fileName); 
        try {   
            File file = new File(filePath);   
            if (!file.exists())   
                file.createNewFile();   
            InputStream fis = new FileInputStream(file);   
            props.load(fis);   
            fis.close();//一定要在修改值之前关闭fis   
            OutputStream fos = new FileOutputStream(filePath);   
            props.setProperty(key, value);   
            props.store(fos, "Update '" + key + "' value"); 
            props.remove(key);
            fos.close(); 
            return true;
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
        }
    }
	
	public static String getFileSize(String byteSize){
		Integer byteInt = Integer.parseInt(byteSize);
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(byteInt / (1024.0 * 1024.0)) + " M";
	}
	
	public static long checkTimeToCurrentWithMinutes(Date time){
		long diff = new Date().getTime() - time.getTime();
	    long min = diff / (1000 * 60);
        return min;
	}
	
	public static Integer getDaysByTwoDate(Date startTime,Date endTime){
		long diff = endTime.getTime() - startTime.getTime();
	    Integer day = (int) (diff / (1000 * 60 * 60 * 24));
		return day;
	}
	
	// generate random validate code with length 
	public static String genRandomValidateCode(int length){
		int CODELENGTH = length;
		String validateCode = "";
		Random random = new Random();
		String randString = "1234567890";
		
		for (int i=0; i < CODELENGTH; i++){
			validateCode += String.valueOf(randString.charAt(random.nextInt( randString.length() )));
		}
		return validateCode;
	}
	
	// filter string
	public static String StringFilter(String str) {        
        //String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        String regEx="[`~@#$^&*()+=|{}''//[//].<>/~@#&*——+|{}【】]";
        Pattern p = Pattern.compile(regEx);        
        Matcher m = p.matcher(str);        
        return  m.replaceAll("").trim();        
	}
	
	public static String getStringByState(String type,Integer param){
		String key = type + "" + param.toString();
		String content = getPropByPropName(key, "setting");
		return content;
	}
	
	
	
	public static Date getLaterTimeByMonth(Date startTime,Integer period){
		
		Integer year = Integer.parseInt( (new SimpleDateFormat("yyyy")).format(startTime) );
		Integer month = Integer.parseInt( (new SimpleDateFormat("MM")).format(startTime) );
		Integer day = Integer.parseInt( (new SimpleDateFormat("dd")).format(startTime) );
		if (day == 29 || day == 30 || day == 31 ) {
			day = 28;
		}
		if(month + period > 12){
			Integer allMonth = month + period;
			while(allMonth > 12){
				month = allMonth - 12;
				allMonth = allMonth -12;
				year ++;
			}
		}else{
			month = month + period;
		}
		
		DecimalFormat df = new DecimalFormat("00");
		Date result = formatStringToDate("yyyyMMdd", year.toString()+df.format(month)+df.format(day));
		return result;
	}
	
	public static String moneyFormat(BigDecimal amount){
		DecimalFormat df =new DecimalFormat("###,###.00");
		String result = df.format(amount);
		if(result.length() == 3){
			result = "0.00";
		}
		return result; 
	}
	
	public static String timeFormat(Date time){
		return (new SimpleDateFormat("yyyy-MM-dd")).format(time);
	}
	
	public static String numberFormat(BigDecimal number){
		DecimalFormat df =new DecimalFormat("0.00");
		return df.format(number);
	}
	
	public static String dateFormat(Date date){
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
	}
	
	public static JSONObject subAmount(String amount){
		JSONObject result = new JSONObject();
		String amountA = amount.substring(0, (amount.length()-2));
		String amountB = amount.substring((amount.length()-2), amount.length());
		if(amountA.length() == 1){
			amountA = "0.";
		}
		result.put("a", amountA);
		result.put("b", amountB);
		return result;
	}
	
	public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
	
	public static String maskerMobile(String mobile){
		return mobile.substring(0,3) + "****" + mobile.substring(mobile.length() - 4);
	}
	
	public static String maskerRealId(String realId){
		return realId.substring(0,3) + "******" + realId.substring(realId.length() - 4);
	}
	
	public static String maskerName(String name){
		return name.substring(0,1) + "***"; 
	}
	
	public static String maskerEmail(String email){
		String [] emails = email.split("@");
		return emails[0].substring(0,4) + "****@" + emails[1];
	}
	
	public static String getWithdrawFeeByAmount(BigDecimal amount){
		return amount.divide(new BigDecimal(Util.getPropByPropName("withdraw.fee.step", "gjrh").toString()),0,BigDecimal.ROUND_CEILING).multiply(new BigDecimal(Util.getPropByPropName("withdraw.fee.singleFee", "gjrh").toString())).toString();
	}
	
	public static boolean judgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}
	
}
