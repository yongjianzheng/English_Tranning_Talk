package com.yongjian.english_tranning_talk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yongjian.english_tranning_talk.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by YONGJIAN on 2016/4/24 0024.
 */
@ContentView(R.layout.welcome_activity)
public class WelcomeActivity  extends AppCompatActivity {
    @ViewInject(R.id.login)
    Button login;
    @ViewInject(R.id.register)
    Button register;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);


    }
    @Event(value = {R.id.login},type = View.OnClickListener.class)
    private void login(View view) {
        Intent i = new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(i);
    }
    @Event(value = {R.id.register},type = View.OnClickListener.class)
    private void register(View view){
        Intent i = new Intent(WelcomeActivity.this,SelectActivity.class);
        startActivity(i);
    }
}

