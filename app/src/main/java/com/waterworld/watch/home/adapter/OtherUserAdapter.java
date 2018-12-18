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
import com.waterworld.watch.home.bean.OtherUserBean;

import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/6 11:23
 * 主要作用：
 */
public class OtherUserAdapter extends RecyclerView.Adapter<OtherUserAdapter.MyHolder> {

    private Context mContext;
    private List<OtherUserBean> mData;

    public OtherUserAdapter(Context mContext, List<OtherUserBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.item_other_user, parent, false);
        return new MyHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int position) {
        myHolder.otherUserAvatar.setImageDrawable(mData.get(position).getUserAvatar());
        myHolder.otherUserName.setText(mData.get(position).getUserName());
        myHolder.otherUserRelation.setText(mData.get(position).getUserRelation());
        if (onOtherUserClick != null) {
            myHolder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onOtherUserClick != null) {
                        int position = myHolder.getLayoutPosition();
                        onOtherUserClick.onOtherUserClick(view,position);
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
        ConstraintLayout parent;
        ImageView otherUserAvatar;//其他用户头像
        TextView otherUserName;//其他用户的名称
        TextView otherUserRelation;//其他用户与当前手表使用者之间的关系

        MyHolder(@NonNull View itemView) {
            super(itemView);
            otherUserAvatar = itemView.findViewById(R.id.iv_other_user_avatar);
            otherUserName = itemView.findViewById(R.id.tv_other_user_name);
            otherUserRelation = itemView.findViewById(R.id.tv_other_user_relation);
            parent = itemView.findViewById(R.id.cl_parent);
        }
    }

    private OtherUserClickListener onOtherUserClick;

    public interface OtherUserClickListener{
        void onOtherUserClick(View view,int position);
    }

    public void setOnOtherUserClick(OtherUserClickListener onOtherUserClick) {
        this.onOtherUserClick = onOtherUserClick;
    }
}
