package com.insightsurface.lib.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BitmapUtils {

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 720, 1280);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);

    }

    // 计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    /**
     * 压缩图片
     *
     * @param bitmap    被压缩的图片
     * @param sizeLimit 大小限制(KB)
     * @return 压缩后的图片
     */
    public static Bitmap compressBitmap(Bitmap bitmap, long sizeLimit) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        Logger.d("imgSize" + baos.toByteArray().length);
        // 循环判断压缩后图片是否超过限制大小
        while (baos.toByteArray().length / 1024 > sizeLimit) {
            // 清空baos
            baos.reset();
            quality -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            Logger.d("imgSize" + baos.toByteArray().length);
        }

        Bitmap newBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()), null, null);

        return newBitmap;
    }

    // 把bitmap转换成String
    public static String bitmapToString(String filePath) {

        Bitmap bm = getSmallBitmap(filePath);

        return bitmapToString(bm);
    }

    /**
     * 把bitmap转换成String
     * @param filePath
     * @param sizeLimit 单位KB
     * @return
     */
    public static String bitmapToString(String filePath, long sizeLimit) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bp = BitmapFactory.decodeFile(filePath, options);
        bp = BitmapUtils.compressBitmap(bp, sizeLimit);
        return bitmapToString(bp);
    }

    public static Bitmap getLoacalBitmap(String url) {
        if (TextUtils.isEmpty(url))
            return null;
        try {
            // BitmapFactory.Options bitmapOptions = new
            // BitmapFactory.Options();
            // bitmapOptions.inSampleSize = 6;
            FileInputStream fis = new FileInputStream(url);
            // return BitmapFactory.decodeStream(fis, null, bitmapOptions);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String bitmapToString(Bitmap bm) {
        return bitmapToString(bm, true);
    }

    public static String bitmapToString(Bitmap bm, boolean needRecycle) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        Logger.d("upload img size ==" + b.length);

        if (needRecycle && bm != null && !bm.isRecycled()) {
            bm.recycle();
        }

        return Base64.encodeToString(b, Base64.DEFAULT);

    }

}
