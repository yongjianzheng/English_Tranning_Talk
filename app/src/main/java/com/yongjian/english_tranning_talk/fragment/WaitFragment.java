package com.yongjian.english_tranning_talk.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yongjian.english_tranning_talk.bean.ApplicationData;
import com.yongjian.english_tranning_talk.bean.User;
import com.yongjian.english_tranning_talk.util.RequestUtil;
import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.bean.OrderList;
import com.yongjian.english_tranning_talk.dialog.AlertDialogHelper;
import com.yongjian.english_tranning_talk.util.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

/**
 * Created by YONGJIAN on 2016/6/9 0009.
 */
@ContentView(R.layout.wait_fragment)
public class WaitFragment extends Fragment {
    SimpleDateFormat formatter=new  SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    boolean show = false;
    long duration;
    Date time1,time2;
    String date;
    private int on =0;
    private int flag = 0;
    private int i = 30;
    private Timer timer = new Timer();
    private Handler handler;
    private User l_user;
    private User mUser = ApplicationData.getInstance().getmUser();
    private String phone;
    @ViewInject(R.id.btn)
    private Button button;
    @ViewInject(R.id.accept)
    private Button accept;
    @ViewInject(R.id.reject)
    private Button reject;
    @ViewInject(R.id.linearlayout)
    private LinearLayout linearLayout;
    TimerTask task;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (preferences.getBoolean("status",false)){
            button.setBackgroundResource(R.drawable.t_bluebutton);
            button.setText(R.string.stop);
            flag =1;
        }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 40) {
                   timer.schedule(task,1000,1000);
                    show();
                    l_user = ApplicationData.getInstance().getL_user();
                    phone = l_user.getPhnum();
                    linearLayout.setVisibility(View.VISIBLE);
                }
                }
            };
        ApplicationData.getInstance().setmWaitHandler(handler);
        task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        i--;
                        if (i==0){
                            timer.cancel();
                            linearLayout.setVisibility(View.GONE);
                        }
                    }
                });
            }
        };
        return x.view().inject(this, inflater, container);
    }

    @Event(value = {R.id.btn})
    private void onclick(View v) {
        if (flag == 0) {
            try {
                RequestUtil.sendLogin(ApplicationData.getInstance().getmUser());
            } catch (IOException e) {
                e.printStackTrace();
            }
            button.setBackgroundResource(R.drawable.t_bluebutton);
            button.setText(R.string.stop);
            Toast.makeText(getActivity(), "上线成功", Toast.LENGTH_SHORT).show();
            flag = 1;

        } else {
            try {
                RequestUtil.sendLoginOut(ApplicationData.getInstance().getmUser());
            } catch (IOException e) {
                e.printStackTrace();
            }
            button.setBackgroundResource(R.drawable.t_redbutton);
            button.setText(R.string.start);
            Toast.makeText(getActivity(), "您已下线", Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.GONE);
            flag = 0;
        }
    }

    @Event(value = {R.id.accept})
    private void accept(View v) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
        linearLayout.setVisibility(View.GONE);
        TelephonyManager manager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(new MobilePhoneStatusListens(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Event(value = {R.id.reject})
    private void reject(View v) {
        linearLayout.setVisibility(View.GONE);

    }

    public void show(){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message message=new Message();
                    message.what = i;
                    i--;
                    handler.sendMessage(message);
                }
            },1000,1000);


    }
    private class MobilePhoneStatusListens extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:   //挂机状态
                    if (on == 1) {
                        time2 = new Date(System.currentTimeMillis());
                        duration = (time1.getTime()-time2.getTime())/(1000*60*60);
                        int cost =Integer.parseInt(String.valueOf(duration))* Integer.parseInt(mUser.getHourypay());
                        OrderList orderList = new OrderList();
                        orderList.setDuration(String.valueOf(duration));
                        orderList.setDate(date);
                        orderList.setT_id(mUser.getId());
                        orderList.setL_id(l_user.getId());
                        orderList.setCost(String.valueOf(cost));
                        Logger.d("CCC","duration"+String.valueOf(duration));
                        Logger.d("CCC","cost"+String.valueOf(cost));
                        try {
                            RequestUtil.sendOrderProduce(orderList);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        on = 0;
                        AlertDialogHelper.showAlertDialog(getActivity(),"恭喜您","您的订单已经提交");
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK: //通话中
                    on = 1;
                    time1 =new Date(System.currentTimeMillis());
                    date = formatter.format(time1);
                    Logger.d("CCC",date);
                    break;
                default:
                    break;
            }
        }
    }

}
