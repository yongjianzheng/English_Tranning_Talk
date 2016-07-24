package com.yongjian.english_tranning_talk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.yongjian.english_tranning_talk.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by YONGJIAN on 2016/6/7 0007.
 */
@ContentView(R.layout.select_register)
public class SelectActivity extends Activity {
    @ViewInject(R.id.teacher)
    private RadioButton teacher;
    @ViewInject(R.id.learner)
    private RadioButton learner;
    @ViewInject(R.id.next)
    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
    @Event(value = {R.id.next},type = View.OnClickListener.class)
    private void next(View v){
        if (teacher.isChecked())
        {
            Intent i = new Intent(SelectActivity.this,TRegisterActivity.class);
            startActivity(i);
            this.finish();
        }
        else
        {
            Intent i = new Intent(SelectActivity.this,LRegisterActivity.class);
            startActivity(i);
            this.finish();
        }
    }


}
