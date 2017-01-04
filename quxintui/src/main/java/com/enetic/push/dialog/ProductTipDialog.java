package com.enetic.push.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.enetic.esale.R;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.activity.ProxyActivity;

/**
 * Created by json on 2016/4/5.
 */
public class ProductTipDialog extends PopupWindow {

    public static ProductTipDialog show(Context mCtx, View parent, View.OnClickListener onClickListener) {
        final ProductTipDialog window = new ProductTipDialog();
        LinearLayout root = new LinearLayout(mCtx);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        root.setLayoutParams(lp);
        root.setGravity(Gravity.CENTER);
        root.setBackgroundColor(Color.argb(150, 0, 0, 0));

        root.setOnClickListener(onClickListener);

        View layout = View.inflate(mCtx, R.layout.layout_proxy_sucess, null);
        layout.findViewById(R.id.add_proxy).setOnClickListener(onClickListener);
        layout.findViewById(R.id.send_proxy).setOnClickListener(onClickListener);
        layout.findViewById(R.id.share_wx).setOnClickListener(onClickListener);
        layout.findViewById(R.id.wx).setOnClickListener(onClickListener);
        layout.findViewById(R.id.submint).setOnClickListener(onClickListener);

        root.addView(layout);

        WindowManager wm = ((ParentActivity) mCtx).getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        window.setWidth(-1);
        window.setHeight(-1);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setContentView(root);
        window.showAtLocation(parent, Gravity.CENTER, 0, 0);
        return window;
    }

}