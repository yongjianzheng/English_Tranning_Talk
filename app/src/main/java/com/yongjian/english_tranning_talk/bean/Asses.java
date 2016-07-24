package com.yongjian.english_tranning_talk.bean;

import java.io.Serializable;

public class Asses implements Serializable {

	/**
	 * 类名：Asses 说明：评价内容对象
	 */
	private static final long serialVersionUID = 1L;

	private int t_id;
	private String t_name;
	private String assgrade;
	private String content;
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public String getT_name() {
		return t_name;
	}
	public void setT_name(String t_name) {
		this.t_name = t_name;
	}
	public String getAssgrade() {
		return assgrade;
	}
	public void setAssgrade(String assgrade) {
		this.assgrade = assgrade;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Asses(int t_id, String t_name) {
		this.t_id = t_id;
		this.t_name = t_name;
	}
	public Asses(){
		
	}
}
