package com.luosi.cases;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.luosi.constants.Constants;
import com.luosi.pojo.API;
import com.luosi.pojo.Case;
import com.luosi.pojo.JsonPathToAssert;
import com.luosi.pojo.WriteBackData;
import com.luosi.utils.AuthenticationUtils;
import com.luosi.utils.ExcelUtils;
import com.luosi.utils.HttpUtils;

import io.qameta.allure.Step;

public class BaseCase {
	/**
	 * 参数化替换方法
	 * @param params
	 * @return
	 */
	@Step("参数化替换{params}")
	public String paramReplace(String params) {
		if(StringUtils.isBlank(params)) {
			return "";
		}
		Set<String> keySet = AuthenticationUtils.env.keySet();
		for (String key : keySet) {
			String value = AuthenticationUtils.env.get(key);
			params = params.replace(key, value);
		}
		return params;
	}
	
	/**
	 * 传入用例，调用http并返回响应
	 * @param api
	 * @param c
	 * @return
	 */
	@Step("调用http请求{api.url}/{c.params}")
	public String call(API api,Case c) {
		String url = api.getUrl();
		String params = c.getParams();
		String type = api.getType();
		String contentType = api.getContentType();
		//测试登录接口
		String content = HttpUtils.call(url, params, type, contentType);
		return content;
	}
	
	/**
	 * 批量回写响应到excel案例中
	 * @param c
	 * @param content
	 */
	public void addWriteBackData(Case c ,String content) {
		//案例编号作为行号
		int rowNum = c.getCaseId();
		//列值固定为第5列
		int cellNum = Constants.ACTUAL_RESPONSE_DATA_CELLNUM;
		//添加到回写集合中
		addWriteBackData(rowNum , cellNum , content);
	}
	
	@Step("回写响应到excel{content}")
	public void addWriteBackData(int rowNum ,int cellNum ,String content) {
		//添加到回写集合中
		ExcelUtils.wbdList.add(new WriteBackData(rowNum, cellNum, content));
	}
	
	/**
	 * 断言接口响应,第一种判断采用json数组的多关键字匹配，第二种采用json对象的等值匹配
	 * @param c
	 * @param content
	 * @return
	 */
	@Step("断言接口响应")
	public boolean assertResponseData(Case c, String content) {
		//获取excel中案例的期望数据
		String expectData = c.getExpectData();
		Object parse = JSONObject.parse(expectData);
		//判断parse是不是JSONArray对象
		if(parse instanceof JSONArray) {
			//如果是数组类型，就采用多关键字匹配
			//期望数据转换成list集合
			List<JsonPathToAssert> list = JSONObject.parseArray(expectData, JsonPathToAssert.class);
			for (JsonPathToAssert jsonPathToAsert : list) {
				//通过jsonpath表达式获取实际响应中的实际值
				Object actualValue = JSONPath.read(content, jsonPathToAsert.getExpression());
				//比较期望值与实际值
				boolean flag = jsonPathToAsert.getValue().equals(actualValue.toString());
				//其中某个断言失败
				if(!flag) {
					return false;
				}
			}
			return true;
		}else if(parse instanceof JSONObject){	//判断parse是不是JSONObject对象
			//如果是对象类型，就采用等值匹配
			return expectData.equals(content);
		}
		return false;
	}
}
