package com.yongjian.english_tranning_talk.util;

import com.yongjian.english_tranning_talk.bean.Asses;
import com.yongjian.english_tranning_talk.bean.TranObject;
import com.yongjian.english_tranning_talk.bean.User;
import com.yongjian.english_tranning_talk.bean.OrderList;
import com.yongjian.english_tranning_talk.bean.TranObjectType;
import com.yongjian.english_tranning_talk.network.NetService;

import java.io.IOException;

/**
 * Created by YONGJIAN on 2016/6/8 0008.
 */
public class RequestUtil {
    private static NetService mNetService = NetService.getInstance();

    public static void register(User user) throws IOException {
        TranObject t = new TranObject(user, TranObjectType.REGISTER);
        mNetService.send(t);
    }
    public static void loginVerify(User user) throws IOException {
        TranObject t = new TranObject(user, TranObjectType.LOGIN);
        mNetService.send(t);
    }
    public static void queryOrder(User user) throws  IOException{
        TranObject t =new TranObject(user,TranObjectType.QUERY_ORDER);
        mNetService.send(t);
    }
    public static void queryAss(User user) throws IOException{
        TranObject t = new TranObject(user,TranObjectType.QUERY_ASS);
        mNetService.send(t);
    }
    public static void searchTeachers1(User user) throws IOException{
        Logger.d("CCC","向服务器发起查询老师请求");
        Logger.d("CCC","查询老师等级为"+user.getGrade());
        TranObject t = new TranObject(user,TranObjectType.SEARCH_TEACHER1);
        mNetService.send(t);
    }
    public static void searchTeachers2(User user) throws IOException{
        Logger.d("CCC","向服务器发起查询老师请求");
        Logger.d("CCC","查询老师等级为"+user.getGrade());
        TranObject t = new TranObject(user,TranObjectType.SEARCH_TEACHER2);
        mNetService.send(t);
    }
    public static void searchTeachers3(User user) throws IOException{
        Logger.d("CCC","向服务器发起查询老师请求");
        Logger.d("CCC","查询老师等级为"+user.getGrade());
        TranObject t = new TranObject(user,TranObjectType.SEARCH_TEACHER3);
        mNetService.send(t);
    }
    public static void searchTeachers4(User user) throws IOException{
        Logger.d("CCC","向服务器发起查询老师请求");
        Logger.d("CCC","查询老师等级为"+user.getGrade());
        TranObject t = new TranObject(user,TranObjectType.SEARCH_TEACHER4);
        mNetService.send(t);
    }
    public static void sendRequest(User user,int t_id)throws IOException{
        TranObject t = new TranObject(user,TranObjectType.SEND_REQUEST);
        Logger.d("CCC","发送连接教师请求");
        t.setReceiveId(t_id);
        mNetService.send(t);
    }
    public static void sendLogin(User user)throws IOException{
        TranObject t =new TranObject(user,TranObjectType.SEND_LOGIN);
        mNetService.send(t);
    }
    public static void sendLoginOut(User user)throws IOException{
        TranObject t =new TranObject(user,TranObjectType.SEND_LOGINOUT);
        mNetService.send(t);
    }
    public static void sendOrderProduce(OrderList orderList)throws IOException{
        TranObject t =new TranObject(orderList,TranObjectType.PRODECE_ORDER);
        mNetService.send(t);
    }
    public static void sendAssProduce(Asses asses)throws IOException{
        Logger.d("CCC",asses.getContent());
        Logger.d("CCC",asses.getT_name());
        Logger.d("CCC",asses.getAssgrade());
        TranObject t = new TranObject(asses,TranObjectType.PRODUCE_ASS);
        mNetService.send(t);
    }
}
