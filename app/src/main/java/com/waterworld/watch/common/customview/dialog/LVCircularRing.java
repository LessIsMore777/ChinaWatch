package com.waterworld.watch.common.customview.dialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.waterworld.watch.R;

/**
 * Created by nhuan
 * Time:2018/12/21.
 */

public class LVCircularRing extends View{
    private Paint mPaint;//画笔
    private int color = Color.argb(100, 255, 255, 255);
    private ObjectAnimator objectAnimator;
    public LVCircularRing(Context context) {
        this(context, null);
    }

    public LVCircularRing(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LVCircularRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//描边
        mPaint.setColor(Color.WHITE);//画笔颜色
        mPaint.setStrokeWidth(8);//描边宽度
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation") Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.loading);
        int xmlHeight = getHeight();
        bitmap = Bitmap.createScaledBitmap(bitmap,xmlHeight,xmlHeight,true);
        canvas.drawBitmap(bitmap,new Matrix(),mPaint);
    }

    public void startAnim(View view) {
        stopAnim();
        startViewAnim(view);
    }

    public void stopAnim() {
        if (objectAnimator != null) {
            clearAnimation();
            objectAnimator.setRepeatCount(1);
            objectAnimator.cancel();
            objectAnimator.end();
        }
    }

    private ObjectAnimator startViewAnim(View view) {
        objectAnimator = ObjectAnimator.ofFloat(view,"rotation", 0f, 360f);
        objectAnimator.setDuration(1000);//设置属性动画持续时间
        objectAnimator.setInterpolator(new LinearInterpolator());//设置插值器
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);//重复播放模式为从头开始播放
        objectAnimator.start();
        return objectAnimator;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
        mPaint.setColor(color);
    }
}
