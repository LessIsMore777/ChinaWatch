package com.waterworld.watch.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.util.ScreenAdapterUtil;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/13 17:20
 * 主要作用：联系人信息(活动)
 */
public class SkillContactInfoActivity extends BaseActivity  {

    /**
     * 头部组件view
     */
    private LinearLayout header_parent;
    private ImageView header_back;
    private TextView header_title;
    private Button header_save;
    /**
     * 联系人信息view
     */
    private EditText editName;
    private EditText editPhone;
    /**
     * 权限开关view
     */
    private CheckBox normalCall;//普通通话权限开关
    private CheckBox urgentCall;//紧急通话权限开关
    private CheckBox singleListener;//单向聆听权限开关
    /**
     * 删除按钮
     */
    private Button btnDelete;//删除按钮
    /**
     * other
     */
    private SkillContactInfoActivity thisActivity;
    private int mode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_skill_contact_info);
        bindView();
        initView();
        getIntentData();
    }

    /**
     * 根据意图加载不同的布局
     */
    private void getIntentData(){

    }

    private void bindView(){
        header_parent = findViewById(R.id.header_parent);
        header_back = findViewById(R.id.header_back);
        header_title = findViewById(R.id.header_title);
        header_save = findViewById(R.id.header_save);
        editName = findViewById(R.id.et_contact_name);
        editPhone = findViewById(R.id.et_contact_phone);
        normalCall = findViewById(R.id.cb_normal_call);
        urgentCall = findViewById(R.id.cb_urgent_call);
        singleListener = findViewById(R.id.cb_single_listener);
        btnDelete = findViewById(R.id.btn_delete);
    }

    /**
     * 初始化View
     * 首先拿到意图携带的数据，判断用户是要增加联系人还是编辑某个联系人
     */
    private void initView(){
        Bundle bundle = thisActivity.getIntent().getExtras();
        assert bundle != null;
        String addMode = bundle.getString("addContact");
        int editMode = bundle.getInt("editContact");
        if (addMode != null) {
            initAddContact();
            mode = 0x01;
        } else if (editMode != -1) {
            initEditContact(editMode);
            mode = 0x02;
        }
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_save.setVisibility(View.VISIBLE);
    }

    /**
     *
     */
    private void initAddContact(){
        header_title.setText("添加联系人");
    }

    /**
     * 初始化编辑联系人模式
     * @param contactIndex 上个页面传递过来的联系人下标
     */
    private void initEditContact(int contactIndex){
        header_title.setText("编辑联系人");
        btnDelete.setVisibility(View.VISIBLE);
    }

}
