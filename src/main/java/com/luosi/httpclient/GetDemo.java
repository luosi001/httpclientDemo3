package com.luosi.httpclient;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GetDemo {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		//1.创建一个request，携带了method 和 URL
		HttpGet httpGet = new HttpGet("http://api.lemonban.com/futureloan/loans");
		httpGet.addHeader("X-Lemonban-Media-Type","lemonban.v1");
		//2.发送请求的客户端
		CloseableHttpClient client =  HttpClients.createDefault();
		//3.发送请求并接收响应
		CloseableHttpResponse response = client.execute(httpGet);
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
