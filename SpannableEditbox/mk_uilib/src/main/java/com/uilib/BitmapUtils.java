package com.uilib;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Mikiller on 2016/8/5.
 */
public class BitmapUtils {

    public static Bitmap decodeSampleBmpFromFile(String filePath, int dscWidth, int dscHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = getSampleSize(options, dscWidth, dscHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeSampleBmpFromRes(Resources res, int resId, int dscWidth, int dscHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = getSampleSize(options, dscWidth, dscHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int getSampleSize(BitmapFactory.Options options, float dscWidth, float dscHeight){
        float width = (float) options.outWidth;
        float height = (float) options.outHeight;
        if(dscWidth >= width || dscHeight >= height)
            return 1;
        return Math.round((width > height) ? (height / dscHeight) : (width / dscWidth));
    }

    public static Bitmap getCenterSquareBmp(Bitmap src, int squareWidth, int squareHeight){
        int top, left;
        top = (src.getHeight() - squareHeight) / 2;
        left = (src.getWidth() - squareWidth) / 2;
        Bitmap bmp = Bitmap.createBitmap(src, left, top, squareWidth, squareHeight);
        return bmp;
    }

    public static Matrix getScaleMatrix(float width, float height, float dscWidth, float dscHeight){
        Matrix matrix = new Matrix();
        matrix.preScale(dscWidth / width, dscHeight / height, 0, 0);
        return matrix;
    }

    public static Bitmap drawRoundBmp(Bitmap src, float corner){
        if(src == null)
            return null;
        Bitmap output = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0,0,src.getWidth(), src.getHeight());
        RectF rectF = new RectF(0,0,src.getWidth(), src.getHeight());
        canvas.drawRoundRect(rectF, corner, corner, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, rect, rect, paint);
        src.recycle();
        return output;
    }
}
