package com.yongjian.english_tranning_talk.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.yongjian.english_tranning_talk.bean.Result;
import com.yongjian.english_tranning_talk.bean.TranObject;
import com.yongjian.english_tranning_talk.bean.User;
import com.yongjian.english_tranning_talk.dialog.ProgressDialogHelper;
import com.yongjian.english_tranning_talk.util.RequestUtil;
import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.dialog.AlertDialogHelper;
import com.yongjian.english_tranning_talk.network.NetService;
import com.yongjian.english_tranning_talk.util.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

/**
 * Created by YONGJIAN on 2016/6/8 0008.
 */
@ContentView(R.layout.l_register)
public class LRegisterActivity extends Activity {

    private static NetService mNetService = NetService.getInstance();
    private static boolean mIsReceived = false;
    private static TranObject mReceivedInfo = null;
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
    @ViewInject(R.id.realregister)
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
    @Event(value = {R.id.realregister},type = View.OnClickListener.class)
    private void register(View v){
        if (phone.getText().toString().length()!=11){
            AlertDialogHelper.showAlertDialog(LRegisterActivity.this,"善意的提醒","请填写正确的手机号码");
        }else if (username.getText().length()<=0){
            AlertDialogHelper.showAlertDialog(LRegisterActivity.this,"善意的提醒","用户名不能为空");
        }else if (pwd.getText().toString().length()<6){
            AlertDialogHelper.showAlertDialog(LRegisterActivity.this,"善意的提醒","密码必须六位及以上");
        }else {
            ProgressDialogHelper.showProgressDialog(LRegisterActivity.this,"请稍候，正在提交......");
            User user =new User();
            user.setType("learner");
            user.setPhnum(phone.getText().toString());
            user.setName(username.getText().toString());
            user.setPwd(pwd.getText().toString());
            if (man.isChecked())
                user.setSex("男");
            else
                user.setType("女");
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
                    Logger.d("CCC","注册时连接数据库成功");
                    try {
                        RequestUtil.register(params[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    while (!mIsReceived){

                    }
                    mNetService.closeConnection();
                    Logger.d("CCC",mReceivedInfo.getResult().toString());
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
                    AlertDialogHelper.showAlertDialog(LRegisterActivity.this,"很抱歉","服务器异常");
                else {
                    if (integer == 1) {
                       // AlertDialogHelper.showAlertDialog(LRegisterActivity.this, "恭喜！", "注册成功!");
                        LRegisterActivity.this.finish();
                    }else if (integer == 2 )
                        AlertDialogHelper.showAlertDialog(LRegisterActivity.this,"很遗憾","注册失败!");
                }
            }
        }.execute(user);
    }
    public static void setRegisterInfo(TranObject object, boolean isReceived) {

        mReceivedInfo = object;
        mIsReceived = true;
    }
}
