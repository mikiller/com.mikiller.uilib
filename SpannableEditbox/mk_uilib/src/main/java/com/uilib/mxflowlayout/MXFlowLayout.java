package com.uilib.mxflowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikiller on 2016/8/8.
 */
public class MXFlowLayout extends ViewGroup{
    List<List<View>> allChildren = new ArrayList<>();

    public MXFlowLayout(Context context) {
        super(context);
    }

    public MXFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MXFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0, height = 0;

        if(widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST){
            int childCount = getChildCount();
            int lineWidth = 0, lineHeight = 0;
            for(int i = 0; i < childCount; i++){
                View child = getChildAt(i);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                if(lp == null) {
                    lp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                }
                int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                if(lineWidth + childWidth > sizeWidth){
                    width = Math.max(childWidth, lineWidth);
                    lineWidth = childWidth;
                    height += lineHeight;
                    lineHeight = childHeight;
                }else {
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }

                if(i == childCount - 1){
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
            }
        }

        setMeasuredDimension(widthMode == MeasureSpec.AT_MOST ? width : sizeWidth, heightMode == MeasureSpec.AT_MOST ? height : sizeHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        allChildren.clear();
        List<Integer> lineHeights = new ArrayList<>();
        int lineWidth = 0, lineHeight = 0;
        int width = getWidth();
        int childCount = getChildCount();
        List<View> lineViews = new ArrayList<>();
        for(int i = 0; i < childCount; i++){
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if(lineWidth + childWidth > width){
                lineHeights.add(lineHeight);
                allChildren.add(lineViews);
                lineWidth = 0;
                lineViews = new ArrayList<>();
            }
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
                lineViews.add(child);


        }
        lineHeights.add(lineHeight);
        allChildren.add(lineViews);

        int left = 0, top = 0;
        int lineNums = allChildren.size();
        for(int i = 0; i < lineNums; i++){
            List<View> lvs = allChildren.get(i);
            lineHeight = lineHeights.get(i);

            for(int j = 0; j < lvs.size(); j++){
                View c = lvs.get(j);
                if(c.getVisibility() == GONE)
                    continue;
                MarginLayoutParams lp = (MarginLayoutParams) c.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + c.getMeasuredWidth();
                int bc = tc + c.getMeasuredHeight();
                c.layout(lc, tc, rc, bc);

                left += c.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }
            left = 0;
            top += lineHeight;
        }

    }
}
