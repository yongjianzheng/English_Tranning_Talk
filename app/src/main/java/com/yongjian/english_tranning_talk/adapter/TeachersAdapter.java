package com.yongjian.english_tranning_talk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yongjian.english_tranning_talk.R;
import com.yongjian.english_tranning_talk.activity.AssesActivity;
import com.yongjian.english_tranning_talk.bean.User;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by YONGJIAN on 2016/6/11 0011.
 */
public class TeachersAdapter extends RecyclerView.Adapter {

    private ArrayList<User>  dataset;
    private Context context;

    public TeachersAdapter(ArrayList<User> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teachers_item,parent,false);
        ViewHolder mvh = new ViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mvh = (ViewHolder) holder;
        mvh.t_name.setText(dataset.get(position).getName());
        mvh.t_sex.setText(dataset.get(position).getSex());
        mvh.t_grade.setText(dataset.get(position).getGrade());
        mvh.t_pay.setText(dataset.get(position).getHourypay());
        holder.itemView.setTag(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @ViewInject(R.id.t_name)
        private TextView t_name;
        @ViewInject(R.id.t_sex)
        private TextView t_sex;
        @ViewInject(R.id.t_grade)
        private TextView t_grade;
        @ViewInject(R.id.t_pay)
        private TextView t_pay;
        public ViewHolder(View itemView) {
            super(itemView);
            x.view().inject(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AssesActivity.startAssactivity(context, (User) v.getTag());

        }
    }
}
