package com.insightsurface.lib.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.insightsurface.lib.R;

/**
 * 个人信息页
 */
public abstract class BaseRefreshActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    protected SwipeRefreshLayout swipeToLoadLayout;
    protected int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doGetData();
    }

    protected abstract void doGetData();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_only_refresh;
    }

    protected abstract int getContentLayoutId();

    protected abstract int getTopViewId();

    protected abstract int getBottomViewId();

    @Override
    protected void initUI() {
        super.initUI();
        swipeToLoadLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToLoadLayout);

        ViewGroup containerView = (ViewGroup) findViewById(R.id.container_fl);
        LayoutInflater.from(this).inflate(getContentLayoutId(), containerView);
        if (getTopViewId() != 0) {
            ViewGroup topView = (ViewGroup) findViewById(R.id.top_view);
            LayoutInflater.from(this).inflate(getTopViewId(), topView);
            initTopViewUI(topView);
        }
        if (getBottomViewId() != 0) {
            ViewGroup bottomView = (ViewGroup) findViewById(R.id.bottom_view);
            LayoutInflater.from(this).inflate(getBottomViewId(), bottomView);
            initBottomViewUI(bottomView);
        }
        swipeToLoadLayout.setOnRefreshListener(this);
        initTargetUI(containerView);
    }

    protected abstract void initTargetUI(ViewGroup view);

    protected abstract void initTopViewUI(ViewGroup view);

    protected abstract void initBottomViewUI(ViewGroup view);


    protected void noMoreData() {
        swipeToLoadLayout.setRefreshing(false);
    }

    public void onLoadMore() {
        page++;
        doGetData();
    }

    @Override
    public void onRefresh() {
        page = 1;
        doGetData();
    }
}
