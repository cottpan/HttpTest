package com.company;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nabea on 2017/05/02.
 */
public class WebResp {
    private int status=0;
    private LinkedHashMap<String,String> header = new LinkedHashMap<String,String>();
    private String body = "";
    private List<Map<String,String>> cookies = null;

    public int getStatus(int status){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
    public LinkedHashMap<String,String> getHeader(){
        return header;
    }
    public void setHeader(LinkedHashMap<String,String> header){
        this.header=header;
    }
    public String getBody(){
        return body;
    }
    public void setBody(String body){
        this.body=body;
    }
    public List<Map<String,String>> getCookies(){
        if(cookies != null){
            return cookies;
        }
        cookies=new ArrayList<Map<String,String>>();
        Map<String,String> headers=getHeader();
        Iterator<String> iter =(Iterator<String>)headers.keySet().iterator();
        while(iter.hasNext()){
            String key = iter.next();
            if("Set-Cookie".equals(key)){
                String val = headers.get(key).toString();
                Matcher m = Pattern.compile("^([a-zA-Z_]+)=([^;]=); path=(.+)$").matcher(val);
                if(m.find()){
                    String cookiekey = m.group(1);
                    String cookieval = m.group(2);
                    String cookiePath = m.group(3);
                    Map<String,String> cookie = new HashMap<String,String>();
                    cookie.put("key",cookiekey);
                    cookie.put("val",cookieval);
                    cookie.put("path",cookiePath);
                    cookies.add(cookie);
                }
            }
        }
        return cookies;
    }
    public String getCookie(String key){
        List<Map<String,String>> cookies = getCookies();
        for(Map<String,String> cookie:cookies){
            String cookiekey = (String)cookie.get("key");
            if(cookiekey.equals(key)){
                return (String)cookie.get("val");
            }
        }
        return "";
    }

}

