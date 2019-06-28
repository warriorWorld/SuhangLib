package com.insightsurface.lib.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.insightsurface.lib.utils.PropertiesUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by Administrator on 2017/7/19.
 */

public class Configure {
    final public static String DOWNLOAD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/" + "Download";
    //数字随便写的  权限request code
    final public static int PERMISSION_CAMERA_REQUST_CODE = 8021;
    final public static int PERMISSION_LOCATION_REQUST_CODE = 8022;
    final public static int PERMISSION_FILE_REQUST_CODE = 8023;
    final public static int PERMISSION_READ_PHONE_STATE_REQUST_CODE = 8024;
    public static final DisplayImageOptions normalImageOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
//            .showImageOnLoading(R.drawable.spinner_1)
//            .showImageOnFail(R.drawable.spinner_1)
            .build();

    public static boolean isTest(Context context) {
        return Boolean.valueOf(PropertiesUtil.getInstance(context).getProperty("IS_TEST"));
    }

    public static boolean getLeanCloudId(Context context) {
        return Boolean.valueOf(PropertiesUtil.getInstance(context).getProperty("LEANCLOUD_ID"));
    }

    public static boolean getLeanCloudKey(Context context) {
        return Boolean.valueOf(PropertiesUtil.getInstance(context).getProperty("LEANCLOUD_KEY"));
    }
}
