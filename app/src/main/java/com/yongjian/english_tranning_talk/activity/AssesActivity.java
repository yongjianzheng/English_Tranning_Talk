package com.yongjian.english_tranning_talk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yongjian.english_tranning_talk.adapter.AssesAdapter;
import com.yongjian.english_tranning_talk.bean.ApplicationData;
import com.yongjian.english_tranning_talk.bean.User;
import com.yongjian.english_tranning_talk.ItemDivider;
import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.bean.Asses;
import com.yongjian.english_tranning_talk.dialog.ProgressDialogHelper;
import com.yongjian.english_tranning_talk.util.Logger;
import com.yongjian.english_tranning_talk.util.RequestUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by YONGJIAN on 2016/6/11 0011.
 */
@ContentView(R.layout.asses_activity)
public class AssesActivity extends AppCompatActivity {

    private static User teacherUser;
    public static RecyclerView.LayoutManager layoutManager = null;
    public static AssesAdapter adapter = null;
    private Timer timer;
    private Handler handler;
    private int waitime =15;
    private int flag =0;
    ArrayList<Asses> list;
    @ViewInject(R.id.asslist)
    private RecyclerView recyclerView;
    @ViewInject(R.id.empty)
    private LinearLayout linearLayout;
    @ViewInject(R.id.cannot)
    private TextView cannot;
    @ViewInject(R.id.connect)
    private Button connect;

    public static void startAssactivity(Context context, User user){
        Intent intent = new Intent(context, AssesActivity.class);
        teacherUser = user;
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        list = teacherUser.getAsseslist();
        if (list.size() == 0) {
            empty();
        } else {
            init();
        }
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what ==1){
                    Logger.d("CCC","收到消息准备关闭对话框");
                    ProgressDialogHelper.closeProgressDialog();
                    waitime = 0;
                }
            }
        };
    }
    public void empty(){
        recyclerView.setVisibility(View.GONE);
        cannot.setText(R.string.cannot_get_ass);
        linearLayout.setVisibility(View.VISIBLE);
    }
    public void init(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AssesAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDivider());
        recyclerView.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
    }
    @Event(value = {R.id.connect})
    private void connect(View v)  {
        ProgressDialogHelper.showProgressDialog(AssesActivity.this,"正在连线，请稍候.....(请耐心等待15秒，15秒内无反应请您重新连线)");
        try {
            Logger.d("CCC","点击后第一步");
            RequestUtil.sendRequest(ApplicationData.getInstance().getmUser(),teacherUser.getId());
            Logger.d("CCC","点击后第二步");
        } catch (IOException e) {
            e.printStackTrace();
        }
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(new MobilePhoneStatusListens(), PhoneStateListener.LISTEN_CALL_STATE);
        Logger.d("CCC","点击后第三步");
        new Thread(new Runnable() {
            @Override
            public void run() {
                waitime = 15;
                while (waitime>0){
                    try {
                        Thread.sleep(1000);
                        Logger.d("CCC","休眠一秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waitime--;
                }
                Message m1 = new Message();
                m1.what =1;
                handler.sendMessage(m1);
            }
        }).start();
        Logger.d("CCC","点击后第四步");


    }
 private class MobilePhoneStatusListens extends PhoneStateListener {

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:   //挂机状态
                if (flag == 1){
                    Intent i = new Intent(AssesActivity.this,CommitAssesActivity.class);
                    i.putExtra("id",teacherUser.getId());
                    i.putExtra("name",teacherUser.getName());
                    startActivity(i);
                }
                break;
            case TelephonyManager.CALL_STATE_RINGING: //来电
                Logger.d("CCC","有电话");
                Message message = new Message();
                message.what=1;
                handler.sendMessage(message);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK: //通话中
                flag = 1;

                break;
            default:
                break;
        }
    }

}
}
