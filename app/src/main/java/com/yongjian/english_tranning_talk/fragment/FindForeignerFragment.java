package com.yongjian.english_tranning_talk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yongjian.english_tranning_talk.activity.CommitAssesActivity;
import com.yongjian.english_tranning_talk.bean.ApplicationData;
import com.yongjian.english_tranning_talk.bean.User;
import com.yongjian.english_tranning_talk.dialog.ProgressDialogHelper;
import com.yongjian.english_tranning_talk.util.RequestUtil;
import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.activity.TeachersActivity;
import com.yongjian.english_tranning_talk.util.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.IOException;

/**
 * Created by YONGJIAN on 2016/4/24 0024.
 */
@ContentView(R.layout.find_fragment)
public class FindForeignerFragment extends Fragment {
    User user = new User();
    private Handler handler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        x.view().inject(this,inflater,container);
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        ProgressDialogHelper.closeProgressDialog();
                        Logger.d("CCC","主线程收到服务器的结果请求，准备跳转");
                        Intent i = new Intent(getActivity(), TeachersActivity.class);
                        startActivity(i);
                        break;
                    default:
                        break;
                }
            }
        };
        ApplicationData.getInstance().setmFindHandler(handler);
        return x.view().inject(this,inflater,container);
    }



    @Event(value = {R.id.four},type = View.OnClickListener.class)
    private void fourclick(View v){
        user.setGrade("口语四级");
        try {
            RequestUtil.searchTeachers1(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProgressDialogHelper.showProgressDialog(getActivity(),"正在查询，请稍候.....");
    }
    @Event(value = {R.id.six},type = View.OnClickListener.class)
    private void sixclick(View v){
        user.setGrade("口语六级");
        try {
            RequestUtil.searchTeachers2(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProgressDialogHelper.showProgressDialog(getActivity(),"正在查询，请稍候.....");
    }
    @Event(value = {R.id.teach},type = View.OnClickListener.class)
    private void teachonclick(View v){
        user.setGrade("教师水平");
        try {
            RequestUtil.searchTeachers3(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProgressDialogHelper.showProgressDialog(getActivity(),"正在查询，请稍候.....");
    }
    @Event(value = {R.id.profess},type = View.OnClickListener.class)
    private void proonclick(View v){
        user.setGrade("教授水平");
        try {
            RequestUtil.searchTeachers4(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProgressDialogHelper.showProgressDialog(getActivity(),"正在查询，请稍候.....");
    }
}
