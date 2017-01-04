package com.enetic.push.activity.agent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.annotation.TrackClick;

import org.json.JSONException;
import org.json.JSONObject;

@Layout(R.layout.activity_phone)
public class PhoneCodeActivity extends ParentActivity {

    String phone = "";
    @ViewIn(R.id.phone)
    private TextView mPhoneView;
    @ViewIn(R.id.edit_query)
    private EditText mCodeView;
    private final static String TITLE = "填写短信验证码";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        phone = getIntent().getStringExtra("phone");
        mPhoneView.setText("已发送短信验证码至：" + phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        clickGETCode(null);
    }

    @TrackClick(value = R.id.submint, location = TITLE, eventName = "完成")
    public void clickPhone(View view) {
        checkCode();
    }

    private void submint() {
        JSONObject object = new JSONObject();
        try {
            object.put("userId", Constants.CURRENT_USER.getUserId());
            object.put("mobile", phone);
            Log.e("","object = " +object.toString());
            httpPost(Constants.HOST + "user/editAuthentication", object, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);

                    Toast.makeText(getApplicationContext(), "绑定成功", Toast.LENGTH_SHORT).show();
                    finish();

                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    try {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void checkCode() {
        String code = mCodeView.getText().toString();
        if (TextUtils.isEmpty(code)) {
            return;
        }
        httpGet(Constants.HOST + "user/verifyCode?mobile=" + phone + "&code=" + code
                , new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        submint();
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        Toast.makeText(getApplicationContext(), "验证码错误", 0).show();
                    }
                });
    }


    @TrackClick(value = R.id.get_authCode, location = TITLE, eventName = "获取验证码")
    public void clickGETCode(final View view) {

        httpGet(Constants.HOST + "user/code?m=" + phone + "&appkey=" + Constants.APP_KRY
                , new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
//                        try {
                            index = 60;
                            mHandler.post(runnable);
                            view.setEnabled(false);
//                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        try {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @ViewIn(R.id.get_authCode)
    Button button;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 <= 0) {
                button.setEnabled(true);
                button.setText("重新获取");
            } else {
                button.setText(msg.arg1 + "s");
                index--;
                mHandler.post(runnable);
            }
        }
    };
    int index = 0;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                mHandler.obtainMessage(0, index, index).sendToTarget();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

}