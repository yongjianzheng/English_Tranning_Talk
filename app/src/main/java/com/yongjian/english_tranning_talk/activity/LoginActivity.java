package com.yongjian.english_tranning_talk.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.yongjian.english_tranning_talk.bean.ApplicationData;
import com.yongjian.english_tranning_talk.bean.Result;
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

/**
 * Created by YONGJIAN on 2016/4/24 0024.
 */
@ContentView(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity {
    User user = new User();
    private String TAG = "LoginActivity";
    private NetService mNetService = NetService.getInstance();
    @ViewInject(R.id.phone)
    private EditText phone;
    @ViewInject(R.id.password)
    private EditText pwd;
    @ViewInject(R.id.remember)
    private CheckBox remember;
    @ViewInject(R.id.login)
    private Button login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        isSetUser();
    }
    @Event(value = {R.id.login},type = View.OnClickListener.class)
    private void login(View v){
        if (phone.getText().length()!=11){
            AlertDialogHelper.showAlertDialog(LoginActivity.this,"善意的提醒","请输入正确的手机号码");
        }else if (pwd.getText().length()<=0){
            AlertDialogHelper.showAlertDialog(LoginActivity.this,"善意的提醒","请输入密码");
        }else {
            ProgressDialogHelper.showProgressDialog(LoginActivity.this,"正在登录，请稍候..........");

            user.setPhnum(phone.getText().toString());
            user.setPwd(pwd.getText().toString());
            tryLogin(user);
        }
    }
    public void tryLogin(User user){
        new AsyncTask<User,Void,Integer>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected Integer doInBackground(User... params) {
                int result =0;
                try {

                    mNetService.closeConnection();
                    mNetService.onInit(LoginActivity.this);
                    mNetService.setupConnection();
                    if (!mNetService.isConnected())
                        return 0;
                    Logger.d(TAG,"服务器连接成功");
                    User user = params[0];
                    Logger.d(TAG,user.getPhnum());
                    RequestUtil.loginVerify(user);
                    ApplicationData data = ApplicationData.getInstance();
                    data.initData(LoginActivity.this);
                    data.start();
                    ProgressDialogHelper.closeProgressDialog();
                    if (data.getReceivedMessage().getResult() == Result.LOGIN_SUCCESS){
                        Logger.d("CCC","收到登陆成功的结果");
                        Logger.d("CCC", ApplicationData.getInstance().getmUser().getType());
                        if (data.getUserType().equals("teacher")){
                            result = 1;
                        }
                        else{
                            result = 2;
                        }
                    }
                    else if(data.getReceivedMessage().getResult() == Result.LOGIN_FAILED){
                        Logger.d("CCC","收到登陆失败的结果");
                        result= 3;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                Logger.d("CCC",String.valueOf(result));
                return result;
            }
            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                Logger.d("CCC","执行到asyn的post");
                Logger.d("CCC",String.valueOf(result));
                ProgressDialogHelper.closeProgressDialog();
                if (result == 0) {
                    AlertDialogHelper.showAlertDialog(LoginActivity.this, "很抱歉", "服务器连接异常");
                }else if (result == 3) {
                    AlertDialogHelper.showAlertDialog(LoginActivity.this, "很遗憾", "登录失败,请确定是否输入有误！");
                }else if (result == 1) {
                    if (remember.isChecked()){
                        save();
                    }
                    Intent i = new Intent(LoginActivity.this,TDrawerActivity.class);
                    startActivity(i);
                    LoginActivity.this.finish();
                }else {
                    if (remember.isChecked()){
                        save();
                    }
                    Intent i = new Intent(LoginActivity.this,LDrawerActivity.class);
                    startActivity(i);
                    LoginActivity.this.finish();
                }

            }
        }.execute(user);
    }
    private void save(){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putBoolean("remember",true);
        editor.putString("phone",user.getPhnum());
        Logger.d("CCC","用户名是"+user.getPhnum());
        editor.putString("pwd",user.getPwd());
        Logger.d("CCC","密码是"+user.getPwd());
        editor.commit();
    }
    private void isSetUser() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("remember",false)){
            String pho = prefs.getString("phone",null);
            String password = prefs.getString("pwd",null);
            phone.setText(pho);
            pwd.setText(password);
        }
    }

}
