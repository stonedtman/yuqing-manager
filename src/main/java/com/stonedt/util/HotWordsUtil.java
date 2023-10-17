package com.stonedt.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HotWordsUtil {
	
	public static void main(String[] args) {
		System.out.println(search());
	}
	
	
//	public static String search() {
//		//实时热点
//		String realtimehotspotsurl = "http://top.baidu.com/buzz?b=1&c=513&fr=topbuzz_b341_c513";
//		//今日热点
//		String todayhotspotsurl = "http://top.baidu.com/buzz?b=341&c=513&fr=topbuzz_b1_c513";
//		//热门搜索
//		String hotsearchurl = "http://top.baidu.com/buzz?b=2";
//		
//		String[] arr ={realtimehotspotsurl,todayhotspotsurl,hotsearchurl};
//		Map<String,Object> map =new HashMap<String,Object>();
//		for (int m = 0; m < arr.length; m++) {
//			String html = get(arr[m], "gb2312");
//			JSONArray list= new JSONArray();
//			Document parse = Jsoup.parse(html);
//			try {
//				Elements tobody = parse.select("#main > div.mainBody > div > table > tbody >tr");
//				for(int i = 1;i<tobody.size();i++) {
//					Elements select = tobody.get(i).select("td");
//						String rank = select.select("td.first").text();
//						String source_url = select.select("td.keyword > a.list-title").attr("href");
//						String topic = select.select("td.keyword > a.list-title").text();
//						String original_weight = select.select("td.last >span").text();
//						if(StringUtils.isBlank(topic)) {
//							continue;
//						}
//						map.put(topic, original_weight);
//						//判断selectOffset是否获取到值了，未获取到值则说明是新的界面，获取到值则说明是老界面
//				}
//			} catch (Exception e) {
//			}
//			
//		}
//		JSONArray list = new JSONArray();
//		for(String key:map.keySet()){
//			JSONObject js = new JSONObject();
//			js.put("x", key);
//			js.put("value", map.get(key).toString());
//			list.add(js);
//		       System.out.println("key:"+key+" "+"Value:"+map.get(key));
//		     }
//		return list.toJSONString();
//	}
	public static String search() {
		String html = get("https://top.baidu.com/board?tab=realtime", "gb2312");
		JSONArray list= new JSONArray();
		Document parse = Jsoup.parse(html);
		try {
			//#sanRoot > main > div.container.right-container_2EFJr > div > div:nth-child(2)
			Elements tobody = parse.select("#sanRoot > main > div.container.right-container_2EFJr > div > div:nth-child(2)");
			for(int i = 1;i<31;i++) {
				Elements select = tobody.select("div:nth-child("+i+")");
				String topic = select.select("div.content_1YWBm > a > div.c-single-text-ellipsis").text();
				System.out.println("topic:"+topic);
				String original_weight = select.select("div.trend_2RttY.hide-icon > div.hot-index_1Bl1a").text();
				System.out.println("original_weight:"+original_weight);
				if(StringUtils.isBlank(topic)||topic.length()>100) {
					continue;
				}
				JSONObject js = new JSONObject();
				js.put("x", topic);
				js.put("value", original_weight);
				list.add(js);
				//判断selectOffset是否获取到值了，未获取到值则说明是新的界面，获取到值则说明是老界面
			}

		} catch (Exception e) {
		}
		return list.toJSONString();
	}
	public static String search2() {
		String url = "https://top.baidu.com/board?tab=realtime";
		String html = HotWordsUtil.get(url, "gb2312");
		Document parse = Jsoup.parse(html);
		Element element = parse.getElementById("sanRoot");
		Elements elementsByClass = element.getElementsByClass("horizontal_1eKyQ");
	    JSONArray jsonArray = new JSONArray();
		for (Element element2 : elementsByClass) {
			JSONObject jsonObject = new JSONObject();
			Element element3 = element2.getElementsByTag("a").get(0);
			String title = element2.getElementsByClass("c-single-text-ellipsis").text();
			jsonObject.put("topic", title);
			String href = element3.attr("href").toString();
			String text = element2.getElementsByClass("hot-index_1Bl1a").get(0).text();
//			int parseInt = Integer.parseInt(text.replaceAll("万", ""));
			jsonObject.put("original_weight", text);
			jsonObject.put("source_url", href);
			jsonObject.put("id", MD5Util.MD5(href));
			jsonObject.put("source_name", "百度风云榜");
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toJSONString();
		
	}
	
	/**
	 * 默认get请求
	 * @param url
	 * @param entityType
	 * @return
	 */
	public static String get(String url,String entityType) {
		SSLContext sslContext = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] arg0, String arg1) {
					return true;
				}
			}).build();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		}
		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();
//		CloseableHttpClient httpclient = HttpClients.createDefault();
		System.out.println("get请求开始------");
		String string = null;
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			
	        RequestConfig config = null;
	        config = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
			// 设置httpclient端口IP
			httpget.setConfig(config);
			httpget.setHeader("User-Agent",getRandomAgent());
			System.out.println("executing request " + httpget.getURI());
			// 执行get请求.141.196.71.217:8080
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				System.out.println("响应状态: " + response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length:" + entity.getContentLength());
					// 打印响应内容
					string = EntityUtils.toString(entity, entityType);
					
				}
			} finally {
				response.close();
			}
		} catch (HttpHostConnectException e) {
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			System.out.println("get请求结束------");
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return string;
	}
	
	
	/**
	 * 随机一个Agent
	 * @return
	 */
	public static String getRandomAgent()
	{
		List<String> RandomAgent = new ArrayList<String>();
		RandomAgent.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
		RandomAgent.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
		RandomAgent.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
		RandomAgent.add("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
		RandomAgent.add("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16");
		int sign = (int)(0+Math.random()*(5));
		return RandomAgent.get(sign);
	}


	public static JSONObject wechatSearch() {
		
		return null;
	}
	/**
	 * 微博热点抓取
	 * @param
	 * @return
	 */
	public static String hotWeibo() {
		String html = get("https://tophub.today/n/KqndgxeLl9", "gb2312");
		JSONArray list= new JSONArray();
		Document parse = Jsoup.parse(html);
		try {
			Elements tobody = parse.select("#page > div.c-d.c-d-e > div.Zd-p-Sc > div:nth-child(1) > div.cc-dc-c > div > div.jc-c > table > tbody");
			for(int i = 1;i<6;i++) {
				Elements select = tobody.select("tr:nth-child("+i+")");
				String topic = select.select("td.al > a").text();
				//
				String topicUrlString = new String(java.net.URLEncoder.encode(topic,"utf-8").getBytes());
				String source_url = "https://s.weibo.com/weibo?q=%23"+topicUrlString+"%23&Refer=top";
				String original_weight = select.select("td:nth-child(3)").text();
				//热度值去除中文
				original_weight = original_weight.replaceAll("[\u4e00-\u9fa5]","");
				if (original_weight.contains(".")){
					BigDecimal bigDecimal = new BigDecimal(original_weight);
					original_weight = bigDecimal.multiply(new BigDecimal("10000")).intValue() + "";
				}
				String source_name = "微博";
				//String publish_time = DateUtil.getDate();
				if(StringUtils.isBlank(topic)) {
					continue;
				}
				JSONObject js = new JSONObject();
				js.put("topic", topic);
				js.put("source_url", source_url);
				js.put("source_name",source_name);
				js.put("original_weight", original_weight);
				list.add(js);
			}
		} catch (Exception e) {

		}
		return list.toJSONString();

	}
	public static String hotWechat() {
		String html = null;
		for (int i = 0; i < 3; i++) {
			html = get("https://tophub.today/n/j8Rv21noLw", "gb2312");
			if (html != null){
				break;
			}
		}
		if (html == null){
			return null;
		}
		JSONArray list= new JSONArray();
		Document parse = Jsoup.parse(html);
		try {
			Elements tobody = parse.select("#page > div.c-d.c-d-e > div.Zd-p-Sc > div:nth-child(1) > div.cc-dc-c > div > div.jc-c > table > tbody");
			for(int i = 1;i<6;i++) {
				Elements select = tobody.select("tr:nth-child("+i+")");
				String topic = select.select("td.al > a").text();
				//
				String topicUrlString = new String(java.net.URLEncoder.encode(topic,"utf-8").getBytes());
				String source_url = "https://weixin.sogou.com/weixin?type=2&ie=utf8&s_from=hotnews&query="+topicUrlString;
				Random r = new Random();
				String original_weight = r.nextInt(100000)+(11-i)*100000 +"";
				//热度值去除中文
				String source_name = "微信热词";
				//String publish_time = DateUtil.getDate();
				if(StringUtils.isBlank(topic)) {
					continue;
				}
				JSONObject js = new JSONObject();
				js.put("topic", topic);
				js.put("source_url", source_url);
				js.put("source_name",source_name);
				js.put("original_weight", original_weight);
				list.add(js);
			}
		} catch (Exception e) {

		}
		return list.toJSONString();
}
	/**
	 * 36氪
	 * @return
	 */
	public static String hot36Kr() {
		String html = get("https://tophub.today/n/Q1Vd5Ko85R", "gb2312");
		JSONArray list= new JSONArray();
		Document parse = Jsoup.parse(html);
		try {
			Elements tobody = parse.select("#page > div.c-d.c-d-e > div.Zd-p-Sc > div:nth-child(1) > div.cc-dc-c > div > div.jc-c > table > tbody");
			for(int i = 1;i<6;i++) {
				Elements select = tobody.select("tr:nth-child("+i+")");
				String topic = select.select("td.al > a").text();
				//
				String topicUrlString = new String(java.net.URLEncoder.encode(topic,"utf-8").getBytes());
				String source_url = "https://www.36kr.com/search/articles/"+topicUrlString;
				Random r = new Random();
				String original_weight = r.nextInt(100000)+(11-i)*100000 +"";
				//热度值去除中文
				String source_name = "36氪";
				//String publish_time = DateUtil.getDate();
				if(StringUtils.isBlank(topic)) {
					continue;
				}
				JSONObject js = new JSONObject();
				js.put("topic", topic);
				js.put("source_url", source_url);
				js.put("source_name",source_name);
				js.put("original_weight", original_weight);
				list.add(js);
			}
		} catch (Exception e) {

		}
		return list.toJSONString();
}


	public static String hotDouyin() {
		String html = get("https://tophub.today/n/K7GdaMgdQy", "gb2312");
		JSONArray list= new JSONArray();
		Document parse = Jsoup.parse(html);
		try {
			Elements tobody = parse.select("#page > div.c-d.c-d-e > div.Zd-p-Sc > div:nth-child(1) > div.cc-dc-c > div > div.jc-c > table > tbody");
			for(int i = 1;i<6;i++) {
				Elements select = tobody.select("tr:nth-child("+i+")");
				String topic = select.select("td.al > a").text();
				//
				String topicUrlString = new String(java.net.URLEncoder.encode(topic,"utf-8").getBytes());
				String source_url = "https://www.douyin.com/search/"+topicUrlString+"";
				String original_weight = select.select("td:nth-child(3)").text();
				//热度值去除中文
				original_weight = original_weight.replaceAll("[\u4e00-\u9fa5]","");
				if (original_weight.contains(".")){
					BigDecimal bigDecimal = new BigDecimal(original_weight);
					original_weight = bigDecimal.multiply(new BigDecimal("10000")).intValue() + "";
				}
				String source_name = "抖音";
				//String publish_time = DateUtil.getDate();
				if(StringUtils.isBlank(topic)) {
					continue;
				}
				JSONObject js = new JSONObject();
				js.put("topic", topic);
				js.put("source_url", source_url);
				js.put("source_name",source_name);
				js.put("original_weight", original_weight);
				list.add(js);
			}
		} catch (Exception e) {

		}
		return list.toJSONString();
	}


	public static String hotBilibili() {
		String html = get("https://tophub.today/n/74KvxwokxM", "gb2312");
		JSONArray list= new JSONArray();
		Document parse = Jsoup.parse(html);
		try {
			Elements tobody = parse.select("#page > div.c-d.c-d-e > div.Zd-p-Sc > div:nth-child(1) > div.cc-dc-c > div > div.jc-c > table > tbody");
			for(int i = 1;i<6;i++) {
				Elements select = tobody.select("tr:nth-child("+i+")");
				String topic = select.select("td.al > a").text();
				//
				String topicUrlString = new String(java.net.URLEncoder.encode(topic,"utf-8").getBytes());
				String source_url = "https://www.bilibili.com/";
				String original_weight = select.select("td:nth-child(3)").text();
				//热度值去除中文
				original_weight = original_weight.replaceAll("[\u4e00-\u9fa5]","");
				if (original_weight.contains(".")){
					BigDecimal bigDecimal = new BigDecimal(original_weight);
					original_weight = bigDecimal.multiply(new BigDecimal("10000")).intValue() + "";
				}
				String source_name = "哔哩哔哩";
				//String publish_time = DateUtil.getDate();
				if(StringUtils.isBlank(topic)) {
					continue;
				}
				JSONObject js = new JSONObject();
				js.put("topic", topic);
				js.put("source_url", "https://search.bilibili.com/all?keyword="+topic);
				js.put("source_name",source_name);
				js.put("original_weight", original_weight);
				list.add(js);
			}
		} catch (Exception e) {

		}
		return list.toJSONString();
	}


	public static String hotTecent() {
		String html = get("https://tophub.today/n/qndg48xeLl", "gb2312");
		JSONArray list= new JSONArray();
		Document parse = Jsoup.parse(html);
		try {
			Elements tobody = parse.select("#page > div.c-d.c-d-e > div.Zd-p-Sc > div:nth-child(1) > div.cc-dc-c > div > div.jc-c > table > tbody");
			for(int i = 1;i<6;i++) {
				Elements select = tobody.select("tr:nth-child("+i+")");
				String topic = select.select("td.al > a").text();
				//
				String topicUrlString = new String(java.net.URLEncoder.encode(topic,"utf-8").getBytes());
				String source_url = "https://view.inews.qq.com/";
				String original_weight = select.select("td:nth-child(3)").text();
				//热度值去除中文
				original_weight = original_weight.replaceAll("[\u4e00-\u9fa5]","");
				if (original_weight.contains(".")){
					BigDecimal bigDecimal = new BigDecimal(original_weight);
					original_weight = bigDecimal.multiply(new BigDecimal("10000")).intValue() + "";
				}
				String source_name = "腾讯新闻";
				//String publish_time = DateUtil.getDate();
				if(StringUtils.isBlank(topic)) {
					continue;
				}
				JSONObject js = new JSONObject();
				js.put("topic", topic);
				js.put("source_url", "https://v.qq.com/x/search/?q="+topic);
				js.put("source_name",source_name);
				js.put("original_weight", original_weight);
				list.add(js);
			}
		} catch (Exception e) {

		}
		return list.toJSONString();
	}

}
