package com.insightsurface.lib.widget.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class WrapHeightView extends View {
    private Context mContext;

    public WrapHeightView(Context context) {
        super(context);
        init(context);
    }

    public WrapHeightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WrapHeightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    public void wrapHeight(final ViewGroup vg,final int heightOffset) {
        vg.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams vgl = getLayoutParams();
                vgl.height = (int) (vg.getMeasuredHeight() + heightOffset);
                setLayoutParams(vgl);
            }
        });
    }
}
