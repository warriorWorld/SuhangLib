package com.insightsurface.lib.business.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.insightsurface.lib.R;
import com.insightsurface.lib.base.BaseActivity;
import com.insightsurface.lib.utils.MatchStringUtil;
import com.insightsurface.lib.utils.SingleLoadBarUtil;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView crossIv;
    private EditText userIdEt;
    private EditText emailEt;
    private EditText userPsdEt;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initUI() {
        super.initUI();
        crossIv = (ImageView) findViewById(R.id.cross_iv);
        userIdEt = (EditText) findViewById(R.id.user_id_et);
        emailEt = (EditText) findViewById(R.id.email_et);
        userPsdEt = (EditText) findViewById(R.id.user_psd_et);
        registerBtn = (Button) findViewById(R.id.register_btn);
        crossIv.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        hideBaseTopBar();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(userIdEt.getText().toString())) {
            baseToast.showToast("请输入用户名!");
            return false;
        }
        if (TextUtils.isEmpty(emailEt.getText().toString())) {
            baseToast.showToast("请输入邮箱地址!");
            return false;
        }
        if (!MatchStringUtil.isEmail(emailEt.getText().toString())) {
            baseToast.showToast("请输入正确的邮箱地址!");
            return false;
        }
        if (TextUtils.isEmpty(userPsdEt.getText().toString())) {
            baseToast.showToast("请输入密码!");
            return false;
        }
        return true;
    }


    private void doRegister() {
        SingleLoadBarUtil.getInstance().showLoadBar(RegisterActivity.this);
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(userIdEt.getText().toString());// 设置用户名
        user.setPassword(userPsdEt.getText().toString());// 设置密码
        user.setEmail(emailEt.getText().toString());//设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                SingleLoadBarUtil.getInstance().dismissLoadBar();
                if (e == null) {
                    // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                    doNext();
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    baseToast.showToast(e.getMessage());
                }
            }
        });
    }

    private void doNext() {
        baseToast.showToast("注册成功!");
        this.finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cross_iv) {
            RegisterActivity.this.finish();
        } else if (i == R.id.register_btn) {
            if (checkData()) {
                doRegister();
            }
        }
    }
}
