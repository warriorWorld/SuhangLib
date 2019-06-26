package com.insightsurface.lib.business.web;

import android.os.Bundle;

import com.insightsurface.lib.R;
import com.insightsurface.lib.base.BaseFragment;
import com.insightsurface.lib.base.FragmentContainerActivity;


/**
 * 个人信息页
 */
public class WebActivity extends FragmentContainerActivity {
    protected WebFragment mWebFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideBaseTopBar();
        setStatusTextColor();
    }

    @Override
    protected BaseFragment getFragment() {
        return mWebFragment;
    }

    @Override
    protected String getTopBarTitle() {
        return getResources().getString(R.string.app_name);
    }

    @Override
    protected void initFragment() {
        mWebFragment = new WebFragment();
    }
}
