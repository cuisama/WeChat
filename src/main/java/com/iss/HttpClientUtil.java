package com.iss;

import java.io.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.naming.Name;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {

    /**
     * get请求
     * @param url
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public String doGet(String url) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        CloseableHttpClient httpclient = getHttpclient();
        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");

        CloseableHttpResponse response = httpclient.execute(request);

        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity entity = response.getEntity();
            return parseEntity(entity);
        }
        return null;
    }

    /**
     * Post请求 参数为常规键值对
     * @param url
     * @param map
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public String doPost(String url,Map<String,String> map) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        CloseableHttpClient httpclient = getHttpclient();

        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        if(map != null){
            for (String key:map.keySet()){
                params.add(new BasicNameValuePair(key,map.get(key)));
            }
        }
        HttpPost request = new HttpPost(url);
        request.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        HttpResponse response = httpclient.execute(request);

        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity entity = response.getEntity();
            return parseEntity(entity);
        }
        return null;
    }

    /**
     * Post请求 参数为复杂格式参数的 json串--String
     * @param url
     * @param jsonParam
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String doPost(String url,String jsonParam) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        CloseableHttpClient httpclient = getHttpclient();
        HttpPost request = new HttpPost(url);

//      StringEntity reqEntity = new StringEntity("{\"user_list\":[{\"openid\": \"oDMpRv_P7d8jA8j2QKswLakFNfn4\",\"lang\": \"zh_CN\"},{\"openid\": \"oDMpRv1XMSy4dfkHZR5KDvMZHZKk\",\"lang\": \"zh_CN\"}]}");
        StringEntity reqEntity = new StringEntity(jsonParam,"UTF-8");
        reqEntity.setContentType("application/json");
        request.setEntity(reqEntity);

        HttpResponse response = httpclient.execute(request);

        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity entity = response.getEntity();
            return parseEntity(entity);
        }
        return null;
    }

    /**
     * Post 参数为多媒体类型
     * @param url
     * @param inputStream
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public String doPost(String url, InputStream inputStream) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        CloseableHttpClient httpclient = getHttpclient();
        HttpPost request = new HttpPost(url);
        FileEntity reqEntity = new FileEntity(new File("C:\\Users\\yxcui\\Desktop\\aa.jpg"),ContentType.MULTIPART_FORM_DATA);
        //ByteArrayEntity reqEntity = new ByteArrayEntity(readBytes(inputStream), ContentType.MULTIPART_FORM_DATA);
//        InputStreamEntity reqEntity = new InputStreamEntity(inputStream);
        request.setEntity(reqEntity);

        HttpResponse response = httpclient.execute(request);

        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity entity = response.getEntity();
            return parseEntity(entity);
        }
        return null;
    }

    /**
     * httpEntity解析成字符串
     * @param entity
     * @return
     * @throws IOException
     */
    private String parseEntity(HttpEntity entity) throws IOException {
        InputStreamReader isr = new InputStreamReader(entity.getContent(),"UTF-8");
        BufferedReader in = new BufferedReader(isr);

        String inputLine;
        String result ="";
        while ((inputLine = in.readLine()) != null)
        {
            result += inputLine;
        }

        in.close();
        System.out.println(result);
        return result;
    }

    /**
     * 跳过https的验证
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private CloseableHttpClient getHttpclient() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // don't check
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // don't check
                    }
                }
        };

        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, trustAllCerts, null);

        LayeredConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(ctx);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslSocketFactory)
                .build();
        return httpclient;
    }

    public static byte[] readBytes(InputStream in) throws IOException {
        BufferedInputStream bufin = new BufferedInputStream(in);
        int buffSize = 1024;
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);

        // System.out.println("Available bytes:" + in.available());

        byte[] temp = new byte[buffSize];
        int size = 0;
        while ((size = bufin.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        bufin.close();

        byte[] content = out.toByteArray();
        return content;
    }
}