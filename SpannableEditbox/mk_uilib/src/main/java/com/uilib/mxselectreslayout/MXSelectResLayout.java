package com.uilib.mxselectreslayout;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uilib.R;
import com.uilib.utils.DisplayUtil;

import java.util.List;

/**
 * Created by Mikiller on 2016/8/12.
 */
public class MXSelectResLayout extends LinearLayout implements View.OnClickListener{
    private ImageView iv_camera, iv_video, iv_record, iv_files;
    private TextView tv_upload;
    private RecyclerView rcv_res;

    private onToolbarClickListener toolbarClickListener;

    public onToolbarClickListener getToolbarClickListener() {
        return toolbarClickListener;
    }

    public void setToolbarClickListener(onToolbarClickListener toolbarClickListener) {
        this.toolbarClickListener = toolbarClickListener;
    }

    public void setItemClickListener(ResRcvAdapter.onItemClickListener listener){
        ((ResRcvAdapter)rcv_res.getAdapter()).setOnItemClickListener(listener);
    }

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
        iv_camera.setOnClickListener(this);
        iv_video = (ImageView) findViewById(R.id.iv_video);
        iv_video.setOnClickListener(this);
        iv_record = (ImageView) findViewById(R.id.iv_record);
        iv_record.setOnClickListener(this);
        iv_files = (ImageView) findViewById(R.id.iv_files);
        iv_files.setOnClickListener(this);
        tv_upload = (TextView) findViewById(R.id.tv_upload);
        tv_upload.setOnClickListener(this);

        rcv_res = (RecyclerView) findViewById(R.id.rcv_res);
        WrapLinearLayoutManager layoutMgr = new WrapLinearLayoutManager(context);
        layoutMgr.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_res.setLayoutManager(layoutMgr);
        rcv_res.setHasFixedSize(true);
        ResRcvAdapter adapter = new ResRcvAdapter(context, null);
        rcv_res.setAdapter(adapter);
        rcv_res.setItemAnimator(new DefaultItemAnimator());

    }

    public List<String> getImgList(){
        return ((ResRcvAdapter)rcv_res.getAdapter()).getImgs();
    }

    public void setImgList(List<String> list){
        ((ResRcvAdapter)rcv_res.getAdapter()).setImgs(list);
    }

    public void setItemWidth(int width){
        ((ResRcvAdapter)rcv_res.getAdapter()).setItemWidth(width);
    }

    @Override
    public void onClick(View v) {
        if(toolbarClickListener == null)
            return;
        if(v.getId() == R.id.iv_camera){
            toolbarClickListener.onCamera();
        }else if(v.getId() == R.id.iv_video){
            toolbarClickListener.onVideo();
        }else if(v.getId() == R.id.iv_record){
            toolbarClickListener.onRecord();
        }else if(v.getId() == R.id.iv_files){
            toolbarClickListener.onFiles();
        }else if(v.getId() == R.id.tv_upload){
            toolbarClickListener.onUpload();
        }
    }

    public static interface onToolbarClickListener{
        void onCamera();
        void onVideo();
        void onRecord();
        void onFiles();
        void onUpload();
    }
}
