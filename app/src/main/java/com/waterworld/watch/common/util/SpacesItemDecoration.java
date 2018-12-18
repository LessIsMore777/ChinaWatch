package com.waterworld.watch.common.util;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/4 10:19
 * 主要作用：
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final String TAG = "SpacesItemDecoration";
    private int leftRight;
    private int topBottom;

    public SpacesItemDecoration(int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        Log.i(TAG,"增加了间隔");
        //得到网格布局管理对象
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        //得到网格布局参数对象
        final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        //获取view在适配器中的位置
        final int childPosition = parent.getChildAdapterPosition(view);
        //获取网格布局中的列数
        assert layoutManager != null;
        final int spanCount = layoutManager.getSpanCount();
        //如果网格布局的排列方式为垂直
        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
            //判断是否在第一排
            if (layoutManager.getSpanSizeLookup().getSpanGroupIndex(childPosition, spanCount) == 0) {//第一排的需要上面
                outRect.top = topBottom;
            }
            outRect.bottom = topBottom;
            //这里忽略和合并项的问题，只考虑占满和单一的问题
            if (lp.getSpanSize() == spanCount) {//占满
                outRect.left = leftRight;
                outRect.right = leftRight;
            } else {
                outRect.left = (int) (((float) (spanCount - lp.getSpanIndex())) / spanCount * leftRight);
                outRect.right = (int) (((float) leftRight * (spanCount + 1) / spanCount) - outRect.left);
            }
        } else {
            if (layoutManager.getSpanSizeLookup().getSpanGroupIndex(childPosition, spanCount) == 0) {//第一排的需要left
                outRect.left = leftRight;
            }
            outRect.right = leftRight;
            //这里忽略和合并项的问题，只考虑占满和单一的问题
            if (lp.getSpanSize() == spanCount) {//占满
                outRect.top = topBottom;
                outRect.bottom = topBottom;
            } else {
                outRect.top = (int) (((float) (spanCount - lp.getSpanIndex())) / spanCount * topBottom);
                outRect.bottom = (int) (((float) topBottom * (spanCount + 1) / spanCount) - outRect.top);
            }
        }
    }


}
