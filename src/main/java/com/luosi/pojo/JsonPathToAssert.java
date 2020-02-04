package com.luosi.pojo;

public class JsonPathToAssert {
	//断言预期jsonpath的值
	private String value;
	//断言预期jsonpath的表达式
	private String expression;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
