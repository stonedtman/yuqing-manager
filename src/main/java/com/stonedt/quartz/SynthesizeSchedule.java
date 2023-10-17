package com.stonedt.quartz;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.stonedt.dao.SynthesizeDao;
import com.stonedt.entity.Synthesize;
import com.stonedt.util.HotWordsUtil;
import com.stonedt.vo.FullSearchParam;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.stonedt.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 综合看板定时任务
 */
@Component
public class SynthesizeSchedule {

    // 定时任务开关
    @Value("${schedule.synthesize.open}")
    private Integer schedule_synthesize_open;

	@Autowired
	private SynthesizeDao synthesizeDao;
	
    /**
     * 模板消息
     */

//	@PostConstruct
 //   @Scheduled(cron = "0 30 4 * * ?")
   	//@Scheduled(fixedDelay = 1000*60*2)
 	//
	
	  //@Scheduled(cron = "0 0/30 * * * ?")
	@Scheduled(cron = "0 0 0/2 * * ?")
    public void popularInformation() {
    	if(schedule_synthesize_open==1) {
    		//获取accesstoken
			System.out.println("开始生成综合看板");
			String hot_all = "";
			String hot_weibo = "";
			String hot_wechat = "";
			String hot_search_terms = "";
			String hot_douyin = "";
			String hot_bilibili = "";
			String hot_tecentvedio = "";
			String hot_policydata = "";
			String hot_finaceData = "";
			String hot_36kr ="";
			FullSearchParam searchParam = new FullSearchParam();
			searchParam.setPageNum(1);
			searchParam.setPageSize(50);
			searchParam.setSearchWord("");
			searchParam.setClassify("4");
			searchParam.setTimeType(1);
			
			//热点事件
			searchParam.setSource_name("百度风云榜");
			//JSONObject hotList = fullSearchService.hotList(searchParam);
			hot_all = HotWordsUtil.search2();
			
			//热门微博
			searchParam.setSource_name("微博");
			//JSONObject hotList2 = fullSearchService.hotList(searchParam);
			//hot_weibo =conversionHotList(hotList2);
			hot_weibo = HotWordsUtil.hotWeibo();
			
			//热门微信
			searchParam.setSource_name("微信");
			
			//JSONObject hotListWechat = fullSearchService.hotList(searchParam);
			//hot_wechat =conversionHotList(hotListWechat);
			hot_wechat = HotWordsUtil.hotWechat();
			
			searchParam.setPageSize(10);
			searchParam.setClassify("1");
			//热门科技
			searchParam.setSource_name("36kr");
			
			//JSONObject hotList36kr = fullSearchService.hotList(searchParam);
			//hot_36kr =conversionHotList(hotList36kr);
			
			hot_36kr = HotWordsUtil.hot36Kr();
			
			searchParam.setClassify("2");
			searchParam.setTimeType(2);
			searchParam.setPageSize(50);
			//热门抖音
			//searchParam.setSource_name("抖音");
			
			///JSONObject hotListDouyin = fullSearchService.hotList(searchParam);
			//hot_douyin =conversionHotList(hotListDouyin);
			
			hot_douyin = HotWordsUtil.hotDouyin();
			
			
			//热门哔哩哔哩
			//searchParam.setSource_name("哔哩哔哩");
			
			//JSONObject hotListBiLiBiLi = fullSearchService.hotList(searchParam);
			//hot_bilibili =conversionHotList(hotListBiLiBiLi);
			hot_bilibili =HotWordsUtil.hotBilibili();
			//热门腾讯视频
			//searchParam.setSource_name("腾讯视频");
			
			//JSONObject hotListTecentVedio = fullSearchService.hotList(searchParam);
			
			
			//hot_tecentvedio =conversionHotList(hotListTecentVedio);
			hot_tecentvedio =HotWordsUtil.hotTecent();
			
			hot_search_terms = HotWordsUtil.search();
			
			//政策--------国务院 > 首页 > 政策 > 最新    http://www.gov.cn/zhengce/zuixin.htm
			
			hot_policydata = getPolicyData();
			
			//经济--------东方财富网(国内经济首页 > 财经频道 > 焦点 > 国内经济) http://finance.eastmoney.com/a/cgnjj.html
			
			hot_finaceData = getFinaceData();
			
				try {


					Synthesize synthesize = new Synthesize();
					synthesize.setHot_all(hot_all);
					synthesize.setHot_weibo(hot_weibo);
					synthesize.setHot_wechat(hot_wechat);
					synthesize.setHot_douyin(hot_douyin);
					synthesize.setHot_bilibili(hot_bilibili);
					synthesize.setHot_tecentvedio(hot_tecentvedio);
					synthesize.setHot_search_terms(hot_search_terms);
					synthesize.setHot_policydata(hot_policydata);
					synthesize.setHot_finaceData(hot_finaceData);
					synthesize.setHot_36kr(hot_36kr);
					synthesize.setUser_id(1L);
					conversionHotList(synthesize);
					synthesizeDao.insertSynthesize(synthesize);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}

			
    }

	
	
	public static void main(String[] args) {
		String policyData = getFinaceData();
		System.out.println(policyData);
	}
	
	
	/**
	 * 政策数据
	 * @return
	 */
	public static String getPolicyData() {
		
		String url = "https://www.gov.cn/zhengce/zuixin/home.htm";

		JSONArray array = new JSONArray();
		try {
			String gethtml = null;
			for (int i = 0; i < 3; i++) {
				gethtml = gethtml(url);
				if(gethtml!=null) {
					break;
				}
			}
			if (gethtml==null) {
				return null;
			}
			Document parse = Jsoup.parse(gethtml);
			Elements select = parse.select(".news_box > .list > ul > li");
			for (int i = 0; i < select.size()&& i<5; i++) {
				JSONObject object = new JSONObject();
				Element element = select.get(i);
				String source_url = element.getElementsByTag("a").get(0).attr("href");
				object.put("source_url", source_url);
				int rank = i+1;
				object.put("rank", rank);
				object.put("original_weight", 100000);
				
				object.put("source_name", "国务院");
				String topic = element.getElementsByTag("a").get(0).text();
				object.put("topic", topic);
				
				String publish_time = element.getElementsByClass("date").get(0).text();
				object.put("publish_time", publish_time+" 00:00:00");
				array.add(object);
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return array.toJSONString();
		
		
		
		
	}
	
	
	/**
	 * 经济数据
	 * @return
	 */
	public static String getFinaceData() {
		
		String url = "https://np-listapi.eastmoney.com/comm/web/getNewsByColumns?client=web&biz=web_news_channel&column=350&order=1&needInteractData=0&page_index=1&page_size=20&req_trace=1666952448875&fields=code,showTime,title,mediaName,summary,image,url,uniqueUrl";
		JSONArray array = new JSONArray();
		try {
			String gethtml = null;
			for (int i = 0; i < 3; i++) {
				gethtml = gethtml(url);
				if(gethtml!=null) {
					break;
				}
			}
			if (gethtml==null) {
				return null;
			}
			
			//Document parse = Jsoup.parse(gethtml);
			//Elements select = parse.select(".artitleList ul li");
			
			JSONArray select = JSONObject.parseObject(gethtml).getJSONObject("data").getJSONArray("list");
			//JSONArray select = JSONArray.parseArray(gethtml);
			
			
			for (int i = 0; i < select.size(); i++) {
				
				JSONObject object = new JSONObject();
				JSONObject parseObject = JSONObject.parseObject(select.get(i).toString());
				
				String topic = parseObject.getString("title");
				if(!topic.equals("")) {
					String source_url = parseObject.getString("uniqueUrl");
					object.put("source_url", source_url);
					int rank = i+1;
					object.put("rank", rank);
					object.put("original_weight", 100000);
					object.put("source_name", "东方财富网");
					object.put("topic", topic);
					
	               String publish_time = parseObject.getString("showTime");
			       //String publish_time = element.getElementsByClass("time").get(0).text();
				   publish_time = publish_time.replaceAll("月", "-").replaceAll("日", "");
					try {
						object.put("publish_time", DateUtil.FormatDate(publish_time));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					array.add(object);
				}
				
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return array.toJSONString();
		
		
		
		
	}
	
	
	
	
	public static String gethtml(String url) throws ParseException, IOException, InterruptedException {

		org.apache.http.client.CookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.createDefault();

		Thread.sleep(1);
		String string = null;
		HttpGet httpget = new HttpGet(url);
		RequestConfig config = RequestConfig.custom().setConnectTimeout(10 * 1000).setSocketTimeout(20 * 1000).build();
		httpget.setConfig(config);
		httpget.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		
		CloseableHttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		Integer statu = response.getStatusLine().getStatusCode();
		List<Cookie> cookies = null;
		if (entity != null) {
			string = EntityUtils.toString(entity, "utf-8");
			cookies = cookieStore.getCookies();
		}
		response.close();
		httpclient.close();
		return string;
	}


	/**
	 * 数据替换
	 */
	public void conversionHotList(Synthesize synthesize) {
		Synthesize oldSynthesize = synthesizeDao.getNewSynthesize();
		if (synthesize.getHot_all()==null) {
			synthesize.setHot_all(oldSynthesize.getHot_all());
		}
		if (synthesize.getHot_weibo()==null) {
			synthesize.setHot_weibo(oldSynthesize.getHot_weibo());
		}
		if (synthesize.getHot_wechat()==null) {
			synthesize.setHot_wechat(oldSynthesize.getHot_wechat());
		}
		if (synthesize.getHot_douyin()==null) {
			synthesize.setHot_douyin(oldSynthesize.getHot_douyin());
		}
		if (synthesize.getHot_bilibili()==null) {
			synthesize.setHot_bilibili(oldSynthesize.getHot_bilibili());
		}
		if (synthesize.getHot_tecentvedio()==null) {
			synthesize.setHot_tecentvedio(oldSynthesize.getHot_tecentvedio());
		}
		if (synthesize.getHot_search_terms()==null) {
			synthesize.setHot_search_terms(oldSynthesize.getHot_search_terms());
		}
		if (synthesize.getHot_policydata()==null) {
			synthesize.setHot_policydata(oldSynthesize.getHot_policydata());
		}
		if (synthesize.getHot_finaceData()==null) {
			synthesize.setHot_finaceData(oldSynthesize.getHot_finaceData());
		}
		if (synthesize.getHot_36kr()==null) {
			synthesize.setHot_36kr(oldSynthesize.getHot_36kr());
		}

	}




}
