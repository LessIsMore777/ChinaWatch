package com.waterworld.watch.common.customview.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nhuan
 * Time:2018/12/21.
 * 自定义控件_圆形加载控件
 */

public class LoadCircleView extends View{
    private float mPadding = 0f;
    private RectF rectF;
    private Context mContext;
    private Paint mPaint;
    private int mWidth = 0;
    private int currentLineIndex = 0;

    /**
     * 如果是在java代码里面new的，则调用这个
     * @param context 上下文
     */
    public LoadCircleView(Context context) {
        this(context, null);
    }

    /**
     * 如果是在xml文件里面声明的，则调用这个
     * 自定义的属性是由AttributeSet传进来的
     * @param context 上下文
     * @param attrs 自定义属性
     */
    public LoadCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 不会自动调用
     * 一般是在第二个构造函数里主动调用
     * 如View有style属性时
     * @param context 上下文
     * @param attrs 自定义属性
     * @param defStyleAttr style属性
     */
    public LoadCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void init() {
        mPaint = new Paint();//创建画笔对象
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//设置绘制风格为描边
//        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);//设置描边的宽度
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取高度测量模式
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取高度测量大小
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        //获取宽度测量模式
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽度测量大小
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //如果高度测量模式和宽度测量模式，都不等于AT_MOST
        if (widthSpecMode != MeasureSpec.AT_MOST && heightSpecMode != MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize >= heightSpecSize ? widthSpecSize : heightSpecSize;
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode != MeasureSpec.AT_MOST) {
            mWidth = heightSpecSize;
        } else if (widthSpecMode != MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = SizeUtils.dip2px(mContext, 50);
        }
        setMeasuredDimension(mWidth, mWidth);
        mPadding = 8;
//        rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 圆心坐标 (center,center)
        int center = mWidth >> 1;
        int radius = (mWidth >> 1) - 8;
        if (currentLineIndex >= 12)
            currentLineIndex = 0;
//        canvas.rotate(currentLineIndex * 30, center, center);
        // 画12根线
        for (int i = 0; i < 12; i++) {
            if (i < currentLineIndex + 4 && i >= currentLineIndex) {
                mPaint.setColor(Color.GRAY);
            } else if (currentLineIndex > 8 && i < currentLineIndex + 4 - 12) {
                mPaint.setColor(Color.GRAY);
            } else {
                mPaint.setColor(Color.WHITE);
            }

//            canvas.drawLine(center, (float) (center + 1.0 / 4 * center),
//                    center, (float) (center + 1.0 / 2 * radius), mPaint);
            canvas.drawLine(center, (float) (center + 1.0 / 2 * radius),
                    center, 2 * radius, mPaint);
            canvas.rotate(30, center, center);
        }
        currentLineIndex++;
        postInvalidateDelayed(50);
    }
}
