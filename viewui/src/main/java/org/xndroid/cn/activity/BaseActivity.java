package org.xndroid.cn.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.xndroid.cn.BaseApplication;
import org.xndroid.cn.annotation.init.ViewInobject;

public class BaseActivity extends AppCompatActivity {

    protected View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = ViewInobject.initLayoutViews(this, this);
        if (mContentView != null) {
            setContentView(mContentView);
        }
        ((BaseApplication)this.getApplication()).addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((BaseApplication)this.getApplication()).removeActivity(this);
    }
}
