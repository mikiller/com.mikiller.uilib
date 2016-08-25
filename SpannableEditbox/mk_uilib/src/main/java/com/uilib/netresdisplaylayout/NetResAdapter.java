package com.uilib.netresdisplaylayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.uilib.R;
import com.uilib.uploadimageview.MXProgressImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Mikiller on 2016/8/24.
 */
public class NetResAdapter extends RecyclerView.Adapter<NetResAdapter.ResViewHolder> {
    Context mContext;
    List<String> data;
    int itemWidth;

    public NetResAdapter(Context mContext, List<String> data) {
        this.data = data;
        this.mContext = mContext;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    @Override
    public ResViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.net_res_item_layout, parent, false);
        ResViewHolder viewHolder = new ResViewHolder(itemWidth, itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ResViewHolder holder, int position) {
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final int pos = holder.getLayoutPosition();
                    URL url = new URL(data.get(pos));
                    final Bitmap bmp = BitmapFactory.decodeStream(url.openStream());
                    holder.iv_pic.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.iv_pic.setImageBitmap(bmp);
//                            NetResAdapter.this.notifyItemChanged(pos);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class ResViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_pic;
        public ResViewHolder(int itemWidth, View itemView) {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if(lp == null){
                lp = new RecyclerView.LayoutParams(itemWidth, itemWidth);
            }
            lp.width = itemWidth;
            lp.height = itemWidth;
            itemView.setLayoutParams(lp);
        }
    }
}
