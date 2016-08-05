package com.uilib.uploadimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uilib.BitmapUtils;
import com.uilib.R;
import com.uilib.mxprogressbar.MXProgressbar;

import java.io.File;

/**
 * Created by Mikiller on 2016/8/4.
 */
public class MXUploadImageView extends RelativeLayout implements View.OnClickListener{
    public enum ImageState{
        STOP, PAUSE, START, FAILED, SUCCESS
    }
    public final static int LINE = 0;
    public final static int CIRCLE = 1;

    private Context mContext;
    private ProgressBar linePgsBar;
    private MXProgressbar circlePgsBar;
    private View mask;
    private TextView tv_wait;
    private LinearLayout ll_refresh;
    private int pgbType;
    private int bgImage;
    private Bitmap bgBmp;
    private int radio;

    private String filePath = null;

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
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MXUploadImageView);
        if(typedArray != null){
            pgbType = typedArray.getInt(R.styleable.MXUploadImageView_pgbType, -1);
            bgImage = typedArray.getResourceId(R.styleable.MXUploadImageView_bgImage, NO_ID);
            radio = (int) typedArray.getDimension(R.styleable.MXUploadImageView_radio, 0f);
        }
        setWillNotDraw(false);
        LayoutInflater.from(context).inflate(R.layout.mxupload_imageview_layout, this, true);
        linePgsBar = (ProgressBar) findViewById(R.id.pgb_line);
        circlePgsBar = (MXProgressbar) findViewById(R.id.pgb_circle);
        mask = findViewById(R.id.mask);
        tv_wait = (TextView) findViewById(R.id.tv_wait);
        tv_wait.setOnClickListener(this);
        ll_refresh = (LinearLayout) findViewById(R.id.ll_refresh);
        ll_refresh.setOnClickListener(this);
    }

    public int getPgbType() {
        return pgbType;
    }

    public void setPgbType(int pgbType) {
        this.pgbType = pgbType;
    }

    public int getBgImage() {
        return bgImage;
    }

    public void setBgImage(int bgImage) {
        this.bgImage = bgImage;
    }

    public Bitmap getBgBmp() {
        return bgBmp;
    }

    public void setBgBmp(Bitmap bgBmp) {
        this.bgBmp = bgBmp;
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ImageState getUploadState() {
        return uploadState;
    }

    public void setUploadState(ImageState uploadState) {
        this.uploadState = uploadState;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        switch (pgbType){
            case 0:
                linePgsBar.setVisibility((uploadState == ImageState.START || uploadState == ImageState.PAUSE) ? View.VISIBLE : View.GONE);
                break;
            case 1:
                circlePgsBar.setVisibility((uploadState == ImageState.START || uploadState == ImageState.PAUSE) ? View.VISIBLE : View.GONE);
                break;
            default:
                break;
        }

        tv_wait.setVisibility(uploadState == ImageState.STOP ? View.VISIBLE : View.GONE);
        ll_refresh.setVisibility(uploadState == ImageState.FAILED ? View.VISIBLE : View.GONE);

        GradientDrawable gd = (GradientDrawable) mask.getBackground();
        gd.setCornerRadius(radio);
        mask.setBackgroundDrawable(gd);

        if(bgImage != NO_ID && bgBmp == null) {
            bgBmp = BitmapUtils.decodeSampleBmpFromRes(mContext.getResources(), bgImage, getWidth(), getHeight());
        }else if(filePath != null && bgBmp == null){
            bgBmp = BitmapUtils.decodeSampleBmpFromFile(filePath, getWidth(), getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bgBmp != null) {
            if(getWidth() >= bgBmp.getWidth() && getHeight() >= bgBmp.getHeight()){
                Matrix matrix = BitmapUtils.getScaleMatrix(bgBmp.getWidth(), bgBmp.getHeight(), getWidth(), getHeight());
                canvas.drawBitmap(BitmapUtils.drawRoundBmp(bgBmp, radio), matrix, null);
                return;
            }

            if(bgBmp.getWidth() < getWidth() || bgBmp.getHeight() < getHeight()){
                bgBmp = Bitmap.createScaledBitmap(bgBmp,getWidth(), getHeight(), false);
            }
            canvas.drawBitmap(BitmapUtils.drawRoundBmp(BitmapUtils.getCenterSquareBmp(bgBmp, getWidth(), getHeight()), radio), 0, 0, null);
        }
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_refresh){

        }else if(v.getId() == R.id.tv_wait){

        }
    }
}
