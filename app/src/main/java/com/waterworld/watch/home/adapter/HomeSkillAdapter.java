package com.waterworld.watch.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.home.bean.SkillManagerBean;

import java.util.List;

/**
 * Created by nhuan
 * Time:2019/3/1.
 */

public class HomeSkillAdapter extends RecyclerView.Adapter<HomeSkillAdapter.MyHolder>{

    private Context mContext;
    private List<SkillManagerBean> mData;

    public HomeSkillAdapter(Context mContext, List<SkillManagerBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public HomeSkillAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_skill, parent, false);
        return new HomeSkillAdapter.MyHolder(itemView);
    }

    public List<SkillManagerBean> getMData(){
        return mData;
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeSkillAdapter.MyHolder myHolder, int position) {
        myHolder.tv_skill.setText(mData.get(position).getSkillName());
        myHolder.iv_skill.setImageDrawable(mData.get(position).getSkillImage());
        if (addSkillClick != null) {
            myHolder.ll_skill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = myHolder.getLayoutPosition();
                    addSkillClick.skillClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView tv_skill;
        private ImageView iv_skill;
        private LinearLayout ll_skill;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_skill = itemView.findViewById(R.id.tv_skill);
            iv_skill = itemView.findViewById(R.id.iv_skill);
            ll_skill = itemView.findViewById(R.id.ll_skill);

        }
    }

    private HomeSkillAdapter.SkillClick addSkillClick;

    public void setAddSkillClick(HomeSkillAdapter.SkillClick addSkillClick) {
        this.addSkillClick = addSkillClick;
    }

    public interface SkillClick {
        void skillClick(View view, int position);
    }
}
