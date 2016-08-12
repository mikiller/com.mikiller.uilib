package com.uilib.mxselectreslayout;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uilib.R;

/**
 * Created by Mikiller on 2016/8/12.
 */
public class MXSelectResLayout extends LinearLayout {
    private ImageView iv_camera, iv_record, iv_files;
    private TextView tv_upload;
    private RecyclerView rcv_res;

    public MXSelectResLayout(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public MXSelectResLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs, 0);
    }

    public MXSelectResLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr){
        LayoutInflater.from(context).inflate(R.layout.meterial_select_layout, this, true);
        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        iv_record = (ImageView) findViewById(R.id.iv_record);
        iv_files = (ImageView) findViewById(R.id.iv_files);
        tv_upload = (TextView) findViewById(R.id.tv_upload);
        rcv_res = (RecyclerView) findViewById(R.id.rcv_res);

        rcv_res.setLayoutManager(new LinearLayoutManager(context));
        rcv_res.setHasFixedSize(true);

    }
}
