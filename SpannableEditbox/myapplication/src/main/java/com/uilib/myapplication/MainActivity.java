package com.uilib.myapplication;

import android.app.Activity;
import android.os.Message;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.uilib.mxprogressbar.MXProgressbar;
import com.uilib.uploadimageview.MXUploadImageView;

import android.os.Handler;


public class MainActivity extends Activity {
    ProgressBar linePgb;
    MXProgressbar circlePgb;

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
         linePgb = (ProgressBar) findViewById(R.id.pgb_line);
        circlePgb = (MXProgressbar) findViewById(R.id.pgb_circle);
        imageView = (MXUploadImageView) findViewById(R.id.iv_upload);

        new Thread(new Runnable() {
            @Override
            public void run() {
                long sleepTime = 1;
                for (int i = 0; i < 101; i++) {
                    if(circlePgb.isRunning()) {
                        handler.obtainMessage(1, i, 0).sendToTarget();
                        if (i == 100)
                            i = 0;
                    }else{
                        i--;
                    }
                    try {
                        Thread.sleep(sleepTime++);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();




    }


    class MHandler extends Handler{
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what){
                case 1:
                    linePgb.setProgress(msg.arg1);
                    circlePgb.setProgress(msg.arg1);
                    break;
            }

        }
    }
}
