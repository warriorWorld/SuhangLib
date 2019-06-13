package com.insightsurface.notebook.utils;

import android.content.Context;
import android.view.WindowManager;

import com.avos.avoscloud.AVException;
import com.insightsurface.notebook.widget.dialog.NormalDialog;

/**
 * Created by Administrator on 2017/7/29.
 */

public class LeanCloundUtil {
    public static boolean handleLeanResult(AVException e) {
        return e == null;
    }

    public static boolean handleLeanResult(Context context, AVException e) {
        if (e == null) {
            return true;
        } else {
            try {
                NormalDialog errorDialog = new NormalDialog(context);
                errorDialog.show();
                errorDialog.setTitle("出错了");
                errorDialog.setMessage(e.getMessage());
            } catch (WindowManager.BadTokenException exception) {
                exception.printStackTrace();
            }
            return false;
        }
    }
}
