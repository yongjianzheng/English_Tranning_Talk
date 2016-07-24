package com.yongjian.english_tranning_talk.bean;

import java.io.Serializable;

/**
 * 传输对象
 */
public class TranObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Object object;
	private Result result;
	private TranObjectType tranType;
	private int receiveId;
	private int sendId;
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public TranObjectType getTranType() {
		return tranType;
	}
	public void setTranType(TranObjectType tranType) {
		this.tranType = tranType;
	}
	public int getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}
	public int getSendId() {
		return sendId;
	}
	public void setSendId(int sendId) {
		this.sendId = sendId;
	}
	public TranObject(Object object,TranObjectType tranType) {
		
		this.object = object;
		this.tranType = tranType;
	}
	public TranObject () {
		
	}
	
}
