package com.company;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nabea on 2017/05/02.
 */
public class HttpReq {
    String webHost="localhast";
    String webEncode="utf-8";
    public HttpReq(String webHost, String webEncode){
        this.webHost=webHost;
        this.webEncode=webEncode;
    }

    protected WebResp doGet(String uri) throws IOException{
        URL url = new URL("http://" + this.webHost + uri);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("GET");
        http.connect();

        return getResponse(http,webEncode);
    }

    protected WebResp doPost(String uri, Map<?,?> postData) throws IOException{
        Iterator<?> it=postData.keySet().iterator();
        StringBuilder sbParam = new StringBuilder();
        while(it.hasNext()){
            String key = (String) it.next();
            String val = (String)postData.get(key);
            key = URLEncoder.encode(key,webEncode);
            val = URLEncoder.encode(val,webEncode);
            if(sbParam.length() > 0 ){
                sbParam.append("&");
            }
            sbParam.append(key).append("=").append(val);
        }
        URL urlObj = new URL("http://" + this.webHost + uri);
        HttpURLConnection http = (HttpURLConnection)urlObj.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Accept-Language","ja");

        //Send request
        OutputStream os = http.getOutputStream();
        PrintStream ps = new PrintStream(os);
        ps.print(sbParam.toString());
        ps.close();
        return getResponse(http,webEncode);
    }

    private WebResp getResponse(HttpURLConnection http, String webEncode) throws IOException{
        WebResp resp = new WebResp();

        resp.setStatus(http.getResponseCode());

        LinkedHashMap<String, String> resHeader = new LinkedHashMap<String,String>();
        Map<String, List<String>> header = http.getHeaderFields();
        Iterator<String> headerIt = header.keySet().iterator();
        while(headerIt.hasNext()){
            String key = headerIt.next();
            List<String> valList = header.get(key);
            if(key != null){
                StringBuilder sb = new StringBuilder();
                for(String val: valList){
                    if (sb.length() > 0){
                        sb.append("\n");
                    }
                    sb.append(val);
                }
                resHeader.put(key,sb.toString());
            }
        }
        resp.setHeader(resHeader);

        //get Body
        InputStream is = http.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,webEncode));
        StringBuilder sbBody = new StringBuilder();
        String s;
        while((s = reader.readLine()) != null){
            sbBody.append(s);
            sbBody.append("\n");
        }
        resp.setBody(sbBody.toString());
        return resp;
    }
}
