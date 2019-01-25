package com.waterworld.watch.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.waterworld.watch.R;
import com.waterworld.watch.home.bean.BabyAvatarBean;

import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/7 19:54
 * 主要作用：宝贝头像(适配器)
 */
public class BabyAvatarAdapter extends RecyclerView.Adapter<BabyAvatarAdapter.MyHolder> {
    private Context mContext;
    private List<BabyAvatarBean> mData;
    private onSelectAvatarClick onSelectAvatarClick;

    public BabyAvatarAdapter(Context mContext, List<BabyAvatarBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public interface onSelectAvatarClick {
        void onSelectClick(View view, int position);
    }

    public void setOnSelectAvatarClick(BabyAvatarAdapter.onSelectAvatarClick onSelectAvatarClick) {
        this.onSelectAvatarClick = onSelectAvatarClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View myHolder = inflater.inflate(R.layout.item_avatar, parent, false);
        return new MyHolder(myHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int position) {
        myHolder.avatar.setImageDrawable(mData.get(position).getAvatar());
        if (mData.get(position).isChecked()){
            myHolder.avatarChecked.setVisibility(View.VISIBLE);
        } else {
            myHolder.avatarChecked.setVisibility(View.GONE);
        }
        if (onSelectAvatarClick != null ) {
            myHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = myHolder.getLayoutPosition();
                    onSelectAvatarClick.onSelectClick(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public List<BabyAvatarBean> getmData() {
        return mData;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        ImageView avatarChecked;
        MyHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.iv_avatar);
            avatarChecked = itemView.findViewById(R.id.iv_avatar_checked);
        }
    }
}
