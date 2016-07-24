package com.yongjian.english_tranning_talk.bean;

import android.content.Context;
import android.os.Handler;
import android.os.Message;


import com.yongjian.english_tranning_talk.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by YONGJIAN on 2016/6/9 0009.
 */
public class ApplicationData {
    private User mUser;
    private User l_user;
    private TranObject mReceivedMessage;         //服务器返回的传输对象
    private ArrayList<OrderList> mOrderList = new ArrayList<>();     //订单列表
    private ArrayList<Asses> mAssList = new ArrayList<>();            //评价列表

    private ArrayList<User> mTeacherList = new ArrayList<>();           //某一等级老师列表
    private Context mContext;
    private boolean mIsReceived;



    HashMap<Integer, User> teacherlist = new HashMap<>();


    private Handler mFindHandler;
    private Handler mOrderHandler;
    private Handler mAssesHandler;
    private Handler mWaitHandler;

    private static ApplicationData mInitData;
    private ApplicationData() {
    }
    /*
    得到ApplicationData的单例
     */
    public static ApplicationData getInstance() {
        if (mInitData == null) {
            mInitData = new ApplicationData();
        }
        return mInitData;
    }
    public void initData(Context context) {
        Logger.d("CCC","initdata");
        mContext =context ;
        mIsReceived = false;
        mAssList = null;
        mOrderList = null;
        mUser = null;
        mReceivedMessage = null;
    }
    public void start() {
        while (!(mIsReceived));
    }

    public TranObject getReceivedMessage() {
        return mReceivedMessage;
    }
    /*
    得到用户类型
     */
    public String getUserType(){
        return mUser.getType();
    }
    /*
    收到服务器返回的登陆结果
     */
    public void loginMessageArrived(Object tranObject) {
        mReceivedMessage = (TranObject) tranObject;
        Result loginResult = mReceivedMessage.getResult();
        Logger.d("CCC",loginResult.toString());
        if (loginResult ==Result.LOGIN_SUCCESS) {
            mUser = (User) mReceivedMessage.getObject();
            Logger.d("CCC", "输出用户类型");
            Logger.d("CCC", mUser.getType());
            mOrderList = mUser.getOrderlists();
            Logger.d("CCC",String.valueOf(mUser.getOrderlists().size()));
            if (mUser.getType().equals("teacher") && mUser.getAsseslist().size()!=0) {
                mAssList = mUser.getAsseslist();
                Logger.d("CCC",String.valueOf(mUser.getAsseslist().size()));
            }
        }else
                mUser =null;
            mIsReceived =true;
        }

    /*
    收到服务器返回的查询订单数据
     */
    public void queryOrderArrived(TranObject object){
        Logger.d("CCC","收到服务器返回的更新订单");
        mReceivedMessage = object;
        mOrderList.clear();
        mOrderList = (ArrayList<OrderList>) mReceivedMessage.getObject();
        if (mOrderHandler!=null){
            Logger.d("CCC","向订单发送handler");
            Message message = new Message();
            message.what = 1;
            mOrderHandler.sendMessage(message);
        }
    }
    /*
    收到服务器返回的查询评价数据
     */
    public void queryAssesArried(TranObject object){
        Logger.d("CCC","收到服务器返回的更新评价");
        mReceivedMessage = object;
        mAssList.clear();
        mAssList = (ArrayList<Asses>) mReceivedMessage.getObject();
        if (mAssesHandler!=null){
            Logger.d("CCC","向评价发送handler");
            Message message = new Message();
            message.what =1 ;
            mAssesHandler.sendMessage(message);
        }
    }


    /*
    收到服务器返回的某一等级教师们
     */
    public void searchTeachersArrived(TranObject object){
        mReceivedMessage = object;
        teacherlist = (HashMap<Integer, User>) mReceivedMessage.getObject();
        Logger.d("CCC","老师人数为"+teacherlist.size());
      //  changeToArrayList();
        if (mFindHandler!=null){
            Message message =new Message();
            message.what =1;
            mFindHandler.sendMessage(message);
            Logger.d("CCC","已经向主线程发送结果到达请求");
        }
    }
    /*
    将保存老师信息的hash表转换成adapter可用的数组链表
     */
    public ArrayList<User> changeToArrayList(){
        if (teacherlist.size()!=0){
            Iterator it=teacherlist.keySet().iterator();
            while (it.hasNext()){
                mTeacherList.add((User) it.next());
            }
        }
        return mTeacherList;
    }
    /*
    收到服务器的连接教师请求
     */
    public void handleRequesr(TranObject object){
        mReceivedMessage = object;
        setL_user((User) mReceivedMessage.getObject());
        if (mWaitHandler != null){
            Message message =new Message();
            message.what =40;
            mWaitHandler.sendMessage(message);
        }

    }
    public User getmUser() {
        return mUser;
    }
    public void setmUser(User mUser) {
        this.mUser = mUser;
    }
    public Handler getmOrderHandler() {
        return mOrderHandler;
    }
    public void setmOrderHandler(Handler mOrderHandler) {
        this.mOrderHandler = mOrderHandler;
    }
    public Handler getmAssesHandler() {
        return mAssesHandler;
    }
    public Handler getmWaitHandler() {
        return mWaitHandler;
    }
    public void setmWaitHandler(Handler mWaitHandler) {
        this.mWaitHandler = mWaitHandler;
    }
    public void setmAssesHandler(Handler mAssesHandler) {
        this.mAssesHandler = mAssesHandler;
    }
    public Handler getmFindHandler() {
        return mFindHandler;
    }
    public void setmFindHandler(Handler mFindHandler) {
        this.mFindHandler = mFindHandler;
    }
    public ArrayList<User> getmTeacherList() {
        return mTeacherList;
    }
    public void setmTeacherList(ArrayList<User> mTeacherList) {
        this.mTeacherList = mTeacherList;
    }
    public User getL_user() {
        return l_user;
    }
    public void setL_user(User l_user) {
        this.l_user = l_user;
    }
    public HashMap<Integer, User> getTeacherlist() {
        return teacherlist;
    }

    public void setTeacherlist(HashMap<Integer, User> teacherlist) {
        this.teacherlist = teacherlist;
    }
    public ArrayList<OrderList> getmOrderList() {
        return mOrderList;
    }

    public void setmOrderList(ArrayList<OrderList> mOrderList) {
        this.mOrderList = mOrderList;
    }

    public ArrayList<Asses> getmAssList() {
        return mAssList;
    }

    public void setmAssList(ArrayList<Asses> mAssList) {
        this.mAssList = mAssList;
    }

}
