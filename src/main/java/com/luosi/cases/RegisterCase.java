package com.luosi.cases;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.luosi.constants.Constants;
import com.luosi.pojo.API;
import com.luosi.pojo.Case;
import com.luosi.utils.DataUtils;
import com.luosi.utils.SQLUtils;

import io.qameta.allure.Step;

public class RegisterCase extends BaseCase{
	private Logger log =  Logger.getLogger(RegisterCase.class);
	
	@Test(dataProvider="datas", description="注册测试")
	public void testRegister(API api,Case c) {
		log.info("*******************注册测试********************");
		//v8 参数化替换
		String params = paramReplace(c.getParams());
		//System.out.println("params:" + params);
		String sql = paramReplace(c.getSql());
		//System.out.println("sql:" + sql);
		c.setParams(params);
		c.setSql(sql);
		//数据库前置查询
		Object beforeResult = SQLUtils.query(c.getSql());
		//调用接口
		String content = call(api, c);
		//回写response
		addWriteBackData(c.getCaseId(), Constants.ACTUAL_RESPONSE_DATA_CELLNUM , content);
		//断言接口响应内容
		boolean assertResponseData = assertResponseData(c, content);
		System.out.println("响应断言结果：" + assertResponseData);
		//数据库后置查询
		Object afterResult = SQLUtils.query(c.getSql());
		boolean assertSQL = true;
		//数据库断言
		if (StringUtils.isNoneBlank(c.getSql())) {
			System.out.println("数据库断言结果：" + assertSQL(beforeResult,afterResult));
			assertSQL = assertSQL(beforeResult,afterResult);
		}
		String passContent = (assertResponseData && assertSQL) ? "Pass":"Fail";
		//断言结果回写
		addWriteBackData(c.getCaseId(),Constants.ASSERT_DATA_CELLNUM ,passContent);
		//报表断言
		Assert.assertEquals(passContent, "Pass");
	}
	@Step("执行sql断言")
	public boolean assertSQL(Object beforeResult,Object afterResult) {
		if((Long)beforeResult == 0 && (Long)afterResult == 1) {
			return true;
		}else {
			return false;
		}
	}
	
	@DataProvider(name="datas")
	public Object[][] datas(){
		Object[][] datas = DataUtils.getApiAndCaseByApiId("1");
		return datas;
	}
}
