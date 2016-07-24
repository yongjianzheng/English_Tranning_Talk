package com.yongjian.english_tranning_talk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.bean.Asses;
import com.yongjian.english_tranning_talk.dialog.AlertDialogHelper;
import com.yongjian.english_tranning_talk.util.RequestUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

/**
 * Created by YONGJIAN on 2016/6/11 0011.
 */
@ContentView(R.layout.commit_ass__activity)
public class CommitAssesActivity extends AppCompatActivity{

    @ViewInject(R.id.group)
    private RadioGroup group;
    @ViewInject(R.id.good)
    private RadioButton good;
    @ViewInject(R.id.medium)
    private RadioButton medium;
    @ViewInject(R.id.bad)
    private RadioButton bad;
    @ViewInject(R.id.content)
    private EditText content;
    @ViewInject(R.id.commit)
    private Button commit;
    @ViewInject(R.id.cancel)
    private Button cancel;
    private Asses asses = new Asses();
    private int id;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        id = getIntent().getIntExtra("id",0);
        name = getIntent().getStringExtra("name");
        asses.setAssgrade("好评");
        asses.setT_id(id);
        asses.setT_name(name);

    }
    @Event(value = {R.id.group},type = RadioGroup.OnCheckedChangeListener.class)
    private void onChecked(RadioGroup group,int checkId){
        switch (group.getCheckedRadioButtonId()) {
            case R.id.good:
                asses.setAssgrade("好评");
                break;
            case R.id.medium:
                asses.setAssgrade("中评");
                break;
            case R.id.bad:
                asses.setAssgrade("差评");
                break;
        }
        }
    @Event(value = {R.id.commit})
    private void commit(View v){
        if (TextUtils.isEmpty(content.getText().toString()))
            AlertDialogHelper.showAlertDialog(CommitAssesActivity.this,"善意的提醒","请输入评价内容");
        else {
            asses.setContent(content.getText().toString());
            try {
                RequestUtil.sendAssProduce(asses);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(CommitAssesActivity.this,"您的评价已经提交",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(CommitAssesActivity.this,LDrawerActivity.class);
            startActivity(i);
            CommitAssesActivity.this.finish();
        }
        }
    @Event(value = {R.id.cancel})
    private void cancel(View v){
        Intent i = new Intent(CommitAssesActivity.this,LDrawerActivity.class);
        startActivity(i);
        CommitAssesActivity.this.finish();
    }

 }
