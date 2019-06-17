package com.insightsurface.notebook.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Administrator on 2017/7/19.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        initLeanCloud();
        initUserInfo();
        dealFileUriExposedException();
        registerActivityLifecycle();
    }

    private void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.i("Lifecycle", "Created" + activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.i("bo", "Started" + activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
//                if (isBackGround) {
//                    isBackGround = false;
                Log.i("Lifecycle", "APP回到了前台");
//                    doStatistics("front");
//                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i("Lifecycle", "Paused" + activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.i("Lifecycle", "Stopped" + activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.i("Lifecycle", "SaveInstance" + activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.i("Lifecycle", "destroyed" + activity);
            }
        });
    }

    private void dealFileUriExposedException() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    private void initUserInfo() {
//        LoginBean.getInstance().setLoginInfo(this, LoginBean.getLoginInfo(this));
    }

    private void initLeanCloud() {
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "jVea4vi5Ma4U4i7LbVfIDJEi-gzGzoHsz", "Xin3oGqmeRiIQCm8w2E2q9U6");
        AVOSCloud.setDebugLogEnabled(true);
    }

    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
