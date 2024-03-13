package com.stonedt.zhuolixin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class HttpClientKit {

    public static String doPost(String url, String context) throws InterruptedException, ExecutionException {
        String string = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("context", context));
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .build();

        httppost.setConfig(config);
        try {
            CloseableHttpResponse response = httpclient.execute(httppost); // 执行http
            try {
                HttpEntity entity = response.getEntity(); // 获取返回实体
                if (entity != null) {
                    string = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return string;
    }

    public static String doGet(String url) throws InterruptedException, ExecutionException {
        String string = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)

                //	        .setProxy(proxy)
                .build();
        httpget.setConfig(config);
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3218.0 Safari/537.36");
        try {
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
//				System.out.println(""+response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity(); // 获取返回实体
                if (entity != null) {
                    string = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return string;
    }

    public static String doPostWithProxyAndCookie(String url, String body, String ipPort, String cookie) throws InterruptedException, ExecutionException {
        String string = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new StringEntity(body, StandardCharsets.UTF_8));

        RequestConfig config = null;
        // 判断是否有ip
        if (null != ipPort && !"".equals(ipPort)) {
            String[] strArr = ipPort.split(":");
            String ip = strArr[0].trim();
            Integer port = Integer.parseInt(strArr[1].trim());
            HttpHost proxy = new HttpHost(ip, port);
            config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10 * 1000).setSocketTimeout(20 * 1000)
                    .build();
        } else {
            config = RequestConfig.custom().setConnectTimeout(10 * 1000).setSocketTimeout(20 * 1000).build();
        }
        httppost.setConfig(config);

        httppost.addHeader("Accept", "*/*");
        httppost.addHeader("Content-Type", "application/json;charset=utf8");
        httppost.setHeader("Cookie", cookie);
        try {
            CloseableHttpResponse response = httpclient.execute(httppost); // 执行http
            try {
                HttpEntity entity = response.getEntity(); // 获取返回实体
                if (entity != null) {
                    string = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return string;
    }

    public static String getHttpClient(String url, String ipPort) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        String string = null;
        // 创建httpget.
        HttpGet httpget = new HttpGet(url);

        RequestConfig config = null;
        // 判断是否有ip
        if (null != ipPort && !"".equals(ipPort)) {
            String[] strArr = ipPort.split(":");
            String ip = strArr[0].trim();
            Integer port = Integer.parseInt(strArr[1].trim());
            HttpHost proxy = new HttpHost(ip, port);
            config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10 * 1000).setSocketTimeout(20 * 1000)
                    .build();
        } else {
            config = RequestConfig.custom().setConnectTimeout(10 * 1000).setSocketTimeout(20 * 1000).build();
        }

        httpget.setConfig(config);
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

        // 执行get请求
        CloseableHttpResponse response = httpclient.execute(httpget);
        // 获取响应实体
        HttpEntity entity = response.getEntity();

        // 打印响应状态
        Integer statu = response.getStatusLine().getStatusCode();
        if (entity != null) {
            // 打印响应内容长度
            //System.out.println("Response content length: " + entity.getContentLength());

            // 打印响应内容
            string = EntityUtils.toString(entity, "gb2312");
            // System.out.println("打印响应内容"+string);
        }
        response.close();
        // 关闭连接,释放资源
        httpclient.close();

        return string;

    }


    public static String getHttpClientUtf8(String url, String ipPort) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        String string = null;
        // 创建httpget.
        HttpGet httpget = new HttpGet(url);

        RequestConfig config = null;
        // 判断是否有ip
        if (null != ipPort && !"".equals(ipPort)) {
            String[] strArr = ipPort.split(":");
            String ip = strArr[0].trim();
            Integer port = Integer.parseInt(strArr[1].trim());
            HttpHost proxy = new HttpHost(ip, port);
            config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10 * 1000).setSocketTimeout(20 * 1000)
                    .build();
        } else {
            config = RequestConfig.custom().setConnectTimeout(10 * 1000).setSocketTimeout(20 * 1000).build();
        }

        httpget.setConfig(config);
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

        // 执行get请求
        CloseableHttpResponse response = httpclient.execute(httpget);
        // 获取响应实体
        HttpEntity entity = response.getEntity();

        // 打印响应状态
        Integer statu = response.getStatusLine().getStatusCode();
        if (entity != null) {
            // 打印响应内容长度
            //System.out.println("Response content length: " + entity.getContentLength());

            // 打印响应内容
            string = EntityUtils.toString(entity, "utf-8");
            // System.out.println("打印响应内容"+string);
        }
        response.close();
        // 关闭连接,释放资源
        httpclient.close();

        return string;

    }

    public static String getHttpClientUTF8WithCookie(String url, String ipPort, String cookie) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        String string = null;
        // 创建httpget.
        HttpGet httpget = new HttpGet(url);

        RequestConfig config = null;
        // 判断是否有ip
        if (null != ipPort && !"".equals(ipPort)) {
            String[] strArr = ipPort.split(":");
            String ip = strArr[0].trim();
            Integer port = Integer.parseInt(strArr[1].trim());
            HttpHost proxy = new HttpHost(ip, port);
            config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(10 * 1000).setSocketTimeout(20 * 1000)
                    .build();
        } else {
            config = RequestConfig.custom().setConnectTimeout(10 * 1000).setSocketTimeout(20 * 1000).build();
        }

        httpget.setConfig(config);
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

        httpget.setHeader("Cookie", cookie);

        // 执行get请求
        CloseableHttpResponse response = httpclient.execute(httpget);
        // 获取响应实体
        HttpEntity entity = response.getEntity();

        // 打印响应状态
        Integer statu = response.getStatusLine().getStatusCode();
        if (entity != null) {
            // 打印响应内容长度
            //System.out.println("Response content length: " + entity.getContentLength());

            // 打印响应内容
            string = EntityUtils.toString(entity, "utf-8");
            // System.out.println("打印响应内容"+string);
        }
        response.close();
        // 关闭连接,释放资源
        httpclient.close();

        return string;

    }


    public static void main(String[] args) throws Exception {

    }


}
