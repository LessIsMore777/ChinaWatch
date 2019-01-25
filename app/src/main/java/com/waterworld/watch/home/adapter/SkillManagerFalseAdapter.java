package com.waterworld.watch.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.home.bean.SkillManagerFalseBean;

import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/5 14:33
 * 主要作用：未启用的技能列表(适配器)
 */
public class SkillManagerFalseAdapter extends RecyclerView.Adapter<SkillManagerFalseAdapter.MyHolder> {

    private Context mContext;
    private List<SkillManagerFalseBean> mData;

    public SkillManagerFalseAdapter(Context mContext, List<SkillManagerFalseBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.item_skill, parent, false);
        return new MyHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        myHolder.skillName.setText(mData.get(position).getSkillName());
        myHolder.skillImage.setImageDrawable(mData.get(position).getSkillImage());
        //判断是否开启编辑模式
        if (mData.get(position).isEdit()) {//开启了
            myHolder.cl_skill.setBackgroundResource(R.color.white_2);
            myHolder.skillAddOrDelete.setVisibility(View.VISIBLE);
            //增加还是删除的图标，通过服务器数据或者是本地数据来进行判断
            myHolder.skillAddOrDelete.setImageDrawable(mData.get(position).getSkillAddOrDelete());
        } else {//未开启
            myHolder.cl_skill.setBackgroundResource(R.color.white_1);
            myHolder.skillAddOrDelete.setVisibility(View.GONE);
            //myHolder.skillAddOrDelete.setImageDrawable(mData.get(position).getSkillAddOrDelete());
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView skillName;
        private ImageView skillImage;
        private ImageButton skillAddOrDelete;
        private ConstraintLayout cl_skill;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            skillName = itemView.findViewById(R.id.tv_skill_name);
            skillImage = itemView.findViewById(R.id.iv_skill_img);
            skillAddOrDelete = itemView.findViewById(R.id.ib_add_or_delete);
            cl_skill = itemView.findViewById(R.id.cl_skill);
        }
    }
}
