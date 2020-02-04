package com.luosi.pojo;

import javax.validation.constraints.NotNull;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Case {

	//用例编号
	@Excel(name="用例编号")
	@NotNull
	private int caseId;
	//用例描述
	@Excel(name="用例描述")
	@NotNull
	private String desc;
	//参数
	@Excel(name="参数")
	@NotNull
	private String params;
	//接口编号
	@Excel(name="接口编号")
	@NotNull
	private String apiId;
	//期望响应数据
	@Excel(name="期望响应数据")
	@NotNull
	private String expectData;
	//期望响应数据
	@Excel(name="检验SQL")
	private String sql;
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Case() {
	}
	public Case(int caseId, String desc, String params, String apiId) {
		this.caseId = caseId;
		this.desc = desc;
		this.params = params;
		this.apiId = apiId;
	}
	
	@Override
	public String toString() {
		return "Case [caseId=" + caseId + ", desc=" + desc + ", params=" + params + ", apiId=" + apiId + ", expectData="
				+ expectData + ", sql=" + sql + "]";
	}
	public String getExpectData() {
		return expectData;
	}
	public void setExpectData(String expectData) {
		this.expectData = expectData;
	}
	public int getCaseId() {
		return caseId;
	}
	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	
}
