package com.enetic.push.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

import com.enetic.esale.R;
import com.enetic.push.activity.ProxyActivity;

/**
 * Created by json on 2016/4/5.
 */
public class ProxyDialog extends Dialog {
    public ProxyDialog(final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_proxy_sucess);


        findViewById(R.id.add_proxy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //增加代理
                ((ProxyActivity) (context)).addProxy();
                cancel();
            }
        });
    }

}