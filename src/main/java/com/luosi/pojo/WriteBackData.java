package com.luosi.pojo;

public class WriteBackData {
	//行
	private int rowNum;
	//列
	private int colNum;
	//内容
	private String content;
	public WriteBackData() {
	}
	public WriteBackData(int rowNum, int colNum, String content) {
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.content = content;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
