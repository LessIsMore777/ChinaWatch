package com.waterworld.watch.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 编写者：Created by SunnyTang
 * 时间：2019/1/11 18:18
 * 主要作用：
 */
public class SkillCurriculumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    private int TYPE_CLASS = 1;
    private int TYPE_TABLE = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * 课程item
     */
    class classHolder extends RecyclerView.ViewHolder {
        public classHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /**
     * 表头item
     */
    class TableHeaderHolder extends RecyclerView.ViewHolder{

        public TableHeaderHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
