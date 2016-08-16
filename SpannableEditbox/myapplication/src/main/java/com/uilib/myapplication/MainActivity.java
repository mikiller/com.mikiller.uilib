package com.uilib.myapplication;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Message;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.uilib.checkabletag.CheckableTag;
import com.uilib.mxfloatactbutton.MXFloatActButton;
import com.uilib.mxflowlayout.MXFlowLayout;
import com.uilib.mxselectreslayout.MXSelectResLayout;
import com.uilib.uploadimageview.MXProgressImageView;
import com.uilib.utils.DisplayUtil;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    //    ProgressBar linePgb;
//    MXProgressbar circlePgb;
    MXSelectResLayout ll_photo;
    MXProgressImageView imageView;
    MHandler handler;

    MXFlowLayout fl_tags;
    MXFloatActButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final SpannableEditbox edt = (SpannableEditbox) findViewById(R.id.edt);
//
//        ((InputRelativeLayout)findViewById(R.id.inputlayout)).setKeyboardStateListener(new KeyboardStateListener() {
//            @Override
//            public void onKeyboardShown() {
//                edt.showToolbar();
//            }
//
//            @Override
//            public void onKeyboardHiden() {
//                edt.hideToolbar();
//            }
//        });
//
//        edt.focus();
//        initPgsImageViewLayout();

        initLl_content();

        initFlowLayout();

        initFloatActButton();

    }

//    private void initPgsImageViewLayout(){
//        handler = new MHandler();
//        ll_photo = (LinearLayout) findViewById(R.id.ll_photo);
//        int screenWidth = DisplayUtil.getScreenWidth(this);
//        LinearLayout.LayoutParams lp;
//        for (int i = 0; i < 4; i++) {
//            MXProgressImageView uploadImageView = new MXProgressImageView(this);
//            lp = (LinearLayout.LayoutParams) uploadImageView.getLayoutParams();
//            if(lp == null)
//                lp = new LinearLayout.LayoutParams(screenWidth / 4, screenWidth / 4, 1);
//            lp.setMargins(5, 10, 5, 10);
//            uploadImageView.setLayoutParams(lp);
//            uploadImageView.setTag(i);
//            uploadImageView.setPgbType(MXProgressImageView.CIRCLE);
//            uploadImageView.setUploadState(MXProgressImageView.ImageState.STOP);
//            uploadImageView.setBgImage(R.mipmap.aa);
//            uploadImageView.setRadio(20);
//            ll_photo.addView(uploadImageView);
//        }
//
//        for (int i = 0; i < 4; i++) {
//            final MXProgressImageView uiv = (MXProgressImageView) ll_photo.getChildAt(i);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    long sleepTime = 1;
//                    for (int i = 0; i < 101; i++) {
//                        if (uiv.isRunning()) {
//                            handler.obtainMessage(1, i, (Integer) uiv.getTag()).sendToTarget();
//                            sleepTime++;
//                        } else {
//                            i--;
//                        }
//                        try {
//                            Thread.sleep(sleepTime);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }).start();
//        }
//    }

    private void initLl_content(){
        ll_photo = (MXSelectResLayout) findViewById(R.id.ll_photo);
        List<String> imglist = new ArrayList<>();
        imglist.add("/storage/emulated/0/report_clue/photo/20160804_132447.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160804_102838.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160804_102719.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160803_154419.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160802_173954.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160802_171224.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160802_170228.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160802_170105.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160802_170035.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160802_165910.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160802_165702.jpg");
        imglist.add("/storage/emulated/0/report_clue/photo/20160802_165418.jpg");
        ll_photo.setImgList(imglist);
        ll_photo.setItemWidth(DisplayUtil.getScreenWidth(this) / 4 + 10);
    }

    private void initFlowLayout(){
        fl_tags = (MXFlowLayout) findViewById(R.id.fl_tags);
//        for(int i = 0; i < 8; i++){
//            final CheckableTag tag = new CheckableTag(this);
//            tag.setNeedClose(true);
//            tag.setText("TEST DESK" + i);
//            tag.setListener(new CheckableTag.onCloseListener() {
//                @Override
//                public void onClose(View v) {
//                    fl_tags.removeView(v);
//                }
//            });
//            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(4,4,4,4);
//            tag.setLayoutParams(lp);
//            fl_tags.addView(tag);
//        }

    }

    int num = 0;
    private void initFloatActButton(){
        fab = (MXFloatActButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setSrcText(String.valueOf(num++));
                fab.setSelected(!fab.isSelected());
                if(fab.isSelected()){
                    fab.setTextColor(getResources().getColor(R.color.tagTextBlue));
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                }else{
                    fab.setTextColor(Color.WHITE);
                    fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tagTextBlue)));
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.play));
                }

            }
        });
    }

    class ViewStateListener implements MXProgressImageView.onViewStateListener{

        @Override
        public void onStop() {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onSuccess() {

        }
    }

    class MHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(msg.arg2 ==2 && msg.arg1 > 30){
                        ((MXProgressImageView)ll_photo.getChildAt(msg.arg2)).setProgress(-1);
                    }else
                        ((MXProgressImageView)ll_photo.getChildAt(msg.arg2)).setProgress(msg.arg1);
                    break;
            }

        }
    }
}
