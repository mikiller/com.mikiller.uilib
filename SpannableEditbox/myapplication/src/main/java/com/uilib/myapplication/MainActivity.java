package com.uilib.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.uilib.BitmapUtils;
import com.uilib.mxprogressbar.MXProgressbar;
import com.uilib.uploadimageview.MXUploadImageView;

import android.os.Handler;


public class MainActivity extends Activity {
    //    ProgressBar linePgb;
//    MXProgressbar circlePgb;
    LinearLayout ll_photo;
    MXUploadImageView imageView;
    MHandler handler;

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
        handler = new MHandler();
        ll_photo = (LinearLayout) findViewById(R.id.ll_photo);
//         linePgb = (ProgressBar) findViewById(R.id.pgb_line);
//        circlePgb = (MXProgressbar) findViewById(R.id.pgb_circle);
//        imageView = (MXUploadImageView) findViewById(R.id.iv_upload);
        int screenWidth = DisplayUtil.getScreenWidth(this);
        LinearLayout.LayoutParams lp;
        for (int i = 0; i < 4; i++) {
            MXUploadImageView uploadImageView = new MXUploadImageView(this);
            lp = (LinearLayout.LayoutParams) uploadImageView.getLayoutParams();
            if(lp == null)
                lp = new LinearLayout.LayoutParams(screenWidth / 4, screenWidth / 4, 1);
            lp.setMargins(5, 10, 5, 10);
            uploadImageView.setLayoutParams(lp);
            uploadImageView.setTag(i);
            uploadImageView.setPgbType(MXUploadImageView.CIRCLE);
            uploadImageView.setUploadState(MXUploadImageView.ImageState.STOP);
            uploadImageView.setBgImage(R.mipmap.aa);
            uploadImageView.setRadio(20);
            ll_photo.addView(uploadImageView);
        }

        for (int i = 0; i < 4; i++) {
            final MXUploadImageView uiv = (MXUploadImageView) ll_photo.getChildAt(i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long sleepTime = 1;
                    for (int i = 0; i < 101; i++) {
                        if (uiv.isRunning()) {
                            handler.obtainMessage(1, i, (Integer) uiv.getTag()).sendToTarget();
                            if (i == 100)
                                i = 0;
                            sleepTime++;
                        } else {
                            i--;
                        }
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }


    }


    class MHandler extends Handler {
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ((MXUploadImageView)ll_photo.getChildAt(msg.arg2)).setProgress(msg.arg1);
                    break;
            }

        }
    }
}
