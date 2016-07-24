package com.yongjian.english_tranning_talk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.bean.Asses;

import java.util.ArrayList;

/**
 * Created by YONGJIAN on 2016/6/10 0010.
 */
public class AssesAdapter extends RecyclerView.Adapter {

    private ArrayList<Asses> dataset;
    public Context context;

    public AssesAdapter(ArrayList<Asses> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.asses_item,parent,false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mvh = (ViewHolder) holder;
        mvh.grade.setText(dataset.get(position).getAssgrade());
        mvh.content.setText(dataset.get(position).getContent());
        holder.itemView.setTag(dataset.get(position));

    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView grade = null;
        private TextView content = null;

        public ViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            grade = (TextView) itemView.findViewById(R.id.assgrade);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
