package com.waterworld.watch.home.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.home.adapter.SelectAvatarAdapter;
import com.waterworld.watch.home.bean.AvatarBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/7 19:53
 * 主要作用：宝贝信息-->设置头像页
 */
public class BabySelectAvatarActivity extends BaseActivity implements View.OnClickListener {

    private BabySelectAvatarActivity thisActivity;
    /**
     * header部分
     */
    private LinearLayout header_parent;
    private ImageButton header_back;
    private TextView header_title;
    private Button header_save;
    /**
     * recycler
     */
    private RecyclerView avatarRecycler;
    private SelectAvatarAdapter avatarAdapter;
    private List<AvatarBean> avatarData;
    /**
     *
     */
    private ConstraintLayout click_takePhoto;//拍照
    private ConstraintLayout click_album;//从相册选择
    public static final int TAKE_PHOTO = 1;//拍摄照片
    public static final int CROP_PHOTO = 2;//修改照片
    private Uri imageUri;//自拍的URI

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_set_avatar);
        bindView();
        initView();
        bindClick();
        initRecycler();
        listClick();
    }

    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 11);
        header_title.setText("头像");
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_save.setVisibility(View.VISIBLE);
    }

    private void bindView() {
        header_parent = findViewById(R.id.header_parent);
        header_back = findViewById(R.id.header_back);
        header_title = findViewById(R.id.header_title);
        header_save = findViewById(R.id.header_save);
        click_takePhoto = findViewById(R.id.cl_take_photo);
        click_album = findViewById(R.id.cl_album);
        avatarRecycler = findViewById(R.id.avatarRecycler);
    }

    private void bindClick() {
        header_back.setOnClickListener(this);
        header_save.setOnClickListener(this);
        click_takePhoto.setOnClickListener(this);
        click_album.setOnClickListener(this);
    }

    private void initRecycler() {
        avatarData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            avatarData.add(new AvatarBean(getResources().getDrawable(R.drawable.ic_avatar_1)));
        }
        avatarAdapter = new SelectAvatarAdapter(thisActivity, avatarData);
        avatarRecycler.setLayoutManager(new GridLayoutManager(thisActivity, 4));
        avatarRecycler.setAdapter(avatarAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_save:
                //保存
                break;
            case R.id.cl_take_photo:
                //拍照
                break;
            case R.id.cl_album:
                //从相册选择
                break;
        }
    }

    /**
     * 列表item点击事件
     */
    private void listClick() {
        avatarAdapter.setOnSelectAvatarClick(new SelectAvatarAdapter.onSelectAvatarClick() {
            @Override
            public void onSelectClick(View view, int position) {
                setCustomTimeToast(thisActivity, "选择了第" + position + "张图片", 3000);
            }
        });
    }

    /**
     * 保存数据
     */
    private void save() {

    }

    private void takePhoto() {
        File outputImage = new File(Environment.getExternalStorageDirectory(),
                "tempImage" + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);

    }
}
