package com.uilib.mxselectreslayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.uilib.R;
import com.uilib.uploadimageview.MXProgressImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikiller on 2016/8/12.
 */
public class ResRcvAdapter extends RecyclerView.Adapter<ResRcvAdapter.ResViewHolder> {
    Context mContext;
    List<String> imgs;
    int itemWidth;
    onItemClickListener onItemClickListener;

    public ResRcvAdapter.onItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(ResRcvAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = new ArrayList<>(imgs);
        notifyDataSetChanged();
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public ResRcvAdapter(Context context, List<String> imgs) {
        mContext = context;
        this.imgs = imgs;
    }

    @Override
    public ResViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.select_res_item_layout, parent, false);
        ResViewHolder viewHolder = new ResViewHolder(itemWidth, itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ResViewHolder holder, final int position) {
//        holder.iv_pic.post(new Runnable() {
//            @Override
//            public void run() {
                setIvPicImg(holder, position);
        holder.iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                if(onItemClickListener != null)
                    onItemClickListener.onPicClick(v, pos, imgs.get(pos));
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                if(onItemClickListener != null)
                    onItemClickListener.onDelete(pos, imgs.get(pos));
                imgs.remove(pos);
                notifyItemRemoved(pos);
            }
        });
//            }
//        });

    }

    private void setIvPicImg(ResViewHolder holder, int position){

//        List<String> pathList = new ArrayList<>(imgs);
        if(imgs == null || imgs.size() == 0)
            return;

        String path = imgs.get(position);
            if (path.endsWith(".aac")) {
                holder.iv_pic.setBgImage(R.mipmap.audio);
            } else{
                holder.iv_pic.setFilePath(path);
            }
    }

    private Bitmap getVidioBitmap(String path, int width, int height, int microKind)
    {
        // 定義一個Bitmap對象bitmap；
        Bitmap bitmap = null;

        // ThumbnailUtils類的截取的圖片是保持原始比例的，但是本人發現顯示在ImageView控件上有时候有部分沒顯示出來；
        // 調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
        bitmap = ThumbnailUtils.createVideoThumbnail(path, microKind);

        // 調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
        // 最後一個參數的具體含義我也不太清楚，因為是閉源的；
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        // 放回bitmap对象；
        return bitmap;

    }

    @Override
    public int getItemCount() {
        return imgs == null ? 0 : imgs.size();
    }

    public static class ResViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv_delete;
        private MXProgressImageView iv_pic;

        public ResViewHolder(int itemWidth, View itemView) {
            super(itemView);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            iv_pic = (MXProgressImageView) itemView.findViewById(R.id.iv_pic);
            iv_pic.setUploadState(MXProgressImageView.ImageState.STOP);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if(lp == null){
                lp = new RecyclerView.LayoutParams(itemWidth, itemWidth);
            }
            lp.width = itemWidth;
            lp.height = itemWidth;
            itemView.setLayoutParams(lp);
        }
    }

    public static interface onItemClickListener{
        void onPicClick(View v, int position, String filePath);
        void onDelete(int position, String filePath);
    }
}
