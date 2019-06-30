package com.insightsurface.lib.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.insightsurface.lib.R;
import com.insightsurface.lib.widget.bar.TopBar;

/**
 * 个人信息页
 */
public abstract class BaseRefreshFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    protected TopBar refreshBaseTopbar;
    protected SwipeRefreshLayout swipeToLoadLayout;
    protected int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(getLayoutId(), container, false);
        initUI(v);
        doGetData();
        return v;
    }

    protected int getLayoutId() {
        return R.layout.fragment_only_refresh;
    }

    protected abstract void doGetData();

    protected abstract int getContentLayoutId();

    protected abstract int getTopViewId();

    protected abstract int getBottomViewId();

    protected void initUI(View v) {
        refreshBaseTopbar = (TopBar) v.findViewById(R.id.refresh_frgament_topbar);
        refreshBaseTopbar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onLeftClick() {
                getActivity().finish();
            }

            @Override
            public void onRightClick() {
            }

            @Override
            public void onTitleClick() {
            }
        });
        swipeToLoadLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeToLoadLayout);

        if (getContentLayoutId() != 0) {
            ViewGroup containerView = (ViewGroup) v.findViewById(R.id.container_fl);
            LayoutInflater.from(getActivity()).inflate(getContentLayoutId(), containerView);
            initFrgmentUI(containerView);
        }
        if (getTopViewId() != 0) {
            ViewGroup topView = (ViewGroup) v.findViewById(R.id.top_view);
            LayoutInflater.from(getActivity()).inflate(getTopViewId(), topView);
            initTopViewUI(topView);
        }
        if (getBottomViewId() != 0) {
            ViewGroup bottomView = (ViewGroup) v.findViewById(R.id.bottom_view);
            LayoutInflater.from(getActivity()).inflate(getBottomViewId(), bottomView);
            initBottomViewUI(bottomView);
        }
        swipeToLoadLayout.setOnRefreshListener(this);
    }

    protected abstract void initFrgmentUI(ViewGroup view);

    protected abstract void initTopViewUI(ViewGroup view);

    protected abstract void initBottomViewUI(ViewGroup view);

    protected void hideRefreshTopBar() {
        refreshBaseTopbar.setVisibility(View.GONE);
    }

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
