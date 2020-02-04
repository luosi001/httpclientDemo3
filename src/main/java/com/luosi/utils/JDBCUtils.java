package com.luosi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.luosi.constants.Constants;

public class JDBCUtils {
	
	public static Connection getConnection() {
		//定义数据库连接对象
		Connection conn = null;
		try {
			//jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8
			conn = DriverManager.getConnection(Constants.JDBC_URL, Constants.JDBC_USER, Constants.JDBC_PWD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
