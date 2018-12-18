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
import com.waterworld.watch.home.bean.ContactBean;

import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/12 19:15
 * 主要作用：联系人列表适配器
 */
public class SkillContactAdapter extends RecyclerView.Adapter<SkillContactAdapter.MyHolder> {

    private Context mContext;
    private List<ContactBean> mData;
    private onContactClickListener onContactClickListener;

    public SkillContactAdapter(Context mContext, List<ContactBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public interface onContactClickListener {
        void contactClick(View view,int position);
    }

    public void setOnContactClickListener(SkillContactAdapter.onContactClickListener onContactClickListener) {
        this.onContactClickListener = onContactClickListener;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ConstraintLayout parent;
        ImageView contactAvatar;
        TextView contactName;
        TextView contactPhone;

        MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int position) {
        myHolder.contactName.setText(mData.get(position).getContactName());
        myHolder.contactPhone.setText(mData.get(position).getContactPhone());
        myHolder.contactAvatar.setImageDrawable(mData.get(position).getContactAvatar());
        if (onContactClickListener != null) {
            myHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = myHolder.getLayoutPosition();
                    onContactClickListener.contactClick(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

}
