package com.iss;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.iss.LocalApi.addKfAccount;

/**
 * Created by yxcui on 2017/5/2.
 */
@WebServlet(urlPatterns = "/APITestServlet")
public class APITestServlet extends HttpServlet {
    //荷叶雨
    private static String APPID = "wx60e890458220a2dd";
    private static String APPSECRET = "f7c65b20e4a50de44d26c88aaf400024";

    private String charset = "utf-8";
    private HttpClientUtil httpClient = new HttpClientUtil();

    private static String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String api = request.getParameter("api");
        response.setCharacterEncoding("UTF-8");
        if(api.equals("getUserInfo")){
            getUserInfo(request,response);
        }else if(api.equals("listUserInfo")){
            listUserInfo(request,response);
        }else if(api.equals("getUserList")){
            getUserList(request,response);
        }else if(api.equals("massSendByOpenid")){
            massSendByOpenid(request,response);
        }else if (api.equals("massSendGetState")){
            massSendGetState(request,response);
        }else if(api.equals("addKfAccount")){
            comPost(WXAPIUrl.ADD_KFACCOUNT,request,response);
        }else if(api.equals("getKfList")){
            comGet(WXAPIUrl.GET_KFLIST,request,response);
        }else if(api.equals("kfSend")){
            comPost(WXAPIUrl.KF_SEND,request,response);
        }else if(api.equals("createTag")){
            comPost(WXAPIUrl.CREATE_TAG,request,response);
        }else if(api.equals("getTags")){
            comGet(WXAPIUrl.GET_TAGS,request,response);
        }else if(api.equals("getTagUsers")){
            comPost(WXAPIUrl.TAG_USERS,request,response);
        }else if(api.equals("batchTagging")){
            comPost(WXAPIUrl.BATCH_TAGGING,request,response);
        }else if(api.equals("listUserTags")){
            comPost(WXAPIUrl.LIST_USER_TAGS,request,response);
        }else if(api.equals("uploadMedia")){
//            uploadMedia(request,response);
            String imagePath = request.getParameter("imagePath");
            String cmd = "curl -F media=@"+imagePath+" \""+WXAPIUrl.UPLOAD_MEDIA.replace("ACCESS_TOKEN",getAccessToken())
                    .replace("TYPE","image")+"\"";
            String result = Command.exeCmd(cmd);
            response.getWriter().write(result);
        }else if(api.equals("createMenu")){
            comPost(WXAPIUrl.CREATE_MENU,request,response);
        }else if(api.equals("deleteMenu")){
            comGet(WXAPIUrl.DELETE_MENU,request,response);
        }else if(api.equals("getMenu")){
            comGet(WXAPIUrl.GET_MENU,request,response);
        }
        
        LocalApi localApi = addKfAccount;
        switch (localApi){
            case addKfAccount:
                break;
        }
    }

    private void uploadMedia(HttpServletRequest request, HttpServletResponse response) {
        String url = WXAPIUrl.UPLOAD_MEDIA.replace("ACCESS_TOKEN",getAccessToken())
                .replace("TYPE","image");
        try {
            String result = httpClient.doPost(url,new FileInputStream(new File("C:\\Users\\yxcui\\Desktop\\aa.jpg")));
            response.getWriter().write(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void comGet(String url, HttpServletRequest request, HttpServletResponse response) {
        url = url.replace("ACCESS_TOKEN",getAccessToken());;
        try {
            String result = httpClient.doGet(url);
            response.getWriter().write(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void comPost(String url,HttpServletRequest request, HttpServletResponse response){
        String jsonParam = request.getParameter("jsonParam");
        url = url.replace("ACCESS_TOKEN",getAccessToken());;
        try {
            String result = httpClient.doPost(url,jsonParam);
            response.getWriter().write(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void massSendGetState(HttpServletRequest request, HttpServletResponse response) {
        String jsonParam = request.getParameter("jsonParam");
        String url = WXAPIUrl.MASS_SEND_GET_STATE.replace("ACCESS_TOKEN",getAccessToken());;
        try {
            String result = httpClient.doPost(url,jsonParam);
            response.getWriter().write(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void massSendByOpenid(HttpServletRequest request, HttpServletResponse response) {
        String jsonParam = request.getParameter("jsonParam");
        String url = WXAPIUrl.MASS_SEND_BY_OPENID.replace("ACCESS_TOKEN",getAccessToken());;
        try {
            String result = httpClient.doPost(url,jsonParam);
            response.getWriter().write(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void listUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String jsonParam = request.getParameter("jsonParam");
        //String jsonParam = "{\"user_list\":[{\"openid\": \"oDMpRv_P7d8jA8j2QKswLakFNfn4\",\"lang\": \"zh_CN\"},{\"openid\": \"oDMpRv1XMSy4dfkHZR5KDvMZHZKk\",\"lang\": \"zh_CN\"}]}";

        String url = WXAPIUrl.USER_INFO_LIST.replace("ACCESS_TOKEN",getAccessToken());
        try {
            String result = httpClient.doPost(url,jsonParam);
            response.getWriter().write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        String url = WXAPIUrl.USER_LIST.replace("ACCESS_TOKEN",getAccessToken())
                .replace("NEXT_OPENID","");
        try {
            String result = httpClient.doGet(url);
            response.getWriter().write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getUserInfo(HttpServletRequest request, HttpServletResponse response) {
        String url = WXAPIUrl.USER_INFO.replace("ACCESS_TOKEN",getAccessToken())
                .replace("OPENID",request.getParameter("openid"));
        try {
            String result = httpClient.doGet(url);
            response.getWriter().write(result);
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
                ACCESS_TOKEN = (String) properties.get("access_token");
                Redis.getInstance().setex(ACCESS_TOKEN_KEY, (Integer) properties.get("expires_in"), ACCESS_TOKEN);
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
