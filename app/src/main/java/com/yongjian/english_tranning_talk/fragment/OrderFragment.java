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

import com.yongjian.english_tranning_talk.ItemDivider;
import com.yongjian.english_tranning_talk.adapter.OrderAdapter;
import com.yongjian.english_tranning_talk.bean.ApplicationData;
import com.yongjian.english_tranning_talk.bean.OrderList;
import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.util.Logger;
import com.yongjian.english_tranning_talk.util.RequestUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;

import static android.support.v4.widget.SwipeRefreshLayout.*;

/**
 * Created by YONGJIAN on 2016/4/24 0024.
 */
public class OrderFragment extends Fragment {

    private ArrayList<OrderList> list = ApplicationData.getInstance().getmOrderList();
    public static RecyclerView.LayoutManager layoutManager = null;
    public static OrderAdapter adapter = null;
    private  Handler handler;
    @ViewInject(R.id.recycleview)
    private RecyclerView recyclerView;
    @ViewInject(R.id.title)
    private LinearLayout title;
    @ViewInject(R.id.empty)
    private LinearLayout empty;
    @ViewInject(R.id.swipe_container)
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_fragment,container,false);
        x.view().inject(this,v);
        Logger.d("CCC",String.valueOf(list.size()));

        if (list.size()==0){
            Logger.d("CCC","list是空的");
            empty();
        }else {
            init();
            Logger.d("CCC","执行到这了");
        }
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Logger.d("CCC","handler收到更新结果信息");
                        swipeRefreshLayout.setRefreshing(false);
                        list =ApplicationData.getInstance().getmOrderList();
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
        ApplicationData.getInstance().setmOrderHandler(handler);
        return  v;
    }
    public void init(){
        swipeRefreshLayout.setVisibility(VISIBLE);

        empty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new OrderAdapter(list,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDivider());
    }
    public void empty(){
        recyclerView.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        empty.setVisibility(View.VISIBLE);
    }

    @Event(value = {R.id.swipe_container},type =OnRefreshListener.class)
    private void refresh(){
        swipeRefreshLayout.setRefreshing(true);
        try {
            RequestUtil.queryOrder(ApplicationData.getInstance().getmUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
