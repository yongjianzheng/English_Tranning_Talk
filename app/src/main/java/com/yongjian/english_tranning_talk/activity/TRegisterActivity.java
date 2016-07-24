package com.yongjian.english_tranning_talk.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.yongjian.english_tranning_talk.bean.Result;
import com.yongjian.english_tranning_talk.bean.TranObject;
import com.yongjian.english_tranning_talk.bean.User;
import com.yongjian.english_tranning_talk.dialog.ProgressDialogHelper;
import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.dialog.AlertDialogHelper;
import com.yongjian.english_tranning_talk.network.NetService;
import com.yongjian.english_tranning_talk.util.Logger;
import com.yongjian.english_tranning_talk.util.RequestUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by YONGJIAN on 2016/4/25 0025.
 */
@ContentView(R.layout.t_register_activity)
public class TRegisterActivity extends AppCompatActivity {
    private static NetService mNetService = NetService.getInstance();
    private static boolean mIsReceived = false;
    private static TranObject mReceivedInfo = null;
    private ArrayList<RadioButton> list = new ArrayList<>();
    @ViewInject(R.id.phonenum)
    private EditText phone;
    @ViewInject(R.id.registeruser)
    private EditText username;
    @ViewInject(R.id.regietpwd)
    private EditText pwd;
    @ViewInject(R.id.man)
    private RadioButton man;
    @ViewInject(R.id.woman)
    private RadioButton woman;
    @ViewInject(R.id.fourgrade)
    private RadioButton four;
    @ViewInject(R.id.sixgrade)
    private RadioButton six;
    @ViewInject(R.id.teachergrade)
    private RadioButton te;
    @ViewInject(R.id.professorgrade)
    private RadioButton pro;
    @ViewInject(R.id.hourpay)
    private EditText hourpay;
    @ViewInject(R.id.realregister)
    private Button register;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        list.add(four);
        list.add(six);
        list.add(te);
        list.add(pro);
    }
    @Event(value = {R.id.realregister},type = View.OnClickListener.class)
    private void register(View v){
        if (phone.getText().toString().length()!=11){
            AlertDialogHelper.showAlertDialog(TRegisterActivity.this,"善意的提醒","请填写正确的手机号码");
        }else if (username.getText().length()<=0){
            AlertDialogHelper.showAlertDialog(TRegisterActivity.this,"善意的提醒","用户名不能为空");
        }else if (pwd.getText().toString().length()<6){
            AlertDialogHelper.showAlertDialog(TRegisterActivity.this,"善意的提醒","密码必须六位及以上");
        }else if(hourpay.getText().toString().length()<=0){
            AlertDialogHelper.showAlertDialog(TRegisterActivity.this,"善意的提醒","请填写您期望的时薪");
        }else {
            ProgressDialogHelper.showProgressDialog(TRegisterActivity.this,"请稍候，正在提交......");
            User user =new User();
            user.setType("teacher");
            user.setPhnum(phone.getText().toString());
            user.setName(username.getText().toString());
            user.setPwd(pwd.getText().toString());
            user.setHourypay(hourpay.getText().toString());
            if (man.isChecked())
                user.setSex("男");
            else
                user.setSex("女");
            for (RadioButton button:list){
                if(button.isChecked()) {
                    user.setGrade(button.getText().toString());
                    break;
                }
            }
            Logger.d("CCC",user.getPhnum());
            tryRegister(user);
        }
    }
    public void tryRegister(User user){
        new AsyncTask<User, Void, Integer>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected Integer doInBackground(User... params) {
                mIsReceived =false;
                mNetService.setupConnection();
                if (!mNetService.isConnected())
                    return  0;
                else{
                    try {
                        RequestUtil.register(params[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (!mIsReceived){

                    }
                    mNetService.closeConnection();
                    if (mReceivedInfo.getResult()== Result.REGISTER_SUCCESS)
                        return 1;
                    else
                        return 2;
                }
            }
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                ProgressDialogHelper.closeProgressDialog();
                if (integer ==0)
                    AlertDialogHelper.showAlertDialog(TRegisterActivity.this,"很抱歉","服务器异常");
                else {
                    if (integer == 1) {
                       // AlertDialogHelper.showAlertDialog(TRegisterActivity.this, "恭喜！", "注册成功!");
                        TRegisterActivity.this.finish();
                    }else if (integer == 2 )
                        AlertDialogHelper.showAlertDialog(TRegisterActivity.this,"很遗憾","注册失败!");
                }
            }
        }.execute(user);
    }
    public static void setRegisterInfo(TranObject object, boolean isReceived) {

        mReceivedInfo = object;
        mIsReceived = true;
    }
}
