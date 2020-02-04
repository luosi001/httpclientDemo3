package com.luosi.utils;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import com.luosi.constants.Constants;
import com.luosi.pojo.API;
import com.luosi.pojo.Case;
import com.luosi.pojo.WriteBackData;

public class ExcelUtils {
	//存储回写数据的list集合
	public static List<WriteBackData> wbdList = new ArrayList<WriteBackData>();
	
	//static<T> 定义T，可以没有static
	public static<T> List<T> read(String path,int sheetIndex,Class<T> clazz) {
		FileInputStream fis = null;
		try {
			//加载excel的流对象
			fis = new FileInputStream(path);
			//导入参数对象（默认值：读取第一个sheet且只读取第一个；表头是sheet的第一行且只有一行）
			ImportParams params = new ImportParams();
			params.setNeedVerify(true);
			params.setStartSheetIndex(sheetIndex);
			//工具解析excel封装成list
			List<T> list = ExcelImportUtil.importExcel(fis, clazz, params);
			System.out.println(list);
			return list;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(fis);
		}
		return null;
	}
	
	public static void write(String path,int sheetIndex) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		//找到Excel文件
		try {
			fis = new FileInputStream(path);
			//打开
			Workbook workbook = WorkbookFactory.create(fis); 
			//获取sheet
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			//循环集合获取行、列，修改内容
			for (WriteBackData wbd : wbdList) {
				Row row = sheet.getRow(wbd.getRowNum());
				Cell cell = row.getCell(wbd.getColNum(),MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(wbd.getContent());
			}
			fos = new FileOutputStream(Constants.EXCEL_PATH);
			workbook.write(fos);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			//关流
			close(fis);
			close(fos);
		}
		
	}
	
	/*public static void read(String path) throws Exception {
		//加载excel的流对象
		FileInputStream fis = new FileInputStream(path);
		//导入参数对象（默认值：读取第一个sheet且只读取第一个；表头是sheet的第一行且只有一行）
		ImportParams params = new ImportParams();
		params.setNeedVerify(true);
		params.setStartSheetIndex(0);
		//工具解析excel封装成list
		List<API> list = ExcelImportUtil.importExcel(fis, API.class, params);
		System.out.println(list);
	}
	public static void read2(String path) throws Exception {
		//加载excel的流对象
		FileInputStream fis = new FileInputStream(path);
		//导入参数对象（默认值：读取第一个sheet且只读取第一个；表头是sheet的第一行且只有一行）
		ImportParams params = new ImportParams();
		params.setNeedVerify(true);
		params.setStartSheetIndex(1);
		//工具解析excel封装成list
		List<API> list = ExcelImportUtil.importExcel(fis, Case.class, params);
		System.out.println(list);
	}*/
	
	private static void close(Closeable stream) {
		if(stream != null) {
			try {
				stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		//ExcelUtils.read("src/main/resources/cases_v3.xlsx");
		List<API> list1 = ExcelUtils.read("src/main/resources/cases_v3.xlsx",0,API.class);
		List<Case> list2 = ExcelUtils.read("src/main/resources/cases_v3.xlsx",1,Case.class);
		//怎样将Case和API关联起来
		//方式1.新增一个类，一个属性是API，另一个属性是Case
		//方式2.把Case和API放到数组中{{API,Case},{API,Case},{API,Case}}
		
	}
}
