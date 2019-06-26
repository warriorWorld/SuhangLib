package com.insightsurface.lib.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.insightsurface.lib.R;


/**
 * 出借记录页
 */
public abstract class FragmentContainerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initFragment();
        super.onCreate(savedInstanceState);
    }

    protected abstract BaseFragment getFragment();

    protected abstract String getTopBarTitle();

    protected abstract void initFragment();

    @Override
    protected void initUI() {
        super.initUI();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.base_container, getFragment());
        transaction.commit();

        baseTopBar.setTitle(getTopBarTitle());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != getFragment()) {
            getFragment().onHiddenChanged(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base;
    }
}
