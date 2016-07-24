package com.yongjian.english_tranning_talk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yongjian.english_tranning_talk.adapter.TeachersAdapter;
import com.yongjian.english_tranning_talk.bean.ApplicationData;
import com.yongjian.english_tranning_talk.bean.User;
import com.yongjian.english_tranning_talk.ItemDivider;
import com.yongjian.english_tranning_talk.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by YONGJIAN on 2016/6/10 0010.
 */
@ContentView(R.layout.teachers_activity)
public class TeachersActivity extends AppCompatActivity {

    private ArrayList<User> list = new ArrayList<User>(ApplicationData.getInstance().getTeacherlist().values());
    private TeachersAdapter adapter = null;
    public static RecyclerView.LayoutManager layoutManager = null;
    @ViewInject(R.id.find)
    private RecyclerView find;
    @ViewInject(R.id.empty)
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        if (list.size()==0){
            linearLayout.setVisibility(View.VISIBLE);
            find.setVisibility(View.GONE);
        }else{
            init();
        }
    }
    public void init(){
        find.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        find.setLayoutManager(layoutManager);
        adapter = new TeachersAdapter(list,this);
        find.setAdapter(adapter);
        find.addItemDecoration(new ItemDivider());
        find.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
    }
}
