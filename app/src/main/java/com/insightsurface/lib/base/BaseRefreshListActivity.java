package com.insightsurface.lib.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.insightsurface.lib.R;

/**
 * 个人信息页
 */
public abstract class BaseRefreshListActivity extends BaseActivity implements OnRefreshListener,
        OnLoadMoreListener {
    protected RecyclerView refreshRcv;
    protected SwipeToLoadLayout swipeToLoadLayout;
    protected int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateInit();
        doGetData();
    }

    protected abstract void onCreateInit();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_only_refresh_recycler;
    }

    protected abstract void doGetData();

    @Override
    protected void initUI() {
        super.initUI();
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        refreshRcv = (RecyclerView) findViewById(R.id.swipe_target);
        refreshRcv.setLayoutManager
                (new LinearLayoutManager
                        (this, LinearLayoutManager.VERTICAL, false));
        refreshRcv.setFocusableInTouchMode(false);
        refreshRcv.setFocusable(false);
        refreshRcv.setHasFixedSize(true);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    protected abstract void initRec();

    protected void noMoreData() {
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
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
