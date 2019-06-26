package com.insightsurface.lib.business.web;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


import com.insightsurface.lib.R;
import com.insightsurface.lib.base.BaseRefreshFragment;
import com.insightsurface.lib.config.Configure;
import com.insightsurface.lib.listener.OnAllVersionScrollChangeListener;
import com.insightsurface.lib.listener.OnDialogClickListener;
import com.insightsurface.lib.listener.OnEditResultListener;
import com.insightsurface.lib.listener.OnShowFileChooserListener;
import com.insightsurface.lib.listener.OnUrlChangeListener;
import com.insightsurface.lib.utils.BaseParameterUtil;
import com.insightsurface.lib.utils.MatchStringUtil;
import com.insightsurface.lib.widget.bar.TopBar;
import com.insightsurface.lib.widget.dialog.EditDialog;
import com.insightsurface.lib.widget.dialog.NormalDialog;

/**
 * Created by Administrator on 2018/9/4.
 */
public class WebFragment extends BaseRefreshFragment {
    protected MyWebView myWebView;
    private String url = "";
    private ClipboardManager clip;//复制文本用
    private boolean hideTopLeft = false;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;

    @Override
    protected void initFrgmentUI(ViewGroup view) {
        myWebView = (MyWebView) view.findViewById(R.id.peanut_web);
        refreshBaseTopbar.setTitle("读取中");
        if (hideTopLeft) {
            refreshBaseTopbar.hideLeftButton();
        }
        myWebView.setOnPeanutWebViewListener(new MyWebView.OnPeanutWebViewListener() {
            @Override
            public void onReceivedTitle(String title) {
                if (!MatchStringUtil.isChinese(title)) {
                    refreshBaseTopbar.setTitle(getResources().getString(R.string.app_name));
                } else {
                    refreshBaseTopbar.setTitle(title);
                }

            }
        });
        myWebView.setOnUrlChangeListener(new OnUrlChangeListener() {
            @Override
            public void onUrlChange(String url) {
                toggleRefreshEnable(myWebView.getScrollY());
            }
        });
        myWebView.setOnWebViewLongClickListener(new MyWebView.OnWebViewLongClickListener() {
            @Override
            public void onImgLongClick(String imgUrl) {

            }
        });
        myWebView.setOnShowFileChooserListener(new OnShowFileChooserListener() {
            @Override
            public void showFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
//                baseToast.showToast(filePathCallback+"\n"+fileChooserParams);
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
            }
        });
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
                if (Configure.IS_TEST) {
                    NormalDialog dialog = new NormalDialog(getActivity());
                    dialog.setOnDialogClickListener(new OnDialogClickListener() {
                        @Override
                        public void onOkClick() {
                            clip.setText(myWebView.getUrl());
                        }

                        @Override
                        public void onCancelClick() {

                        }
                    });
                    dialog.show();
                    dialog.setTitle(myWebView.getUrl());
                    dialog.setOkBtnText("复制地址");
                }
            }
        });
        refreshBaseTopbar.setTopBarLongClickLister(new TopBar.OnTopBarLongClickListener() {
            @Override
            public void onLeftLongClick() {

            }

            @Override
            public void onRightLongClick() {

            }

            @Override
            public void onTitleLongClick() {
                if (Configure.IS_TEST) {
                    EditDialog dialog = new EditDialog(getActivity());
                    dialog.setOnEditResultListener(new OnEditResultListener() {
                        @Override
                        public void onResult(String text) {
                            myWebView.loadUrl(text);
                        }

                        @Override
                        public void onCancelClick() {

                        }
                    });
                    dialog.show();
                }
            }
        });

        //给服务器端标记来源(用于区分App和浏览器)
        String ua = myWebView.getSettings().getUserAgentString();
        myWebView.getSettings().setUserAgentString(ua + "LOANKEY_ANDROID /" +
                BaseParameterUtil.getInstance().getAppVersionName(getActivity()));


        myWebView.setOnAllVersionScrollChangeListener(new OnAllVersionScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                toggleRefreshEnable(scrollY);
            }
        });
        hideLoadMore();
        init();
    }

    @Override
    protected void initTopViewUI(ViewGroup view) {

    }

    @Override
    protected void initBottomViewUI(ViewGroup view) {

    }

    private void init() {
        try {
            clip = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            if (getActivity() instanceof WebActivity) {
                Intent intent = getActivity().getIntent();
                url = intent.getStringExtra("url");
                if (TextUtils.isEmpty(url)) {
                    getActivity().finish();
                }

                    myWebView.loadUrl(url);
            }
        } catch (Exception e) {
            //catch
            if (Configure.IS_TEST) {
                NormalDialog dialog = new NormalDialog(getActivity());
                dialog.show();
                dialog.setTitle(e + "");
            }
        }
    }

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessageAboveL) return;
//            Uri result = data == null || resultCode != getActivity().RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == getActivity().RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (null != myWebView && !hidden) {
            toggleRefreshEnable(myWebView.getScrollY());
        }
    }

    private void toggleRefreshEnable(int scrollY) {
        if (scrollY == 0 ) {
            swipeToLoadLayout.setRefreshEnabled(true);
        } else {
            swipeToLoadLayout.setRefreshEnabled(false);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCurrentUrl() {
        return myWebView.getUrl();
    }

    @Override
    public void doGetData() {
        try {
            if (TextUtils.isEmpty(myWebView.getUrl())) {
                myWebView.loadUrl(url);
            } else {
                myWebView.reload();
            }
            noMoreData();
        } catch (Exception e) {
            //有可能fragment还没加载完就调用所以可能空指针
            e.printStackTrace();
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_webview;
    }

    public void loadUrl(String url) {
        if (null != myWebView) {
            myWebView.loadUrl(url);
        }
    }

    @Override
    protected int getTopViewId() {
        return 0;
    }

    @Override
    protected int getBottomViewId() {
        return 0;
    }

    public void setHideTopLeft(boolean hideTopLeft) {
        this.hideTopLeft = hideTopLeft;
    }
}
