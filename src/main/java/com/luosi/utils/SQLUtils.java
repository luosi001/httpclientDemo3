package com.luosi.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import io.qameta.allure.Step;

public class SQLUtils {

	/**
	 * 调用DBUtils执行对应的查询sql语句，并返回查询结果
	 * @param sql
	 * @return
	 */
	@Step("执行sql{sql}")
	public static Object query(String sql) {
		if(StringUtils.isBlank(sql)) {
			return null;
		}
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtils.getConnection();
		//创建返回结果类型对象
		ScalarHandler rsh = new ScalarHandler();
		try {
			//执行查询
			Object result = qr.query(conn, sql, rsh);
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtils.close(conn);
		}
		return null;
	}
	
	public static void main(String[] args) {
		String sql = "select count(*) from member where id=70368";
		System.out.println(query(sql));
	}
}
