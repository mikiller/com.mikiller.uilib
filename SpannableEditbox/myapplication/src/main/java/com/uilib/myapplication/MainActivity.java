package com.uilib.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.uilib.inputlayout.InputLayout;
import com.uilib.spannableeditbox.SpannableEditbox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SpannableEditbox edt = (SpannableEditbox) findViewById(R.id.edt);

        ((InputLayout)findViewById(R.id.inputlayout)).setKeyboardStateListener(new InputLayout.KeyboardStateListener() {
            @Override
            public void onKeyboardShown() {
                edt.showToolbar();
            }

            @Override
            public void onKeyboardHiden() {
                edt.hideToolbar();
            }
        });
    }
}
