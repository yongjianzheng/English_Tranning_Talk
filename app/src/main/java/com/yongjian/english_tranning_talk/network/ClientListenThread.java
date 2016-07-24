package com.yongjian.english_tranning_talk.network;

import android.content.Context;

import com.yongjian.english_tranning_talk.activity.TRegisterActivity;
import com.yongjian.english_tranning_talk.bean.ApplicationData;
import com.yongjian.english_tranning_talk.bean.TranObject;
import com.yongjian.english_tranning_talk.bean.User;
import com.yongjian.english_tranning_talk.activity.LRegisterActivity;
import com.yongjian.english_tranning_talk.util.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by YONGJIAN on 2016/6/7 0007.
 */
public class ClientListenThread extends  Thread{
    private Socket mSocket = null;
    private Context mContext = null;
    private ObjectInputStream ois;
    private boolean IsStart = false;

    public ClientListenThread(Context mContext, Socket mSocket) {
        this.mContext = mContext;
        this.mSocket = mSocket;
        try {
            ois = new ObjectInputStream(mSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSocket(Socket socket){
        this.mSocket = socket;
    }
    public void run(){
        IsStart =true;
        while(true){
            TranObject mReceived = null;
            try {
                mReceived = (TranObject) ois.readObject();
                Logger.d("CCC","接受成功");
                switch (mReceived.getTranType()){
                    case LOGIN:
                        ApplicationData.getInstance().loginMessageArrived(mReceived);
                        Logger.d("CCC","收到服务器返回的登陆信息");
                        break;
                    case REGISTER:
                        Logger.d("CCC","收到服务器返回的注册信息");
                        User user = (User) mReceived.getObject();
                        Logger.d("CCC",user.getPhnum());
                        Logger.d("CCC",mReceived.getTranType().toString());
                        Logger.d("CCC",String.valueOf(user.getId()));
                        if (user.getType().equals("learner"))
                        {
                            LRegisterActivity.setRegisterInfo(mReceived,true);
                            Logger.d("CCC","注册成功");
                        }
                        else {
                            TRegisterActivity.setRegisterInfo(mReceived,true);
                            Logger.d("CCC","注册成功");
                        }
                        break;
                    case QUERY_ORDER:
                        ApplicationData.getInstance().queryOrderArrived(mReceived);
                        break;
                    case QUERY_ASS:
                        ApplicationData.getInstance().queryAssesArried(mReceived);
                        break;
                    case SEARCH_TEACHER1:              //返回四级等级的在线老师
                        ApplicationData.getInstance().searchTeachersArrived(mReceived);
                        Logger.d("CCC","收到服务器返回的查询老师请求");
                        break;
                    case SEARCH_TEACHER2:              //返回六级等级的在线老师
                        ApplicationData.getInstance().searchTeachersArrived(mReceived);
                        Logger.d("CCC","收到服务器返回的查询老师请求");
                        break;
                    case SEARCH_TEACHER3:              //返回教师等级的在线老师
                        ApplicationData.getInstance().searchTeachersArrived(mReceived);
                        Logger.d("CCC","收到服务器返回的查询老师请求");
                        break;
                    case SEARCH_TEACHER4:              //返回教授等级的在线老师
                        ApplicationData.getInstance().searchTeachersArrived(mReceived);
                        Logger.d("CCC","收到服务器返回的查询老师请求");
                        break;
                    case SEND_REQUEST:
                        ApplicationData.getInstance().handleRequesr(mReceived);
                        Logger.d("CCC","收到服务器返回的连接教师请求");
                        break;
                    default:
                        break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void close(){
        IsStart = false;
    }

}
