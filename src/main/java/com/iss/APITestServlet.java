package com.iss;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import redis.clients.jedis.Jedis;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yxcui on 2017/5/2.
 */
@WebServlet(urlPatterns = "/APITestServlet")
public class APITestServlet extends HttpServlet {
    private String charset = "utf-8";
    private HttpClientUtil httpClient = new HttpClientUtil();

    private static String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String api = request.getParameter("api");
        if(api.equals("getUserInfo")){
            getUserInfo(request,response);
        }else if(api.equals("listUserInfo")){
            listUserInfo(request,response);
        }else if(api.equals("getUserList")){
            getUserList(request,response);
        }
    }

    private void listUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String url = WXAPIUrl.USER_INFO_LIST.replace("ACCESS_TOKEN",getAccessToken());
        try {
            String result = httpClient.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        String url = WXAPIUrl.USER_LIST.replace("ACCESS_TOKEN",getAccessToken())
                .replace("NEXT_OPENID","");
        try {
            String result = httpClient.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String url = WXAPIUrl.USER_INFO.replace("ACCESS_TOKEN",getAccessToken())
                .replace("OPENID","");
        try {
            httpClient.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    /**
     * 从redis或者weixin接口获取access_token
     * @return
     */
    private String getAccessToken(){
        Jedis jedis = null;
        String ACCESS_TOKEN = null;
        try{
            jedis = Redis.getInstance();
            ACCESS_TOKEN = jedis.get(ACCESS_TOKEN_KEY);
            if(ACCESS_TOKEN == null || ACCESS_TOKEN.length() < 1){
                String url = WXAPIUrl.ACCESS_TOKEN.replaceAll("APPID","wx60e890458220a2dd")
                        .replaceAll("APPSECRET","f7c65b20e4a50de44d26c88aaf400024");
                String result = httpClient.doGet(url);
                ObjectMapper mapper = new ObjectMapper();
                Map<String,Object> properties = mapper.readValue(result, Map.class);
                Redis.getInstance().setex(ACCESS_TOKEN_KEY, (Integer) properties.get("expires_in"), (String) properties.get("access_token"));
                return ACCESS_TOKEN;
            }else{
                return ACCESS_TOKEN;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }
}
