package com.luosi.constants;

public class Constants {
	//案例路径
	public static final String EXCEL_PATH = "src/main/resources/cases_v8.xlsx";
	//回写响应结果到excel中的列的编号
	public static final int ACTUAL_RESPONSE_DATA_CELLNUM = 5;
	//回写断言结果到excel中的列的编号
	public static final int ASSERT_DATA_CELLNUM = 6;
	//数据库连接地址
	public static final String JDBC_URL = "jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
	//数据库连接用户名
	public static final String JDBC_USER = "future";
	//数据库连接密码
	public static final String JDBC_PWD = "123456";
	
	// 参数化替换字段
	public static final String REGISTER_MOBILEPHONE_TEXT = "${toBeRegisterMobilephone}";
	public static final String REGISTER_PASSWORD_TEXT = "${toBeRegisterPassword}";
	public static final String LOGIN_MOBILEPHONE_TEXT = "${toBeLoginMobilephone}";
	public static final String LOGIN_PASSWORD_TEXT = "${toBeLoginPassword}";
	public static final String MEMBER_ID = "${member_id}";
	// 参数化替换值
	public static final String REGISTER_MOBILEPHONE_VALUE = getTel();	//注册时获取随机号码
	public static final String REGISTER_PASSWORD_VALUE = "12345678";
	public static final String LOGIN_MOBILEPHONE_VALUE = REGISTER_MOBILEPHONE_VALUE;	//登录号码取注册的
	public static final String LOGIN_PASSWORD_VALUE = "12345678";
	
	public static int getNum(int start, int end) {
		return (int) (Math.random() * (end - start + 1) + start);
	}

	private static String getTel() {
		String[] telFirst = { "134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159", "130",
				"131", "132", "155", "156", "133", "153" };
		int index = getNum(0, telFirst.length - 1);
		String first = telFirst[index];
		String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
		String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
		return first + second + third;
	}

	public static void main(String[] args) {
		System.out.println(getTel());
	}	
}
