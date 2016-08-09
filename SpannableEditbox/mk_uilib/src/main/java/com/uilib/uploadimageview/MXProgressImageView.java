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

import com.uilib.utils.BitmapUtils;
import com.uilib.R;
import com.uilib.mxprogressbar.MXProgressbar;

/**
 * Created by Mikiller on 2016/8/4.
 */
public class MXProgressImageView extends RelativeLayout implements View.OnClickListener{
    public enum ImageState{
        STOP, PAUSE, START, FAILED, SUCCESS
    }
    public final static int LINE = 0;
    public final static int CIRCLE = 1;

    private RelativeLayout rl_background;
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

    private onViewStateListener listener;

    public MXProgressImageView(Context context) {
        super(context);
        initParams(context, null, 0);
    }

    public MXProgressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams(context, attrs, 0);
    }

    public MXProgressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs, defStyleAttr);
    }

    private void initParams(Context context, AttributeSet attrs, int defStyleAttr){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MXProgressImageView);
        if(typedArray != null){
            pgbType = typedArray.getInt(R.styleable.MXProgressImageView_pgbType, -1);
            bgImage = typedArray.getResourceId(R.styleable.MXProgressImageView_bgImage, NO_ID);
            radio = (int) typedArray.getDimension(R.styleable.MXProgressImageView_radio, 0f);
        }
        setWillNotDraw(false);
        LayoutInflater.from(context).inflate(R.layout.mxupload_imageview_layout, this, true);
        rl_background = (RelativeLayout) findViewById(R.id.rl_background);
        rl_background.setOnClickListener(this);
        linePgsBar = (ProgressBar) findViewById(R.id.pgb_line);
        circlePgsBar = (MXProgressbar) findViewById(R.id.pgb_circle);
        circlePgsBar.setOnClickListener(this);
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

    public onViewStateListener getListener() {
        return listener;
    }

    public void setListener(onViewStateListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setPgsBarVisibility();

        tv_wait.setVisibility(uploadState == ImageState.STOP ? View.VISIBLE : View.GONE);
        ll_refresh.setVisibility(uploadState == ImageState.FAILED ? View.VISIBLE : View.GONE);

        GradientDrawable gd = (GradientDrawable) mask.getBackground();
        gd.setCornerRadius(radio);
        mask.setBackgroundDrawable(gd);

        if(bgImage != NO_ID && bgBmp == null) {
            bgBmp = BitmapUtils.decodeSampleBmpFromRes(getResources(), bgImage, getWidth(), getHeight());
        }else if(filePath != null && bgBmp == null){
            bgBmp = BitmapUtils.decodeSampleBmpFromFile(filePath, getWidth(), getHeight());
        }
    }

    private void setPgsBarVisibility(){
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
        if(progress == 100){
            onSuccess();
        }else if(progress == -1){
            onFailed();
        }
    }


    public boolean isRunning(){
        return circlePgsBar.isRunning();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_refresh){
            onRestart(v);
        }else if(v.getId() == R.id.tv_wait){
            onStart(v);
        }else if(v.getId() == R.id.pgb_circle){
            circlePgsBar.onSwitchClicked(v);
            if(isRunning()){
                onContinue();
            }else{
                onPause();
            }
        }else if(v.getId() == R.id.rl_background){
            //show preview
        }
    }

    protected void onStop(){
        uploadState = ImageState.STOP;
        tv_wait.setVisibility(View.VISIBLE);
        mask.setVisibility(View.VISIBLE);
        setProgress(0);
        circlePgsBar.setRunning(false);
        setPgsBarVisibility();
        ll_refresh.setVisibility(View.GONE);
        if(listener != null)
            listener.onStop();
    }

    protected void onStart(View v){
        uploadState = ImageState.START;
        v.setVisibility(View.GONE);
        setPgsBarVisibility();
        circlePgsBar.setRunning(true);
        if(listener != null)
            listener.onStart();
    }

    protected void onRestart(View v){
        setProgress(0);
        onStart(v);
    }

    protected void onPause(){
        uploadState = ImageState.PAUSE;
        if(listener != null)
            listener.onPause();
    }

    protected void onContinue()
    {
        uploadState = ImageState.START;
        if(listener != null)
            listener.onStart();
    }
    protected void onFailed(){
        uploadState = ImageState.FAILED;
        ll_refresh.setVisibility(View.VISIBLE);
        setProgress(0);
        setPgsBarVisibility();
        if(listener != null){
            listener.onFailed();
        }
    }

    protected void onSuccess(){
        uploadState = ImageState.SUCCESS;
        mask.setVisibility(View.GONE);
        setPgsBarVisibility();
        if(listener != null)
            listener.onSuccess();
    }

    public interface onViewStateListener{
        void onStop();
        void onStart();
        void onPause();
        void onFailed();
        void onSuccess();
    }
}
