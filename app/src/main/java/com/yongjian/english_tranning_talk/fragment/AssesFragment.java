package com.yongjian.english_tranning_talk.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yongjian.english_tranning_talk.adapter.AssesAdapter;
import com.yongjian.english_tranning_talk.ItemDivider;
import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.bean.ApplicationData;
import com.yongjian.english_tranning_talk.bean.Asses;
import com.yongjian.english_tranning_talk.util.Logger;
import com.yongjian.english_tranning_talk.util.RequestUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by YONGJIAN on 2016/6/9 0009.
 */
public class AssesFragment extends Fragment {

    private ArrayList<Asses> list = ApplicationData.getInstance().getmAssList();
    public static RecyclerView.LayoutManager layoutManager = null;
    public static AssesAdapter adapter = null;
    private Handler handler;
    @ViewInject(R.id.asslist)
    private RecyclerView recyclerView;
    @ViewInject(R.id.empty)
    private LinearLayout linearLayout;
    @ViewInject(R.id.swipe_container)
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.asses_fragment,container,false);
        x.view().inject(this,v);
        if (list.size()==0){
            Logger.d("CCC","list是空的");
            empty();
        }else{
            Logger.d("CCC","执行到这了");
            init();
        }
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Logger.d("CCC","handler收到更新结果信息");
                        swipeRefreshLayout.setRefreshing(false);
                        list =ApplicationData.getInstance().getmAssList();
                        if (list.size()==0)
                            empty();
                        else
                            init();
                        break;
                    default:
                        break;
                }
            }
        };
        ApplicationData.getInstance().setmAssesHandler(handler);
        return v;
    }
    public void empty(){
        swipeRefreshLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }
    public  void init(){
        recyclerView.setHasFixedSize(true);
        adapter = new AssesAdapter(list,getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDivider());
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

    }

    @Event(value = {R.id.swipe_container},type =SwipeRefreshLayout.OnRefreshListener.class)
    private void refresh(){
        swipeRefreshLayout.setRefreshing(true);
        try {
            RequestUtil.queryAss(ApplicationData.getInstance().getmUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
