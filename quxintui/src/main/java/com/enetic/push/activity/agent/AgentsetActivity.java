package com.enetic.push.activity.agent;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ContactUsActivity;
import com.enetic.push.activity.FeekBackActivity;
import com.enetic.push.activity.LoginActivity;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.dialog.LoadingDialog;
import com.enetic.push.utils.DataCleanManager;
import com.enetic.push.utils.Preferences;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.BaseApplication;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.annotation.TrackClick;
import org.json.JSONObject;

@Layout(R.layout.activity_agentset)
public class AgentsetActivity extends ParentActivity {

    private final static String TITLE = "设置";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
    }


//    @TrackClick(value = R.id.layout_tiao, location = TITLE, eventName = "隐私条款")
//    private void clickTiao(View view) {
//        Toast.makeText(getApplicationContext(), "待开发", Toast.LENGTH_SHORT
//        ).show();
//    }


    @TrackClick(value = R.id.layout_about, location = TITLE, eventName = "关于我们")
    private void clickAbout(View view) {
        startActivity(new Intent(this, ContactUsActivity.class));
    }

    LoadingDialog loadingDialog;

    @TrackClick(value = R.id.layout_cache, location = TITLE, eventName = "清理缓存")
    private void clickCache(View view) {
        if (loadingDialog == null)
            loadingDialog = new LoadingDialog(this);
        loadingDialog.show(5);
        DataCleanManager.clearAllCache(this);
    }


    @TrackClick(value = R.id.layout_mark, location = TITLE, eventName = "给我们评分")
    private void clickMark(View view) {
        Toast.makeText(getApplicationContext(), "待开发", Toast.LENGTH_SHORT
        ).show();
    }

    @TrackClick(value = R.id.layout_feekback, location = TITLE, eventName = "意见反馈")
    private void clickFeekback(View view) {
        startActivity(new Intent(this, FeekBackActivity.class));
    }


    @TrackClick(value = R.id.logout, location = TITLE, eventName = "退出登录")
    private void clickLogOut(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出吗?").setTitle("提示").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                final LoadingDialog loadingDialog = new LoadingDialog(AgentsetActivity.this);
                loadingDialog.show(-1);
                String token = Preferences.getInstance(getApplicationContext()).getPreference("token", "");
                httpGet(Constants.URL_LOGOUT + token, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        loadingDialog.dismiss();
                        ((BaseApplication) getApplicationContext()).finishAllActivity();
                        Preferences.getInstance(getApplicationContext()).removeValue("token");
                        Preferences.getInstance(getApplicationContext()).removeValue("userId");
                        Preferences.getInstance(getApplicationContext()).removeValue("type");
                        Preferences.getInstance(getApplicationContext()).setPreference("token", "");
                        Preferences.getInstance(getApplicationContext()).setPreference("userId", "-1");
                        Preferences.getInstance(getApplicationContext()).setPreference("type", "-1");
                        ((BaseApplication) getApplicationContext()).finishAllActivity();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        loadingDialog.dismiss();
                    }
                });
            }
        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();

    }
}
