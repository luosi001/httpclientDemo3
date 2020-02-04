package com.luosi.cases;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONPath;
import com.luosi.constants.Constants;
import com.luosi.pojo.API;
import com.luosi.pojo.Case;
import com.luosi.utils.DataUtils;
import com.luosi.utils.SQLUtils;

public class RechargeCase extends BaseCase{
	private Logger log =  Logger.getLogger(RechargeCase.class);
	
	@Test(dataProvider="datas", description="充值测试")
	public void testRecharge(API api,Case c) {
		log.info("*******************充值测试********************");
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
			System.out.println("数据库断言结果：" + assertSQL(beforeResult,afterResult,c));
			assertSQL = assertSQL(beforeResult,afterResult,c);
		}
		String passContent = (assertResponseData && assertSQL) ? "Pass":"Fail";
		//断言结果回写
		addWriteBackData(c.getCaseId(),Constants.ASSERT_DATA_CELLNUM ,passContent);
		//报表断言
		Assert.assertEquals(passContent, "Pass");
	}
	
	public boolean assertSQL(Object beforeSQLResult,Object afterSQLResult,Case c) {
		System.out.println("c.getParams()" + c.getParams());
		String moneyStr = JSONPath.read(c.getParams(), "$.amount").toString();
		String beforeResultStr = beforeSQLResult.toString();
		String afterResultStr = afterSQLResult.toString();
		double beforeMoney = Double.parseDouble(beforeResultStr);
		double afterMoney = Double.parseDouble(afterResultStr);
		double money = Double.parseDouble(moneyStr);
		return afterMoney - beforeMoney == money;
	}
	
	@DataProvider(name="datas")
	public Object[][] datas(){
		Object[][] datas = DataUtils.getApiAndCaseByApiId("3");
		return datas;
	}
}
