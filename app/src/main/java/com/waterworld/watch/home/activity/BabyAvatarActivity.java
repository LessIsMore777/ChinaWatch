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
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.adapter.BabyAvatarAdapter;
import com.waterworld.watch.home.bean.BabyAvatarBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/7 19:53
 * 主要作用：宝贝头像(活动)
 */
public class BabyAvatarActivity extends BaseActivity implements View.OnClickListener {

    private BabyAvatarActivity thisActivity;
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
    private BabyAvatarAdapter avatarAdapter;
    private List<BabyAvatarBean> avatarData;
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
        setContentView(R.layout.activity_baby_avatar);
        bindView();
        initView();
        bindClick();
        initRecycler();
        listClick();
    }

    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
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
            avatarData.add(new BabyAvatarBean(getResources().getDrawable(R.drawable.ic_avatar_1_smile), false));
        }
        avatarAdapter = new BabyAvatarAdapter(thisActivity, avatarData);
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
                save();
                //保存
                break;
            case R.id.cl_take_photo:
                takePhoto();
                //拍照
                break;
            case R.id.cl_album:
                //从相册选择
                break;
        }
    }

    /**
     * 列表item点击事件
     * 实现单选效果
     */
    private void listClick() {
        avatarAdapter.setOnSelectAvatarClick(new BabyAvatarAdapter.onSelectAvatarClick() {
            @Override
            public void onSelectClick(View view, int position) {
                //1.首先判断当前点击的item头像选中状态是否为true
                if (avatarAdapter.getmData().get(position).isChecked()) {
                    //1.1遍历当前的list。
                    for (int i = 0; i <= avatarData.size() - 1; i++) {
                        //把全部的item头像选中状态设置为false。
                        avatarData.get(i).setChecked(false);
                    }
                    //1.2记得刷新适配器
                    avatarAdapter.notifyDataSetChanged();
                } else {//2.如果不是true，则有两种情况要判断
                    int falseCount = 0;//2.1 falseCount表示记录list中item头像没有被选中的数量
                    int trueCount = 0;//2.2 trueCount表示记录list中item头像被选中的下标
                    //2.3 遍历list
                    for (int j = 0; j <= avatarData.size() - 1; j++) {
                        //2.4 判断每一个item选中状态是否为false
                        if (!avatarData.get(j).isChecked()) {
                            //2.5 如果为false，那么falseCount自增。
                            falseCount++;
                        } else {
                            //2.6 如果为true，则记录true的下标。
                            trueCount = j;
                        }
                    }
                    //3.循环结束，判断falseCount的数量是否为list的元素个数，如果相等。则表示当前list中所有的item没有一个是被选中的
                    if (falseCount == avatarData.size()) {
                        //3.1那么就设置当前点击的item为选中状态
                        avatarData.get(position).setChecked(true);
                        //3.2记得刷新适配器
                        avatarAdapter.notifyDataSetChanged();
                    } else {//4.如果不相等，表示list中有某个元素被选中了
                        //4.1那么设置被选中的为false
                        avatarData.get(trueCount).setChecked(false);
                        //4.2把点击的item设置为true
                        avatarData.get(position).setChecked(true);
                        avatarAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * 保存数据
     */
    private void save() {
        for (int i = 0; i <= avatarData.size() - 1; i++) {
            if (avatarData.get(i).isChecked()) {
                ToastUtils.showCustomTime(thisActivity, "当前选中的是第:" + (i + 1) + "个头像", 3000);
                break;
            } else {
                ToastUtils.showCustomTime(thisActivity, "请选择系统头像或者自定义头像", 3000);
            }
        }
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
