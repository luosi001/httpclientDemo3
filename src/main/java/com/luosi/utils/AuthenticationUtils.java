package com.luosi.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpRequestBase;

import com.alibaba.fastjson.JSONPath;
import com.luosi.constants.Constants;

public class AuthenticationUtils {
	//存储环境变量
	public static Map<String,String> env = new HashMap<String,String>();
	/**
	 * 从响应体中获取token信息存储到缓存中
	 * @param responseBody
	 */
	public static void storeToken(String responseBody) {
		System.out.println("responseBody:" + responseBody);
		//1.取出token  command+2 l
		Object token = JSONPath.read(responseBody, "$data.token_info.token");
		//2.存储
		if(token != null) {
			env.put("token", token.toString());
			Object member_id = JSONPath.read(responseBody, "$.data.id");
			if(member_id != null) {
				AuthenticationUtils.env.put(Constants.MEMBER_ID,member_id.toString());
			}
		}
	}
	
	public static void addToken(HttpRequestBase request) {
		String token = env.get("token");
		if(token != null) {
			System.out.println("token:" + token);
			request.addHeader("Authorization","Bearer "+token);
		}
	}
}
