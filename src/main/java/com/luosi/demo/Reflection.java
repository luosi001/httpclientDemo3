package com.luosi.demo;

import com.luosi.pojo.API;

public class Reflection {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		//反射：java在运行的时候动态加载一个类，并且创建对象，调用属性和方法
		//反射的基本：必须要有字节码对象
		Class clazz = API.class;
		API api = new API();
		Class clazz2 = api.getClass();
		Class clazz3 = Class.forName("com.luosi.pojo.API");
		//调用空参构造
		Object obj = clazz3.newInstance();
		System.out.println(obj);
	}
}
