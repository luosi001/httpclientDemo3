package com.luosi.utils;

import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

public class StringUtils {
	
	public static String json2KeyValue(String jsonStr) {
		//json转换为map
		Map<String,String> map = JSONObject.parseObject(jsonStr,Map.class);
		Set<String> keySet = map.keySet();
		String str = "";
		//map转换成key=value&key1=value1格式
		for (String key : keySet) {
			String value = map.get(key);
			if(!"".equals(str)) {
				str += "&" ;
			}
			str += key + "=" + value ;
		}
		return str;
	}
}
