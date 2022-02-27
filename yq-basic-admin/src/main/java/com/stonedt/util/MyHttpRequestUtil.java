package com.stonedt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class MyHttpRequestUtil {
  public static String sendPostEsSearch(String url, String params) {
    System.err.println(url + "?" + params);
    try {
      PrintWriter out = null;
      BufferedReader in = null;
      URL realUrl = new URL(url);
      URLConnection conn = realUrl.openConnection();
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
      conn.setDoOutput(true);
      conn.setDoInput(true);
      out = new PrintWriter(conn.getOutputStream());
      out.print(params);
      out.flush();
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null)
        response.append(line); 
      try {
        if (out != null)
          out.close(); 
        if (in != null)
          in.close(); 
      } catch (IOException ex) {
        ex.printStackTrace();
      } 
      return response.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    } 
  }
  
  public static String doPostKafka(String topicName, String message, String kafkaUrl) {
    String resultJson = "";
    CloseableHttpClient httpClient = HttpClients.createDefault();
    try {
      HttpPost httpPost = new HttpPost(kafkaUrl);
      httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
      List<NameValuePair> params = new ArrayList<>();
      params.add(new BasicNameValuePair("topicName", topicName));
      params.add(new BasicNameValuePair("message", message));
      httpPost.setEntity((HttpEntity)new UrlEncodedFormEntity(params, "UTF-8"));
      CloseableHttpResponse httpResponse = httpClient.execute((HttpUriRequest)httpPost);
      try {
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
          resultJson = EntityUtils.toString(entity, "UTF-8");
        } else {
          EntityUtils.consume(entity);
        } 
      } finally {
        httpResponse.close();
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        httpClient.close();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    } 
    return resultJson;
  }
  
  public static String sendPostRaw(String params, String url, String encoding) {
    String body = "";
    try {
      CloseableHttpClient client = HttpClients.createDefault();
      HttpPost httpPost = new HttpPost(url);
      StringEntity s = new StringEntity(params, encoding);
      s.setContentEncoding((Header)new BasicHeader("Content-Type", "application/json;charset=UTF-8"));
      httpPost.setEntity((HttpEntity)s);
      httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
      CloseableHttpResponse response = client.execute((HttpUriRequest)httpPost);
      HttpEntity entity = response.getEntity();
      if (entity != null)
        body = EntityUtils.toString(entity, encoding); 
      EntityUtils.consume(entity);
      response.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return body;
  }
}
