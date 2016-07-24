package com.yongjian.english_tranning_talk.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

	/**
	 * 类名 ：user 说明 ：user 对象
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String phnum;
	private String sex;
	private String pwd;
	private byte[] photo;
	private String wallet;
	private int isOnline;
	private String type;

	private String hourypay;
	private String grade;
	private byte[] gradepho;
	
	
	private ArrayList<Asses> asseslist;
	private ArrayList<OrderList> orderlists;
	
	
	public int getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhnum() {
		return phnum;
	}
	public void setPhnum(String phnum) {
		this.phnum = phnum;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getWallet() {
		return wallet;
	}
	public void setWallet(String wallet) {
		this.wallet = wallet;
	}
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	public String getHourypay() {
		return hourypay;
	}
	public void setHourypay(String hourypay) {
		this.hourypay = hourypay;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public byte[] getGradepho() {
		return gradepho;
	}
	public void setGradepho(byte[] gradepho) {
		this.gradepho = gradepho;
	}
	
	public User(String name, String phnum, String sex, String pwd, byte[] photo, String wallet, int isOnline,
			String type) {
		this.name = name;
		this.phnum = phnum;
		this.sex = sex;
		this.pwd = pwd;
		this.photo = photo;
		this.wallet = wallet;
		this.isOnline = isOnline;
		this.type = type;
	}
	public User(String name, String phnum, String sex, String pwd, byte[] photo, String wallet, int isOnline,
			String hourypay, String grade, byte[] gradepho,String type) {
		this.name = name;
		this.phnum = phnum;
		this.sex = sex;
		this.pwd = pwd;
		this.photo = photo;
		this.wallet = wallet;
		this.isOnline = isOnline;
		this.hourypay = hourypay;
		this.grade = grade;
		this.gradepho = gradepho;
		this.type = type;
	}
	public User(){
		
	}
	public ArrayList<Asses> getAsseslist() {
		return asseslist;
	}
	public void setAsseslist(ArrayList<Asses> asseslist) {
		this.asseslist = asseslist;
	}
	public ArrayList<OrderList> getOrderlists() {
		return orderlists;
	}
	public void setOrderlists(ArrayList<OrderList> orderlists) {
		this.orderlists = orderlists;
	}
}
