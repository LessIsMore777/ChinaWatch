package com.waterworld.watch.login.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuidePageActivity extends BaseActivity {

    private List<View> mList;
    private int[] imageView = {R.drawable.guide_page_1,R.drawable.guide_page_2,R.drawable.guide_page_3};

    @BindView(R.id.vp_guide)
    ViewPager vp_guide;

    @BindView(R.id.iv_enter)
    ImageView iv_enter;

    @BindView(R.id.ll_splash)
    LinearLayout ll_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        ButterKnife.bind(this);
        initView();
        onClick();
    }

    private void initView(){
        mList = new ArrayList<>();
        LinearLayout.LayoutParams layoutParam1 = new LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT,ViewPager.LayoutParams.MATCH_PARENT);
        for (int i=0;i<imageView.length;i++){
            ImageView iv1 = new ImageView(this);
            iv1.setLayoutParams(layoutParam1);
            iv1.setScaleType(ImageView.ScaleType.FIT_START);
            iv1.setImageResource(imageView[i]);
            mList.add(iv1);
        }
        vp_guide.setAdapter(new MyPagerAdapter(mList));
        ll_splash.getChildAt(0).setBackgroundResource(R.drawable.splash_select);
    }

    private void onClick(){
        vp_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<imageView.length;i++){
                    if(i == position) {
                        ll_splash.getChildAt(position).setBackgroundResource(R.drawable.splash_select);
                    }else {
                        ll_splash.getChildAt(i).setBackgroundResource(R.drawable.splash_normal);
                    }
                }
                if(position == imageView.length-1){
                    iv_enter.setVisibility(View.VISIBLE);
                }else {
                    iv_enter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        iv_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuidePageActivity.this,LoginActivity.class));
                finish();
            }
        });

    }


    public class MyPagerAdapter extends PagerAdapter {

        List<View> mList;

        public MyPagerAdapter(List<View> list) {
            mList = list;
        }
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mList.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }
    }
}
