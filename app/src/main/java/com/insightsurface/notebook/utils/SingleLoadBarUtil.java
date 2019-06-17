package com.insightsurface.notebook.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;


import java.util.LinkedList;
import java.util.List;


/**
 * Created by Administrator on 2017/11/23.
 */

public class SingleLoadBarUtil {
    private List<Dialog> loadBarList = new LinkedList<Dialog>();

    private SingleLoadBarUtil() {
    }


    private static volatile SingleLoadBarUtil instance = null;

    public static SingleLoadBarUtil getInstance() {
        if (instance == null) {
            synchronized (SingleLoadBarUtil.class) {
                if (instance == null) {
                    instance = new SingleLoadBarUtil();
                }
            }
        }
        return instance;
    }

    private void addLoadBar(Dialog progressDialog) {
        loadBarList.add(progressDialog);
    }

    private void dismissAll() {
        if (null != loadBarList && loadBarList.size() > 0) {
            for (Dialog dialog : loadBarList) {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            loadBarList.clear();
        }
    }

    private boolean isShowing() {
        return false;
//        if (null != loadBarList && loadBarList.size() > 0) {
//            for (Dialog dialog : loadBarList) {
//                if (null != dialog && dialog.isShowing()) {
//                    return true;
//                }
//            }
//            return false;
//        } else {
//            return false;
//        }
    }

    public void showLoadBar(Context context) {
        try {
            if (isShowing()){
                return;
            }
            ProgressDialog loaderBar = new ProgressDialog(context);
            loaderBar.setTitle("加载中...");
            addLoadBar(loaderBar);
            loaderBar.show();
            loaderBar.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissLoadBar() {
        try {
            dismissAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
