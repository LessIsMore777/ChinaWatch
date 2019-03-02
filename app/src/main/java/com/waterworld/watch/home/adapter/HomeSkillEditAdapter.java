package com.waterworld.watch.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.home.bean.SkillManagerEditBean;

import java.util.List;

public class HomeSkillEditAdapter extends RecyclerView.Adapter<HomeSkillEditAdapter.MyHolder> {

    private Context mContext;
    private List<SkillManagerEditBean> mData;

    public HomeSkillEditAdapter(Context mContext, List<SkillManagerEditBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_skill_edit, parent, false);
        return new MyHolder(itemView);
    }

    public List<SkillManagerEditBean> getMData(){
        return mData;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int position) {
        myHolder.skillName.setText(mData.get(position).getSkillName());
        myHolder.skillImage.setImageDrawable(mData.get(position).getSkillImage());
        myHolder.skillAddOrDelete.setImageDrawable(mData.get(position).getSkillAddOrDelete());
        myHolder.cl_skill.setBackgroundResource(R.color.white_2);
        if (operateSkill != null) {
            myHolder.cl_skill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = myHolder.getLayoutPosition();
                    operateSkill.operateSkill(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView skillName;
        private ImageView skillImage;
        private ImageView skillAddOrDelete;
        private ConstraintLayout cl_skill;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            skillName = itemView.findViewById(R.id.tv_skill_name);
            skillImage = itemView.findViewById(R.id.iv_skill_img);
            skillAddOrDelete = itemView.findViewById(R.id.ib_add_or_delete);
            cl_skill = itemView.findViewById(R.id.cl_skill);
        }
    }

    private operateSkillClick operateSkill;

    public void setOnSkillClick(operateSkillClick operateSkill) {
        this.operateSkill = operateSkill;
    }

    public interface operateSkillClick {
        void operateSkill(View view, int position);
    }

}
