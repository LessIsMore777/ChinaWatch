package com.waterworld.watch.mine.adapter;

import android.annotation.SuppressLint;
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
import com.waterworld.watch.common.util.StringUtils;
import com.waterworld.watch.mine.bean.MessageBean;

import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2019/1/7 10:35
 * 主要作用：
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder> {

    private Context mContext;
    private List<MessageBean> mData;
    private onItemClick onItemClick;

    public interface onItemClick{
        void ItemClick(View view,int position);
    }

    public void setOnItemClick(MessageAdapter.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public MessageAdapter(Context mContext, List<MessageBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.item_mine_message_notification, parent, false);
        return new MyHolder(viewHolder);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, int position) {
        String content = mData.get(position).getContent();
        if (StringUtils.length(content) > 14){
            String tempContent = content.substring(0,7);
            myHolder.content.setText(tempContent +"...");
        } else {
            myHolder.content.setText(content);
        }
        myHolder.time.setText(mData.get(position).getTime());
        if (mData.get(position).isRead()){
            myHolder.isRead.setVisibility(View.VISIBLE);
        } else {
            myHolder.isRead.setVisibility(View.GONE);
        }
        if (onItemClick != null) {
            myHolder.parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = myHolder.getLayoutPosition();
                    onItemClick.ItemClick(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ConstraintLayout parentView;
        TextView content;
        TextView time;
        ImageView isRead;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            parentView = itemView.findViewById(R.id.parentView);
            content = itemView.findViewById(R.id.tv_content);
            time = itemView.findViewById(R.id.tv_time);
            isRead = itemView.findViewById(R.id.isRead);
        }
    }
}
