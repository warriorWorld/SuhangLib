package com.insightsurface.lib.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageUtil {
    /**
     * 鏃嬭浆Bitmap
     *
     * @param b
     * @param rotateDegree
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) rotateDegree);
        Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
        return rotaBitmap;
    }

    /**
     * Drawable转化为Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * uri到bitmap
     *
     * @param uri
     * @return
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            Log.e("[Android]", e.getMessage());
            Log.e("[Android]", "目录为：" + uri);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 鍔犺浇鏈湴鍥剧墖 http://bbs.3gstdy.com
     *
     * @param url
     * @return
     */
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

    /**
     * 鍘嬬缉鍥剧墖鍒版寚瀹氬ぇ灏�
     *
     * @param bitMap
     * @param maxSize 鍥剧墖鍏佽鏈�澶х┖闂�,鍗曚綅:kb
     * @return
     */
    public static Bitmap imageZoom(Bitmap bitMap, double maxSize) {
        double bitmapSize = getBitmapSize(bitMap);
        // 鍒ゆ柇bitmap鍗犵敤绌洪棿鏄惁澶т簬鍏佽鏈�澶х┖闂� 濡傛灉澶т簬鍒欏帇缂� 灏忎簬鍒欎笉鍘嬬缉
        while (bitmapSize > maxSize) {
            // 鑾峰彇bitmap澶у皬 鏄厑璁告渶澶уぇ灏忕殑澶氬皯鍊�
            double i = bitmapSize / maxSize;
            // 寮�濮嬪帇缂� 姝ゅ鐢ㄥ埌骞虫柟璺� 灏嗗甯﹀拰楂樺害鍘嬬缉鎺夊搴旂殑骞虫柟鏍瑰��
            // 淇濇寔鍒诲害鍜岄珮搴﹀拰鍘焍itmap姣旂巼涓�鑷达紝鍘嬬缉鍚庝篃杈惧埌浜嗘渶澶уぇ灏忓崰鐢ㄧ┖闂寸殑澶у皬
            bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i), bitMap.getHeight() / Math.sqrt(i));
            bitmapSize = getBitmapSize(bitMap);
        }
        return bitMap;
    }

    /**
     * 鑾峰彇鍥剧墖澶у皬
     *
     * @return 鍗曚綅:kb.
     */
    private static double getBitmapSize(Bitmap bitMap) {
        // 灏哹itmap鏀捐嚦鏁扮粍涓紝鎰忓湪bitmap鐨勫ぇ灏忥紙涓庡疄闄呰鍙栫殑鍘熸枃浠惰澶э級
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        // 灏嗗瓧鑺傛崲鎴怟B
        double mid = b.length / 1024;
        return mid;
    }

    /**
     * 浠嶴D鍗′腑鑾峰彇鍥剧墖鐨勬柟寮�,闇�瑕佽鍙朣D鍗℃潈闄�
     *
     * @param fileName
     * @return
     */
    public static Bitmap getImageFromSDFile(String fileName) {
        Bitmap image = null;
        File file = new File(fileName);
        if (file.exists()) {
            image = BitmapFactory.decodeFile(fileName);
        }
        return image;

    }

    public static Bitmap downloadImageFromNetwork(String imageUrl) {
        Bitmap bitmap = null;
        try {
            InputStream is = new URL(imageUrl).openStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {

        }
        return bitmap;
    }

    /**
     * 浠嶢ssets涓鍙栧浘鐗�
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 鑾峰彇鍥剧墖澶у皬,鏇寸簿纭�
     *
     * @param bitmap
     * @return
     */
    private static double getBitmapSize2(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount() / 1024;
        }
        return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        // return bitmap.getWidth() * bitmap.getHeight() / 1024;
    }

    public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
        // 鑾峰彇杩欎釜鍥剧墖鐨勫鍜岄珮
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 鍒涘缓鎿嶄綔鍥剧墖鐢ㄧ殑matrix瀵硅薄
        Matrix matrix = new Matrix();
        // 璁＄畻瀹介珮缂╂斁鐜�
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缂╂斁鍥剧墖鍔ㄤ綔
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 瀛楃涓茶浆鎹㈡垚Bitmap
     *
     * @param string
     * @return
     */
    public static Bitmap stringtoBitmap(String string) {
        // 灏嗗瓧绗︿覆杞崲鎴怋itmap绫诲瀷
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Bitmap杞崲鎴愬瓧绗︿覆
     *
     * @param bitmap
     * @return
     */
    public static String bitmaptoString(Bitmap bitmap) {
        if (null == bitmap)
            return null;
        // 灏咮itmap杞崲鎴愬瓧绗︿覆
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    public static String bitmaptoString(String path) {
        if (TextUtils.isEmpty(path))
            return null;
        return bitmaptoString(new File(path));
    }

    public static String bitmaptoString(File file) {
        if (null == file || !file.exists()) {
            return null;
        }
        FileInputStream in;
        String data = null;
        try {
            in = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length() + 100];
            int length = in.read(buffer);
            data = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
