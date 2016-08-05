package com.uilib.uploadimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.uilib.R;
import com.uilib.mxprogressbar.MXProgressbar;

/**
 * Created by Mikiller on 2016/8/4.
 */
public class MXUploadImageView extends RelativeLayout {
    public enum ImageState{
        STOP, PAUSE, START, FAILED, SUCCESS
    }

    private ProgressBar linePgsBar;
    private MXProgressbar circlePgsBar;
    private int pgbType;
    private ImageState uploadState;

    private boolean isRunning = false;

    public MXUploadImageView(Context context) {
        super(context);
        initParams(context, null, 0);
    }

    public MXUploadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams(context, attrs, 0);
    }

    public MXUploadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs, defStyleAttr);
    }

    private void initParams(Context context, AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MXUploadImageView);
        if(typedArray != null){
            pgbType = typedArray.getInt(R.styleable.MXUploadImageView_pgbType, -1);
        }
        LayoutInflater.from(context).inflate(R.layout.mxupload_imageview_layout, this, true);
        linePgsBar = (ProgressBar) findViewById(R.id.pgb_line);
        circlePgsBar = (MXProgressbar) findViewById(R.id.pgb_circle);
        switch (pgbType){
            case 0:
                linePgsBar.setVisibility(View.VISIBLE);
                break;
            case 1:
                circlePgsBar.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setProgress(int progress){
        switch (pgbType){
            case 0:
                linePgsBar.setProgress(progress);
                break;
            case 1:
                circlePgsBar.setProgress(progress);
                break;
            default:
                break;
        }
    }


    public boolean isRunning(){
        return circlePgsBar.isRunning();
    }
}
