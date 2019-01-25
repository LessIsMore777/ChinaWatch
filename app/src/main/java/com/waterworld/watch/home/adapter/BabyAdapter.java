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
import com.waterworld.watch.home.bean.BabyBean;

import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/29 18:29
 * 主要作用：
 */
public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.MyHolder> {

    private Context mContext;
    private List<BabyBean> mData;

    public BabyAdapter(Context mContext, List<BabyBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.item_baby, parent, false);
        return new MyHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int position) {
        myHolder.baby_name.setText(mData.get(position).getBabyName());
        myHolder.baby_avatar.setImageDrawable(mData.get(position).getBabyAvatar());
        if (babyClick != null) {
            myHolder.baby_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (babyClick != null) {
                        int position = myHolder.getLayoutPosition();
                        babyClick.onItem(view,position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private LinearLayout baby_item;
        private ImageView baby_avatar;
        private TextView baby_name;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            baby_item = itemView.findViewById(R.id.baby_item);
            baby_avatar = itemView.findViewById(R.id.baby_avatar);
            baby_name = itemView.findViewById(R.id.baby_name);
        }
    }

    private BabyClick babyClick;

    public void setBabyClick(BabyClick babyClick) {
        this.babyClick = babyClick;
    }

    public interface BabyClick{
        void onItem(View view, int position);
    }
}
