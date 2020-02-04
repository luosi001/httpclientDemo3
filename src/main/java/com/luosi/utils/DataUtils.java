package com.luosi.utils;

import java.util.ArrayList;
import java.util.List;

import com.luosi.constants.Constants;
import com.luosi.pojo.API;
import com.luosi.pojo.Case;

public class DataUtils {
	public static List<API> apiList = ExcelUtils.read(Constants.EXCEL_PATH,0,API.class);
	public static List<Case> caseList = ExcelUtils.read(Constants.EXCEL_PATH,1,Case.class);
	
	//根据传入的apiid获取一个api和多个case
	public static Object[][] getApiAndCaseByApiId(String apiId) {
		API wantApi = null;
		List<Case> wantCases = new ArrayList<Case>();
		//获取wantApi
		for (API api : apiList) {
			if(apiId.equals(api.getId())){
				wantApi = api;
				break;
			}
		}
		//获取wantCases
		for(Case c : caseList) {
			if(apiId.equals(c.getApiId())) {
				wantCases.add(c);
			}
		}
		//把wantApi和wantCases合并,即存放到二维数组中
		Object[][] datas = new Object[wantCases.size()][2];
		for (int i = 0; i < wantCases.size(); i++) {
			datas[i][0] = wantApi;
			datas[i][1] = wantCases.get(i);
		}
		return datas;
	}
}
