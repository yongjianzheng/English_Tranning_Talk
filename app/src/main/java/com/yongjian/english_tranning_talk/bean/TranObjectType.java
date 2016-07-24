package com.yongjian.english_tranning_talk.bean;

/*
 * 传输对象类型
 */


public enum TranObjectType {
	REGISTER, // 注册
	REGISTER_PHONE,//注册的手机号验证
	LOGIN, // 用户登录
	LOGOUT, // 用户退出登录
	UNCONNECTED, // 无法连接
	FILE, // 传输文件
	REFRESH, // 刷新
	SEARCH_TEACHER1,//找老师们
	SEARCH_TEACHER2,
	SEARCH_TEACHER3,
	SEARCH_TEACHER4,
	SEARCH_ASSESS, //找特定老师的评价
	SEND_REQUEST,//  发送连接请求
	SEND_LOGIN,  //教师发送开始服务请求请求
	SEND_LOGINOUT,// 教师发送停止服务请求
	PRODECE_ORDER,  //教师确定订单
	PRODUCE_ASS,// 学生确认评价
	QUERY_ORDER,//查询订单
	QUERY_ASS, //查询评价
	
	
	FRIEND_REQUEST,//好友申请        暂时不实现
	FRIENDLOGIN, //  好友上线        暂时不实现
	FRIENDLOGOUT, // 好友下线        暂时不实现
	MESSAGE, //      用户发送消息              暂时不实现
}
