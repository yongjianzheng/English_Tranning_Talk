package com.yongjian.english_tranning_talk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.bean.OrderList;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by YONGJIAN on 2016/6/10 0010.
 */
public class OrderAdapter extends RecyclerView.Adapter {

    private ArrayList<OrderList> dataset;
    public  Context context;

    public OrderAdapter(ArrayList<OrderList> dataset,Context context) {
        this.dataset = dataset;
        this.context =context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        ViewHolder mvh = new ViewHolder(v);
          return mvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mvh = (ViewHolder) holder;
        mvh.year.setText(dataset.get(position).getDate().substring(0,10));
        mvh.time.setText(dataset.get(position).getDate().substring(10));
        mvh.duration.setText(dataset.get(position).getDuration());
        mvh.cost.setText(dataset.get(position).getCost());
        holder.itemView.setTag(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @ViewInject(R.id.year)
        private TextView year;
        @ViewInject(R.id.time)
        private TextView time;
        @ViewInject(R.id.duration)
        private TextView duration;
        @ViewInject(R.id.cost)
        private TextView cost;

        public ViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
        }
    }
}
