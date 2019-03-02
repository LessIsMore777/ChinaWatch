package com.waterworld.watch.home.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.customview.CustomDialog;
import com.waterworld.watch.common.util.AppManager;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WatchBindActivity extends BaseActivity{

    @BindView(R.id.header_parent)
    LinearLayout header_parent;

    @BindView(R.id.header_back)
    ImageView header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.et_bind_code)
    EditText et_bind_code;

    @BindView(R.id.iv_scan_qr)
    ImageView iv_scan_qr;

    @BindView(R.id.btn_confirm)
    Button btn_confirm;

    //相机权限
    private static final int REQ_CODE_PERMISSION = 0x01;

    private CustomDialog mDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_bind);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 10);
        header_title.setText(getString(R.string.watch_bind));
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);

        mDialog = new CustomDialog(this, getString(R.string.warm_prompt), getString(R.string.confirm_exit_app),new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                AppManager.finishAllActivity();
            }
        }, new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        },getString(R.string.confirm),getString(R.string.cancel));
    }

    private void startCaptureActivityForResult() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(WatchBindActivity.this);
        intentIntegrator.setCaptureActivity(ScanQRCodeActivity.class);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCaptureActivityForResult();
                } else {
                    ToastUtils.showLong(this, getString(R.string.have_to_agree_premissions));
                }
            }
            break;
        }
    }

    //拿到扫描返回数据，去绑定
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String content = result.getContents();
                startActivity(new Intent(this,BabyRelationShipActivity.class).putExtra("imei",content));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.header_back)
    void onBack(){
        mDialog.setCanotBackPress();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @OnClick(R.id.iv_scan_qr)
    void onScan(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
        } else {
            startCaptureActivityForResult();
        }
    }

    @OnClick(R.id.btn_confirm)
    void onConfirm(){
        if (et_bind_code.getText().toString().trim().length() == 1){
            String content = et_bind_code.getText().toString().trim();
            startActivity(new Intent(this,BabyRelationShipActivity.class).putExtra("imei",content));
        } else {
            ToastUtils.showShort(this,getString(R.string.imei_illegal));
        }
    }
}
