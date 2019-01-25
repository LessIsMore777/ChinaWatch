package com.waterworld.watch.location.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopeer.itemtouchhelperextension.Extension;
import com.waterworld.watch.R;
import com.waterworld.watch.location.activity.AddElectronicFenceActivity;
import com.waterworld.watch.location.bean.ElectronicFenceBean;
import com.waterworld.watch.location.interfaces.onElectronicFenceClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nhuan
 * Time:2019/1/22.
 */

public class ElectronicFenceAdapter extends RecyclerView.Adapter<ElectronicFenceAdapter.MyHolder>{

    private Context mContext;
    private List<ElectronicFenceBean> mList;
    private onElectronicFenceClickListener mListener;

    public ElectronicFenceAdapter(Context context, List<ElectronicFenceBean> list){
        mContext = context;
        mList = list;
    }

    public void setOnElectronicFenceClickListener(onElectronicFenceClickListener listener){
        mListener = listener;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_electronic_fence,viewGroup,false);
        return new ExtendHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.tv_fence_name.setText(mContext.getString(R.string.electronic_fence_name)+mList.get(i).getFence_name());
        myHolder.tv_remind_way.setText(mContext.getString(R.string.remind_way)+mList.get(i).getRemind_way());
        myHolder.tv_safety_range.setText(mContext.getString(R.string.safety_range)+mList.get(i).getSafety_range());
        myHolder.cb_enable.setChecked(mList.get(i).isEnable());
        if(mListener != null){
            myHolder.cb_enable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.get(i).setEnable(!mList.get(i).isEnable());
                }
            });

            myHolder.rl_item_electronic_fence.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, AddElectronicFenceActivity.class));
                }
            });
            myHolder.ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(i);
                    notifyItemRemoved(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.rl_item_electronic_fence)
        RelativeLayout rl_item_electronic_fence;

        @BindView(R.id.tv_fence_name)
        TextView tv_fence_name;

        @BindView(R.id.tv_remind_way)
        TextView tv_remind_way;

        @BindView(R.id.tv_safety_range)
        TextView tv_safety_range;

        @BindView(R.id.cb_enable)
        CheckBox cb_enable;

        @BindView(R.id.ll_container)
        LinearLayout ll_container;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ExtendHolder extends MyHolder implements Extension {

        View mActionViewDelete;

        public ExtendHolder(View itemView) {
            super(itemView);
            mActionViewDelete = itemView.findViewById(R.id.tv_delete);
        }

        @Override
        public float getActionWidth() {
            return ll_container.getWidth();
        }
    }

}
