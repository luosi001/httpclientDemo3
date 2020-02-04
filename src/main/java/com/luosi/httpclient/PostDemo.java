package com.luosi.httpclient;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PostDemo {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		//1.创建一个request，携带了method 和 URL
		HttpPost post = new HttpPost("http://api.lemonban.com/futureloan/member/login");
		//1.1添加请求头
		post.addHeader("X-Lemonban-Media-Type","lemonban.v2");
		post.addHeader("Content-Type","application/json");	//json格式
		//1.2添加请求体
		StringEntity requestEntity = new StringEntity("{\"mobile_phone\":\"15177777777\",\"pwd\":\"15177777777\"}");
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
	}

}
