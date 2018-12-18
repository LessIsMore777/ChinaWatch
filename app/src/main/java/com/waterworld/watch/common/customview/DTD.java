package com.waterworld.watch.common.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.waterworld.watch.R;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/7 10:54
 * 主要作用：
 */
public class DTD extends Dialog {

    private Context context;
    private String title;
    private String confirmButtonText;
    private String cacelButtonText;
    private ClickListenerInterface clickListenerInterface;

    private int layoutID;//布局文件id
    private int[] viewID;//要监听的控件id
    private String content;//
    public interface ClickListenerInterface {
        void doConfirm();

        void doCancel();
    }

    public DTD(Context context, String title, String confirmButtonText, String cacelButtonText) {
        super(context, R.style.CustomDialog);
        this.context = context;
        this.title = title;
        this.confirmButtonText = confirmButtonText;
        this.cacelButtonText = cacelButtonText;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.common_dialog, null);
        setContentView(view);
        TextView tvTitle =  view.findViewById(R.id.tv_content);
        TextView tvConfirm =  view.findViewById(R.id.btn_enter);
        TextView tvCancel =  view.findViewById(R.id.btn_exit);

        tvTitle.setText(title);
        tvConfirm.setText(confirmButtonText);
        tvCancel.setText(cacelButtonText);

        tvConfirm.setOnClickListener(new clickListener());
        tvCancel.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_enter:
                    clickListenerInterface.doConfirm();
                    break;
                case R.id.btn_exit:
                    clickListenerInterface.doCancel();
                    break;
            }
        }
    }
}
