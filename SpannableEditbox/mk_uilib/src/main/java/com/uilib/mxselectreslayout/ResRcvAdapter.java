package com.uilib.mxselectreslayout;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.uilib.R;
import com.uilib.uploadimageview.MXProgressImageView;

import java.util.List;

/**
 * Created by Mikiller on 2016/8/12.
 */
public class ResRcvAdapter extends RecyclerView.Adapter<ResRcvAdapter.ResViewHolder> implements View.OnClickListener{
    Context mContext;
    List<String> imgs;
    onItemClickListener onItemClickListener;

    public ResRcvAdapter.onItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(ResRcvAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ResRcvAdapter(Context context, List<String> imgs) {
        mContext = context;
        this.imgs = imgs;
    }

    @Override
    public ResViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.select_res_item_layout, parent, false);
        ResViewHolder viewHolder = new ResViewHolder(itemView);
        viewHolder.iv_pic.setOnClickListener(this);
        viewHolder.iv_delete.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ResViewHolder holder, int position) {
        holder.iv_pic.setFilePath(imgs.get(position));
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_pic){
            if(onItemClickListener != null)
                onItemClickListener.onPicClick(v, 0, null);
        }else if(v.getId() == R.id.iv_delete){
            if(onItemClickListener != null)
                onItemClickListener.onDelete(0, null);
        }
    }

    public static class ResViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_delete;
        private MXProgressImageView iv_pic;

        public ResViewHolder(View itemView) {
            super(itemView);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            iv_pic = (MXProgressImageView) itemView.findViewById(R.id.iv_pic);
        }
    }

    public static interface onItemClickListener{
        void onPicClick(View v, int position, String filePath);
        void onDelete(int position, String filePath);
    }
}
