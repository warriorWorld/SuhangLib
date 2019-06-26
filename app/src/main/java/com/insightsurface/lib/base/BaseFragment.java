package com.insightsurface.lib.base;/**
 * Created by Administrator on 2016/10/26.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.insightsurface.lib.widget.toast.EasyToast;

import java.util.HashMap;


/**
 * 作者：苏航 on 2016/10/26 14:46
 * 邮箱：772192594@qq.com
 */
public class BaseFragment extends Fragment {
    protected EasyToast baseToast;

    public String getFragmentTag() {
        return getClass().getSimpleName();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseToast = new EasyToast(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    protected void doStatistics(String eventName) {
        doStatistics(eventName, new HashMap<String, String>());
    }

    //埋点
    protected void doStatistics(String eventName, HashMap<String, String> params) {
    }
}
