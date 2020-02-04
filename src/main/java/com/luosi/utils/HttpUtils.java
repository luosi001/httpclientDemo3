package com.luosi.utils;

import java.io.IOException;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {
	private static Logger log =  Logger.getLogger(HttpUtils.class);
	public static BasicHeader lemonBanHeader = new BasicHeader("X-Lemonban-Media-Type","lemonban.v2");
	/**
	 * 调用httpClient发送http请求，返回字符串类型的响应体
	 * @param url 接口地址
	 * @param param 接口参数（json格式)
	 * @param type 接口method
	 * @param contentType 接口参数类型
	 * @return
	 */
	public static String call(String url,String param,String type,String contentType) {
		if("json".equalsIgnoreCase(contentType)) {
			if("post".equalsIgnoreCase(type)) {
				return HttpUtils.testPostJson(url, param);
			}else if("get".equalsIgnoreCase(type)) {
				return HttpUtils.testGetJson(url, param);
			}else if("patch".equalsIgnoreCase(type)) {
				//HttpUtils.testPatchJson(url, param);
				return "";
			}
		}else if("form".equalsIgnoreCase(contentType)){
			//param是json格式，可以通过fastjson转成map对象，再将map转换成需要的字符串
			return HttpUtils.testPostForm(url, StringUtils.json2KeyValue(param));
		}
		return "";
	}
	
	public static String testGetJson(String url,String param) {
		//1.创建一个request，携带了method 和 URL
		HttpGet httpGet = new HttpGet(url);
		//httpGet.addHeader("X-Lemonban-Media-Type","lemonban.v1");
		httpGet.addHeader(lemonBanHeader);
		//2.发送请求的客户端
		CloseableHttpClient client =  HttpClients.createDefault();
		//3.发送请求并接收响应
		CloseableHttpResponse response;
		try {
			response = client.execute(httpGet);
			//4.1获取所有响应头
			Header[] allHeader = response.getAllHeaders();
			//4.2获取状态吗
			int statusCode = response.getStatusLine().getStatusCode();
			//4.3获取响应体
			HttpEntity entity = response.getEntity();
			//4.4格式化响应体
			String body = EntityUtils.toString(entity);
			System.out.println(body);
			return body;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static String testPostJson(String url,String param){
		//1.创建一个request，携带了method 和 URL
		HttpPost post = new HttpPost(url);
		//1.1添加请求头
		//post.addHeader("X-Lemonban-Media-Type","lemonban.v2");
		post.addHeader(lemonBanHeader);
		post.addHeader("Content-Type","application/json");	//json格式
		//1.2添加请求体
		StringEntity requestEntity;
		try {
			requestEntity = new StringEntity(param,"utf-8");
			//form格式
			//post.addHeader("Content-Type","application/x-www-form-urlencoded");
			//StringEntity requestEntity = new StringEntity("mobile_phone=15177777777&pwd=15177777777");
			post.setEntity(requestEntity);
			//1.3添加鉴权请求头
			AuthenticationUtils.addToken(post);
			//2.创建发送请求的客户端
			CloseableHttpClient client = HttpClients.createDefault();
			//3.发送请求并接收响应
			//创建代理对象 HttpHost(代理地址,代理端口)  --》此过程了解即可，可不使用代理
			//HttpHost proxy = new HttpHost("127.0.0.1",8089);
			//CloseableHttpResponse response = client.execute(proxy,post);
			CloseableHttpResponse response = client.execute(post);
			//4.1获取所有响应头
			Header[] allHeader = response.getAllHeaders();
			//4.2获取状态吗
			int statusCode = response.getStatusLine().getStatusCode();
			//4.3获取响应体
			HttpEntity entity = response.getEntity();
			//4.4格式化响应体
			String body = EntityUtils.toString(entity);
			//5.输出响应
			/*for (Header header : allHeader) {
				System.out.println("Headers:" + header);
			}*/
			//System.out.println("statusCode:" + statusCode);
			//System.out.println("body:" + body);
			log.info("Headers:" + Arrays.toString(allHeader));
			log.info("statusCode:" + statusCode);
			log.info("body:" + body);
			//6.toke存储
			AuthenticationUtils.storeToken(body);
			return body;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static String testPostForm(String url,String param){
		//1.创建一个request，携带了method 和 URL
		HttpPost post = new HttpPost(url);
		//1.1添加请求头
		post.addHeader("Content-Type","application/x-www-form-urlencoded");	//form格式
		//1.2添加请求体
		StringEntity requestEntity;
		try {
			requestEntity = new StringEntity(param,"utf-8");
			//form格式
			//post.addHeader("Content-Type","application/x-www-form-urlencoded");
			//StringEntity requestEntity = new StringEntity("mobile_phone=15177777777&pwd=15177777777");
			post.setEntity(requestEntity);
			//2.创建发送请求的客户端
			CloseableHttpClient client = HttpClients.createDefault();
			//3.发送请求并接收响应
			//创建代理对象 HttpHost(代理地址,代理端口)  --》此过程了解即可，可不使用代理
			//HttpHost proxy = new HttpHost("127.0.0.1",8089);
			//CloseableHttpResponse response = client.execute(proxy,post);
			CloseableHttpResponse response = client.execute(post);
			//4.1获取所有响应头
			Header[] allHeader = response.getAllHeaders();
			//4.2获取状态吗
			int statusCode = response.getStatusLine().getStatusCode();
			//4.3获取响应体
			HttpEntity entity = response.getEntity();
			//4.4格式化响应体
			String body = EntityUtils.toString(entity);
			System.out.println(body);
			return body;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args){
		/*String url = "http://api.lemonban.com/futureloan/loans";
		HttpUtils.testGetJson(url, null);
		System.out.println("*****************");
		String url2 = "http://api.lemonban.com/futureloan/member/95171/info";
		HttpUtils.testGetJson(url2, "");
		System.out.println("******************");
		String url3 = "http://api.lemonban.com/futureloan/member/login";
		String params = "{\"mobile_phone\":\"15177777777\",\"pwd\":\"15177777777\"}";
		HttpUtils.testPostJson(url3, params);*/
		String url4 = "http://test.lemonban.com/futureloan/mvc/api/member/register";
		String params = "mobilephone=15177777777&pwd=15177777777";
		HttpUtils.testPostForm(url4, params);
		
	}
}
