package com.enetic.push.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;

public class AsyncImageLoader {
    private BitmapUtils mBitmapUtils;
    private Context mContext;
    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(17170445);

    public AsyncImageLoader(Context context, int defaultImg, int failedImg) {
        this.mContext = context;
        this.mBitmapUtils = new BitmapUtils(this.mContext);
        this.mBitmapUtils.configDefaultLoadingImage(defaultImg);
        this.mBitmapUtils.configDefaultLoadFailedImage(failedImg);
        this.mBitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        this.mBitmapUtils.closeCache();
    }

    public void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{TRANSPARENT_DRAWABLE, new BitmapDrawable(imageView.getResources(), bitmap)});
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }

    private void fadeInDisplayBackground(View view, Bitmap bitmap) {
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{TRANSPARENT_DRAWABLE, new BitmapDrawable(view.getResources(), bitmap)});
        view.setBackgroundDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }

    public void display(ImageView container, String url,DefaultBitmapLoadCallBack callBack) {
        this.mBitmapUtils.display(container, url,callBack);
    }

    public void display(View container, String url) {
        this.mBitmapUtils.display(container, url, new AsyncImageLoader.AsyncBitmapLoadBackgroundCallBack());
    }

    public class AsyncBitmapLoadBackgroundCallBack extends DefaultBitmapLoadCallBack<View> {
        public AsyncBitmapLoadBackgroundCallBack() {
        }

        public void onLoadCompleted(View container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            AsyncImageLoader.this.fadeInDisplayBackground(container, bitmap);
        }
    }

    public class AsyncBitmapLoadCallBack extends DefaultBitmapLoadCallBack<ImageView> {
        public AsyncBitmapLoadCallBack() {
        }

        public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
            AsyncImageLoader.this.fadeInDisplay(container, bitmap);
        }
    }
}