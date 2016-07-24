package com.yongjian.english_tranning_talk.bean;

import java.io.Serializable;

public class OrderList implements Serializable {

	/**
	 * 类名：orderlist 说明 ：订单对象
	 */
	private static final long serialVersionUID = 1L;
    
	private int l_id;
	
	private int t_id;
	private String cost;
	private String date;
	private String duration;
	
	public int getL_id() {
		return l_id;
	}
	public void setL_id(int l_id) {
		this.l_id = l_id;
	}
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public OrderList(int l_id, int t_id, String cost, String date, String duration) {
		this.l_id = l_id;
		this.t_id = t_id;
		this.cost = cost;
		this.date = date;
		this.duration = duration;
	}
	public OrderList(){
		
	}
	
}
