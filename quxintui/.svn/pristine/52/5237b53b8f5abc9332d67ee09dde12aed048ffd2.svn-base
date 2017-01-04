package com.enetic.push.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.enetic.esale.R;

/**
 * Created by json on 2016/07/04.
 */
public class LoadingDialog extends Dialog {

    private TextView progress;

    public LoadingDialog(Context context) {
        super(context, R.style.dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_loading);
        progress = (TextView) findViewById(R.id.progress);
        ImageView imageView = (ImageView) findViewById(R.id.loading);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.progress_anim);
        imageView.startAnimation(operatingAnim);
        setCanceledOnTouchOutside(false);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismiss();
        }
    };


    public void setProgress(String progress) {
        this.progress.setText(progress);
    }

    public void show(int s) {
        super.show();
        if (s > 0)
            handler.sendEmptyMessageDelayed(0, s * 1000);
        Window window = getWindow();
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }
}
