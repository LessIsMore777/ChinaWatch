package com.waterworld.watch.mine.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.waterworld.watch.R;
import com.waterworld.watch.mine.bean.AvatarBean;
import com.waterworld.watch.mine.interfaces.onAvatarSelectListener;

import java.util.List;

/**
 * Created by nhuan
 * Time:2019/1/7.
 */

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.MyHolder> {

    private Context mContext;
    private List<AvatarBean> list;
    private onAvatarSelectListener listener;

    public AvatarAdapter(Context mContext, List<AvatarBean> list){
        this.mContext = mContext;
        this.list = list;
    }

    public void setOnAvatarSelectListener(onAvatarSelectListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View holder = LayoutInflater.from(mContext).inflate(R.layout.item_user_avatar,viewGroup,false);
        MyHolder myHolder = new MyHolder(holder);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
        myHolder.avatar.setImageDrawable(list.get(i).getAvatar());
        if(list.get(i).isSelect()){
            myHolder.isSelect.setVisibility(View.VISIBLE);
        }else {
            myHolder.isSelect.setVisibility(View.GONE);
        }
        if (listener != null) {
            myHolder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = myHolder.getLayoutPosition();
                    listener.onSelect(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        ImageView isSelect;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.iv_avatar);
            isSelect = itemView.findViewById(R.id.iv_isSelect);
        }
    }
}
