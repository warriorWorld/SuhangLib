package com.insightsurface.lib.widget.imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.insightsurface.lib.config.Configure;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class WrapHeightImageView extends android.support.v7.widget.AppCompatImageView {
    private Context mContext;
    private Bitmap mBitmap;
    /**
     * Handler处理类
     */
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    post(new Runnable() {
                        @Override
                        public void run() {
                            ViewGroup.LayoutParams vgl = getLayoutParams();
                            if (mBitmap == null) {
                                return;
                            }
                            //获取bitmap的宽度
                            float bitWidth = mBitmap.getWidth();
                            //获取bitmap的宽度
                            float bithight = mBitmap.getHeight();

                            //计算出图片的宽高比，然后按照图片的比列去缩放图片
                            float bitScalew = bitWidth / bithight;
                            // 高按照比例计算
                            vgl.width = (int) getMeasuredWidth();
                            vgl.height = (int) (getMeasuredWidth() / bitScalew);
                            //设置图片充满ImageView控件
                            setScaleType(ScaleType.FIT_XY);
                            //等比例缩放
                            setAdjustViewBounds(true);
                            setLayoutParams(vgl);
                            setImageBitmap(mBitmap);
                        }
                    });
                    break;
            }
        }
    };

    public WrapHeightImageView(Context context) {
        super(context);
        init(context);
    }

    public WrapHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WrapHeightImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    public void setImgUrl(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap = ImageLoader.getInstance().loadImageSync(url, Configure.normalImageOptions);
                Message msg = Message.obtain();
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    public void setImgUrl(final String url, final float width, final float height) {
        setImgUrl(url, width, height, null);
    }

    public void setImgUrl(final String url, final float width, final float height, final DisplayImageOptions options) {
        post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams vgl = getLayoutParams();

                //计算出图片的宽高比，然后按照图片的比列去缩放图片
                float bitScalew;
                if (width == 0f || height == 0f) {
                    bitScalew = 1;
                } else {
                    bitScalew = width / height;
                }
                // 高按照比例计算
                vgl.width = (int) getMeasuredWidth();
                vgl.height = (int) (getMeasuredWidth() / bitScalew);
                //设置图片充满ImageView控件
                setScaleType(ScaleType.FIT_XY);
                //等比例缩放
                setAdjustViewBounds(true);
                setLayoutParams(vgl);
                if (width == 0f || height == 0f) {
//                    setImageResource(R.drawable.loading);
                } else if (null == options) {
                    ImageLoader.getInstance().displayImage(url, WrapHeightImageView.this);
                } else {
                    ImageLoader.getInstance().displayImage(url, WrapHeightImageView.this, options);
                }
            }
        });
    }
}
