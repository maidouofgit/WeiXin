package com.maidou.weixin.util;

public class StringValidation {

	public static Boolean validateInteger(String inputString){
		
		String reg = "[0-9]*";
		if (!inputString.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
	
	public static Boolean validateMobile(String inputString){
		
		String reg = "^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}$";
		if (!inputString.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
	
	public static Boolean validateChineseWord(String inputString){
		
		String reg = "[\\u4e00-\\u9fa5]+";		
		if (!inputString.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}

	public static Boolean validateMD5_32(String inputString){
		
		String reg = "^[a-fA-F0-9]{32,32}$";
		if (!inputString.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
	
    public static Boolean validateChineseID(String inputString){
		
		String reg = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
		if (!inputString.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
	
    // YYYY-MM-DD
    public static Boolean validateDateA(String inputString){
		
		String reg = "^\\d{4}(\\-)\\d{1,2}(\\-)\\d{1,2}$";
		if (!inputString.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
    
    public static Boolean validateFloat(String inputString){
		
		String reg = "[0-9]*(\\.?)[0-9]*";
		if (!inputString.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
    
    // 字母数字 2~16
    public static Boolean validateUsernameA(String inputString){
		
		String reg = "^[a-zA-Z][a-zA-Z0-9_]{2,16}$";
		if (!inputString.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
    
    public static Boolean validateEmail(String inputString){
		
		String reg = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
		if (!inputString.matches(reg)) {
			return false;
		} else {
			return true;
		}
	}
    
    public static Boolean validateWords(String inputString){
		
    	String reg = "^([\\u4E00-\\uFA29]|[\\uE7C7-\\uE7F3]|[\\w]|[\\.\\_\\,\\，\\。])*$";
 		if (!inputString.matches(reg)) {
 			return false;
 		} else {
 			return true;
 		}
 	}

    public static Boolean validateMinLength(String inputString, Integer minLength){
    	if (inputString.length() < minLength){
    		return false;
    	} else {
    		return true;
    	}
    }
    
    public static Boolean validateMaxLength(String inputString, Integer maxLength){
    	if (inputString.length() > maxLength){
    		return false;
    	} else {
    		return true;
    	}
    }
	
}
