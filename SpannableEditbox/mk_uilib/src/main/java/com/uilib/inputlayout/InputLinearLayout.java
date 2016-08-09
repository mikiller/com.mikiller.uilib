package com.uilib.inputlayout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Mikiller on 2016/7/13.
 */
public class InputLinearLayout extends LinearLayout{
    private KeyboardStateListener listener;

    public InputLinearLayout(Context context) {
        super(context);
    }

    public InputLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setKeyboardStateListener(KeyboardStateListener listener){
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (isKeyboardShown(this.getRootView())) {
            if(listener != null){
                listener.onKeyboardShown();
            }
        } else {
            if(listener != null)
                listener.onKeyboardHiden();
        }
    }

    private boolean isKeyboardShown(View rootView) {
        final int keyboardHeight = 100;
        Rect rect = new Rect();
        rootView.getWindowVisibleDisplayFrame(rect);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int hx = rootView.getBottom() - rect.bottom;
        return hx > keyboardHeight * dm.density;
    }
}
