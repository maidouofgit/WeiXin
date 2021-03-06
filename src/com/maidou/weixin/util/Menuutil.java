package com.maidou.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MenuUtil {
    /**
     * 创建Menu
    * @Title: createMenu
    * @Description: 创建Menu
    * @param @return
    * @param @throws IOException    设定文件
    * @return int    返回类型
    * @throws
     */
    public static String createMenu() { 
    	String menu = 
    			"{\"button\":["
    					//+"{\"name\":\"扫描\",\"sub_button\":[{\"type\":\"scancode_waitmsg\",\"name\":\"扫码带提示\",\"key\":\"rselfmenu_0_0\"},{\"type\":\"click\",\"name\":\"已办工单\",\"key\":\"02_FINISH\"},{\"type\":\"click\",\"name\":\"我的工单\",\"key\":\"03_MYJOB\"},{\"type\":\"click\",\"name\":\"公告消息箱\",\"key\":\"04_MESSAGEBOX\"},{\"type\":\"click\",\"name\":\"签到\",\"key\":\"05_SIGN\"}]}"
    					+"{\"type\":\"scancode_waitmsg\",\"name\":\"扫码带提示\",\"key\":\"rselfmenu_0_0\"},"
    					+"{\"type\":\"location_select\",\"name\":\"发送位置\",\"key\":\"rselfmenu_2_0\"},"
    					+"{\"name\":\"日常工作\",\"sub_button\":[{\"type\":\"click\",\"name\":\"待办工单\",\"key\":\"01_WAITING\"},{\"type\":\"click\",\"name\":\"已办工单\",\"key\":\"02_FINISH\"},{\"type\":\"click\",\"name\":\"我的工单\",\"key\":\"03_MYJOB\"},{\"type\":\"click\",\"name\":\"公告消息箱\",\"key\":\"04_MESSAGEBOX\"},{\"type\":\"click\",\"name\":\"签到\",\"key\":\"05_SIGN\"}]}"
    					+"]}";
         
        //此处改为自己想要的结构体，替换即可
        String access_token = WeiXinUtil.getAccessToken().getToken();
 
        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
        try {
           URL url = new URL(action);
           HttpURLConnection http =   (HttpURLConnection) url.openConnection();    
             
           http.setRequestMethod("POST");        
           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
           http.setDoOutput(true);        
           http.setDoInput(true);
           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
 
           http.connect();
           OutputStream os= http.getOutputStream();    
           os.write(menu.getBytes("UTF-8"));//传入参数    
           os.flush();
           os.close();
         
           InputStream is =http.getInputStream();
           int size =is.available();
           byte[] jsonBytes =new byte[size];
           is.read(jsonBytes);
           String message=new String(jsonBytes,"UTF-8");
           return "返回信息"+message;
           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }    
        return "createMenu 失败";
   }
     
    /**
     * 删除当前Menu
    * @Title: deleteMenu
    * @Description: 删除当前Menu
    * @param @return    设定文件
    * @return String    返回类型
    * @throws
     */
   public static String deleteMenu()
   {
       String access_token= WeiXinUtil.getAccessToken().getToken();
       String action = "https://api.weixin.qq.com/cgi-bin/menu/delete? access_token="+access_token;
       try {
          URL url = new URL(action);
          HttpURLConnection http =   (HttpURLConnection) url.openConnection();    
            
          http.setRequestMethod("GET");        
          http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");    
          http.setDoOutput(true);        
          http.setDoInput(true);
          System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
          System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
 
          http.connect();
          OutputStream os= http.getOutputStream();    
          os.flush();
          os.close();
        
          InputStream is =http.getInputStream();
          int size =is.available();
          byte[] jsonBytes =new byte[size];
          is.read(jsonBytes);
          String message=new String(jsonBytes,"UTF-8");
          return "deleteMenu返回信息:"+message;
          } catch (MalformedURLException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }
       return "deleteMenu 失败";   
   }
    
    public static void main(String[] args) {
         
        System.out.println(createMenu());
     
    }
}
