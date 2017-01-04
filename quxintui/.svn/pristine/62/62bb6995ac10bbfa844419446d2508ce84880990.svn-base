
package com.enetic.push.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.bean.UserBean;
import com.enetic.push.utils.Preferences;
import com.enetic.push.utils.UpdataUserInfo;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

@Layout(R.layout.activity_lead)
public class LeadActivity extends ParentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //地瓜老板
        //userInfo.getUserInfo(getApplicationContext(), "148", Preferences.getInstance(getApplicationContext()).getPreference("type", "1")); //
        //在哪里
//        userInfo.getUserInfo(getApplicationContext(), "150", Preferences.getInstance(getApplicationContext()).getPreference("type", "1")); // 1:代理
        //userInfo.getUserInfo(getApplicationContext(), "150", Preferences.getInstance(getApplicationContext()).getPreference("type", "0")); // 0:商家
        //测试账号。
        //userInfo.getUserInfo(getApplicationContext(), "151", Preferences.getInstance(getApplicationContext()).getPreference("type", "2"));
        //哈哈
        //userInfo.getUserInfo(getApplicationContext(), "164", Preferences.getInstance(getApplicationContext()).getPreference("type", "2")); //代理
        //userInfo.getUserInfo(getApplicationContext(), "163", Preferences.getInstance(getApplicationContext()).getPreference("type", "0")); //商户
        String token = Preferences.getInstance(getApplicationContext()).getPreference("token", "");
        if (!TextUtils.isEmpty(token)) {
            httpGet(Constants.USER_TOKEN + token, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    String userId = Preferences.getInstance(getApplicationContext()).getPreference("userId", "-1");
                    userInfo.getUserInfo(getApplicationContext(), userId, Preferences.getInstance(getApplicationContext()).getPreference("type", "-1"));
                }

                @Override
                public void doRequestFailure(Exception exception, String msg) {
                    super.doRequestFailure(exception, msg);
                    handler.sendEmptyMessageDelayed(0, 1000);
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    handler.sendEmptyMessageDelayed(0, 1000);
                }
            });
        } else {
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    break;
                case 1:
                    int type = Integer.valueOf(Preferences.getInstance(getApplicationContext()).getPreference("type", "1")); //1:商户 2:代理
                    startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("type", type - 1));
                    finish();
                    break;
            }
        }
    };

    UpdataUserInfo userInfo = new UpdataUserInfo() {
        @Override
        public void getUserInfo(UserBean bean) {
            Constants.CURRENT_USER = bean;
            handler.sendEmptyMessageDelayed(bean == null ? 0 : 1, 1000);
        }
    };
}