package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main {

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://info.tokyodisneyresort.jp/rt/s/gps/tdl_index.html?nextUrl=http://info.tokyodisneyresort.jp/rt/s/realtime/tdl_attraction.html");
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        StringBuilder sbBody = new StringBuilder();
        String s;
        while((s = reader.readLine()) != null){
            sbBody.append(s);
            sbBody.append("\n");
        }
        System.out.println(sbBody.toString());
/*        HttpReq httpReq = new HttpReq("google.co.jp","utf-8");
        WebResp webResp = new WebResp();

        try{
            webResp = httpReq.doGet("/");
        }catch(Exception e){

        }
        System.out.println(webResp.getBody());
        System.out.println(webResp.getCookies().toString());*/
    }
}
