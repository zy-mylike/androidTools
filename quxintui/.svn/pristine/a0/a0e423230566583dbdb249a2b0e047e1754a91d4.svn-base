package com.enetic.push.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.enetic.esale.R;

import java.io.File;

/**
 * Created by moon1 on 2016/4/5.
 */
public class PopupWindows extends PopupWindow {
    private View.OnClickListener mOnPhotoClick;
    private View.OnClickListener mOnTakePhotoClick;

    public void setPhotoClick(View.OnClickListener mOnPhotoClick) {
        this.mOnPhotoClick = mOnPhotoClick;
    }

    public void setTakePicture(View.OnClickListener mOnTakePhotoClick) {
        this.mOnTakePhotoClick = mOnTakePhotoClick;
    }

    public PopupWindows(Context mContext, View parent) {
        View view = View.inflate(mContext, R.layout.layout_item_popupwindows, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

        setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();

        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        if (mOnPhotoClick != null && mOnTakePhotoClick != null) {
            bt1.setOnClickListener(mOnPhotoClick);
            bt2.setOnClickListener(mOnTakePhotoClick);
        }
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
