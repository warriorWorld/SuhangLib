package com.insightsurface.lib.widget.dialog;/**
 * Created by Administrator on 2016/11/4.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.insightsurface.lib.R;
import com.insightsurface.lib.listener.OnDialogClickListener;


/**
 * 作者：苏航 on 2016/11/4 11:08
 * 邮箱：772192594@qq.com
 */
public class NormalDialog extends Dialog {
    private Context context;
    private TextView dialogTitle;
    private TextView dialogMessage;
    private Button okBtn;
    private Button cancelBtn;

    private OnDialogClickListener mOnDialogClickListener;

    public NormalDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        init();

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager wm = ((Activity) context).getWindowManager();
        Display d = wm.getDefaultDisplay();
        // lp.height = (int) (d.getHeight() * 0.4);
        lp.width = (int) (d.getWidth() * 0.9);
        // window.setGravity(Gravity.LEFT | Gravity.TOP);
        window.setGravity(Gravity.CENTER);
//        window.getDecorView().setPadding(0, 0, 0, 0);
        // lp.x = 100;
        // lp.y = 100;
        // lp.height = 30;
        // lp.width = 20;
        window.setAttributes(lp);
    }

    protected int getLayoutId() {
        return R.layout.dialog_normal;
    }

    private void init() {
        dialogTitle = (TextView) findViewById(R.id.dialog_title);
        dialogMessage = (TextView) findViewById(R.id.dialog_message);
        okBtn = (Button) findViewById(R.id.ok_btn);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);

        dialogTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != mOnDialogClickListener) {
                    mOnDialogClickListener.onCancelClick();
                }
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (null != mOnDialogClickListener) {
                    mOnDialogClickListener.onOkClick();
                }
            }
        });
    }

    public void setTitle(String title) {
        setTitle(new SpannableString(title));
    }

    public void setTitle(SpannableString title) {
        try {
            dialogTitle.setVisibility(View.VISIBLE);
            dialogTitle.setText(title);
        } catch (NullPointerException e) {
        }
    }

    public void setMessage(String message) {
        dialogMessage.setVisibility(View.VISIBLE);
        dialogMessage.setText(Html.fromHtml(message));
    }

    public void setMessage(CharSequence text) {
        dialogMessage.setText(text);
    }

    public void setMessage(SpannableString message) {
        dialogMessage.setVisibility(View.VISIBLE);
        dialogMessage.setText(message);
    }

    public void setMessageColor(int color) {
        dialogMessage.setTextColor(color);
    }

    public void setTitleColor(int color) {
        dialogTitle.setTextColor(color);
    }

    public void setMessageLeft(boolean left) {
        if (left) {
            dialogMessage.setGravity(Gravity.LEFT);
        } else {
            dialogMessage.setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }

    public void setTitleSize(int size) {
        dialogTitle.setTextSize(size);
    }

    public void setMessageSize(int size) {
        dialogMessage.setTextSize(size);
    }

    public void setTitleBold() {
        TextPaint tp = dialogTitle.getPaint();
        tp.setFakeBoldText(true);
    }

    public void setOkBtnText(String text) {
        okBtn.setVisibility(View.VISIBLE);
        okBtn.setText(text);
    }

    public void setCancelBtnText(String text) {
        cancelBtn.setVisibility(View.VISIBLE);
        cancelBtn.setText(text);
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        mOnDialogClickListener = onDialogClickListener;
    }
}
