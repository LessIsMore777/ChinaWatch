package com.waterworld.watch.mine.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.util.PhotoUtils;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.login.activity.RegisterActivity;
import com.waterworld.watch.mine.event.AvatarEvent;
import com.waterworld.watch.mine.adapter.AvatarAdapter;
import com.waterworld.watch.mine.bean.AvatarBean;
import com.waterworld.watch.mine.interfaces.IMineManager;
import com.waterworld.watch.mine.interfaces.onAvatarSelectListener;
import com.waterworld.watch.mine.manager.MineManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AvatarActivity extends BaseActivity{

    @BindView(R.id.header_parent)
    LinearLayout header_parent;

    @BindView(R.id.header_back)
    ImageView header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.header_confirm)
    TextView header_save;

    @BindView(R.id.rv_avatar)
    RecyclerView rv_avatar;

    @BindView(R.id.rv_take_photo)
    RelativeLayout rv_take_photo;

    @BindView(R.id.rv_select_photo)
    RelativeLayout rv_select_photo;

    private List<AvatarBean> list;
    private AvatarAdapter adapter;

    private static final int CODE_GALLERY_REQUEST = 0x00;               //打开相册请求
    private static final int CODE_CAMERA_REQUEST = 0x01;                //打开相机请求
    private static final int CODE_RESULT_REQUEST = 0x02;                //结果回调
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;    //相机访问权限检测
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;   //sdcard访问权限检测

    private int output_X = 480;
    private int output_Y = 480;

    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath()+"/WaterWorld/ParentAvatar/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath()+"/WaterWorld/ParentAvatar/crop_photo.jpg");

    private Uri imageUri;
    private Uri cropImageUri;

    //recycleView点击位置
    private int sys_avatar;
    private String imgName;

    private IMineManager iMineManager = MineManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        ButterKnife.bind(this);
        if(!fileUri.getParentFile().exists()){
            fileUri.getParentFile().mkdirs();
        }
        initView();

        adapter.setOnAvatarSelectListener(new onAvatarSelectListener() {
            @Override
            public void onSelect(View view, int position) {
                for (int i = 0 ; i<list.size(); i++){
                    list.get(i).setSelect(false);
                }
                list.get(position).setSelect(true);
                adapter.notifyDataSetChanged();
                sys_avatar = position;
            }
        });
    }

    private void initView(){
        //setTitle
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this)/12);
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_title.setText(getString(R.string.avatar));
        header_save.setVisibility(View.VISIBLE);

        //setRecycleView
        list = new ArrayList<>();
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_01),true));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_02),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_03),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_04),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_05),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_06),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_07),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_08),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_09),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_10),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_11),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_12),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_13),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_14),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_15),false));
        list.add(new AvatarBean(getResources().getDrawable(R.drawable.sys_parent_16),false));
        adapter = new AvatarAdapter(this,list);
        rv_avatar.setLayoutManager(new GridLayoutManager(this,4));
        rv_avatar.setAdapter(adapter);
    }

    @OnClick(R.id.header_back)
    void back(){
        finish();
    }

    @OnClick(R.id.header_confirm)
    void save(){
        AvatarEvent avatarEvent = new AvatarEvent();
        avatarEvent.setType(1);
        avatarEvent.setName("sys_parent_"+new DecimalFormat("00").format(sys_avatar+1)+".png");
        EventBus.getDefault().post(avatarEvent);
        finish();
    }

    @OnClick(R.id.rv_take_photo)
    void onTakePhoto(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
                ToastUtils.showShort(this,getString(R.string.have_refused_once));
            }
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE}
                ,CAMERA_PERMISSIONS_REQUEST_CODE);
        }else {
            if(hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    imageUri = FileProvider.getUriForFile(this,"com.waterworld.fileprovider",fileUri);
                }
                PhotoUtils.takePicture(this,imageUri,CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.showShort(this,getString(R.string.insert_sdcard));
            }
        }
    }

    @OnClick(R.id.rv_select_photo)
    void onSelectPhoto(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this,CODE_GALLERY_REQUEST);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            imageUri = FileProvider.getUriForFile(this,"com.waterworld.fileprovider",fileUri);
                        }
                        PhotoUtils.takePicture(this,imageUri,CODE_CAMERA_REQUEST);
                    }else {
                        ToastUtils.showShort(this,getString(R.string.insert_sdcard));
                    }
                }else {
                    ToastUtils.showShort(this,getString(R.string.open_camera));
                }
                break;
            //调用系统相册申请sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    PhotoUtils.openPic(this,CODE_GALLERY_REQUEST);
                }else {
                    ToastUtils.showShort(this,getString(R.string.open_sdcard));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this,imageUri,cropImageUri,1,1
                    ,output_X,output_Y,CODE_RESULT_REQUEST);
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    if (hasSdcard()){
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this,data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this,"com.waterworld.fileprovider",new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(this,newUri,cropImageUri,1,1,
                                output_X,output_Y,CODE_RESULT_REQUEST);
                    } else {
                        ToastUtils.showShort(this,getString(R.string.insert_sdcard));
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    uploadImg();

                    break;
                default:
                    break;
            }
        }
    }

    //上传自定义图片
    private void uploadImg() {
        if (iMineManager != null) {
            iMineManager.uploadImg(fileCropUri, new BaseObserverListener<BaseResultBean<String>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showShort(AvatarActivity.this,getString(R.string.net_error));
                }

                @Override
                public void onNext(BaseResultBean<String> baseResultBean) {
                    if(baseResultBean.getCode() == 0) {
                        imgName = baseResultBean.getData();
                        AvatarEvent avatarEvent = new AvatarEvent();
                        avatarEvent.setType(0);
                        avatarEvent.setName(imgName);
                        EventBus.getDefault().post(avatarEvent);
                        finish();
                    }else {
                        ToastUtils.showShort(AvatarActivity.this,getString(R.string.upload_fail)+","+baseResultBean.getMsg());
                    }
                }
            });
        }
    }

    /**
     * 检查设备SDCard是否存在
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

}
