package com.insightsurface.notebook.config;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/7/19.
 */

public class Configure {
    public final static boolean isTest = false;
    //数据库版本号
    public static final int DB_VERSION = 3;
    public final static String DST_FOLDER_NAME = "aNotebook";
    final public static String storagePath = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/" + DST_FOLDER_NAME;
    final public static String WORDS_FOLDER_NAME = "WORDS";
    final public static String WORDS_PATH = storagePath + File.separator + WORDS_FOLDER_NAME;
    final public static String DOWNLOAD_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/" + "notebook";
    //获取正在运行的服务 有的手机获取不到 所以换一种方式
    final public static String YOUDAO = "http://fanyi.youdao.com/openapi.do?keyfrom=mangaeasywa" +
            "tch&key=986400551&type=data&doctype=json&version=1.1&q=";
    // 3DES加密key
    final public static String key = "iq2szojof6x1ckgejwe52urw";
    //数字随便写的  权限request code
    final public static int PERMISSION_CAMERA_REQUST_CODE = 8021;
    final public static int PERMISSION_LOCATION_REQUST_CODE = 8022;
    final public static int PERMISSION_FILE_REQUST_CODE = 8023;
    final public static int PERMISSION_READ_PHONE_STATE_REQUST_CODE = 8024;
}
