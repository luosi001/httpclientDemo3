package com.luosi.cases;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.luosi.constants.Constants;
import com.luosi.pojo.API;
import com.luosi.pojo.Case;
import com.luosi.utils.AuthenticationUtils;
import com.luosi.utils.DataUtils;
import com.luosi.utils.ExcelUtils;

public class LoginCase extends BaseCase{
	private Logger log = Logger.getLogger(LoginCase.class);
	
	/**
	 * 虽然写在登录的类中，但是在testng.xml中配置的当前套件执行之前执行该方法的
	 */
	@BeforeSuite
	public void init() {
		log.info("*******************套件初始化********************");
		//套件执行初始化
		//配置参数化变量
		AuthenticationUtils.env.put(Constants.REGISTER_MOBILEPHONE_TEXT,Constants.REGISTER_MOBILEPHONE_VALUE);
		AuthenticationUtils.env.put(Constants.REGISTER_PASSWORD_TEXT,Constants.REGISTER_PASSWORD_VALUE);
		AuthenticationUtils.env.put(Constants.LOGIN_MOBILEPHONE_TEXT,Constants.LOGIN_MOBILEPHONE_VALUE);
		AuthenticationUtils.env.put(Constants.LOGIN_PASSWORD_TEXT,Constants.LOGIN_PASSWORD_VALUE);
	}
	
	@Test(dataProvider="datas", description="登录测试")
	public void testLogin(API api,Case c) {
		log.info("*******************登录测试********************");
		//v8 参数化替换
		String params = paramReplace(c.getParams());
		//System.out.println("params:" + params);
		String sql = paramReplace(c.getSql());
		//System.out.println("sql:" + sql);
		c.setParams(params);
		c.setSql(sql);
		//调用接口
		String content = call(api, c);
		//回写response poi+list循环
		addWriteBackData(c.getCaseId(), Constants.ACTUAL_RESPONSE_DATA_CELLNUM , content);
		//断言接口响应内容
		boolean assertResponseData = assertResponseData(c, content);
		System.out.println("响应断言结果：" + assertResponseData);
		//断言结果回写
		String passContent = (assertResponseData) ? "Pass":"Fail";
		addWriteBackData(c.getCaseId(),Constants.ASSERT_DATA_CELLNUM ,passContent);
		//报表断言
		Assert.assertEquals(passContent, "Pass");
	}

	@AfterSuite
	public void finish() {
		//套件执行完毕后，执行批量回写
		ExcelUtils.write(Constants.EXCEL_PATH, 1);
	}
	
	@DataProvider(name="datas")
	public Object[][] datas(){
		Object[][] datas = DataUtils.getApiAndCaseByApiId("2");
		return datas;
	}
}
