package com.enetic.push.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.bean.UserBean;
import com.enetic.push.dialog.GeneralDialog;
import com.enetic.push.share.Wx;
import com.enetic.push.utils.FileUtils;
import com.enetic.push.utils.Preferences;
import com.enetic.push.utils.UpdataUserInfo;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;

import org.eteclab.base.BaseApplication;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.annotation.TrackClick;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * A login screen that offers login via email/password.
 */
@Layout(R.layout.activity_login)
public class LoginActivity extends ParentActivity {

    private final static String TITLE = "登录";
    private static int type = -1;

    @ViewIn(R.id.no_person)
    private TextView mPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPerson.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

    }

    @TrackClick(value = R.id.login_shop, location = TITLE, eventName = "商家登录")
    private void clickShop(View view) {
        if (type == 0) {
            return;
        }
        type = 0;
//        startLogin(null);
        Wx w = Wx.init(this);
        w.sendAuthRequest();
    }

    @TrackClick(value = R.id.login_agent, location = TITLE, eventName = "代理登录")
    private void clickAgent(View view) {
        if (type == 1) {
            return;
        }
        type = 1;
        Wx w = Wx.init(this);
        w.sendAuthRequest();
    }

    @TrackClick(value = R.id.no_person, location = TITLE, eventName = "不确定自己的身份")
    private void clickNoPerson(View view) {

        final GeneralDialog generalDialog = new GeneralDialog(this, getRootLayout());
        generalDialog.setTitle("提示");
        generalDialog.setMessage("如果您是一个微商店家或者想要从事微商行业，请您选择商家登陆；如果您不想自己开店，只想通过做代理销售来获得提成收入，请您选择代理登录。如仍有疑问，请关注我们的微信服务号：蜂巢全新推，联系客服进行解答。");
        generalDialog.setPositiveButton("朕知道了", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generalDialog.dismiss();
            }
        });
        generalDialog.show();

    }

    public void startLogin(Wx.WxUserBean bean) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("openId", bean.openid);
            obj.put("unionId", bean.unionid);
            obj.put("nickName", bean.nickname);
            obj.put("sex", bean.sex);
            obj.put("portrait", bean.headimgurl);
            obj.put("type", type + 1);

            LogUtils.e(obj.toString());
            File f = new File(FileUtils.SDPATH, "login.txt");
            FileWriter w = new FileWriter(f);
            w.write(obj.toString());
            w.close();
            httpPost(Constants.URL_LOGIN_WX, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, final JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        if (obj.getString("code").equals("1000")) {
                            String token = obj.getString("token");
                            String userId = obj.getString("userId");
                            String type = obj.getString("type");
                            Preferences.getInstance(getApplicationContext()).setPreference("token", token);
                            Preferences.getInstance(getApplicationContext()).setPreference("userId", userId);
                            Preferences.getInstance(getApplicationContext()).setPreference("type", type);
                            UpdataUserInfo info = new UpdataUserInfo() {
                                @Override
                                public void getUserInfo(UserBean bean) {
                                    Constants.CURRENT_USER = bean;
                                    if (bean != null) {
                                        ((BaseApplication) getApplication()).finishAllActivity();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("type", LoginActivity.this.type));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            };
                            info.getUserInfo(getApplicationContext(), userId, type);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    File f = new File(FileUtils.SDPATH, "logindoAuthFailure.txt");
                    FileWriter w = null;
                    try {
                        w = new FileWriter(f);
                        w.write(obj.toString());
                        w.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doRequestFailure(Exception exception, String msg) {
                    super.doRequestFailure(exception, msg);
                    File f = new File(FileUtils.SDPATH, "logindoAuthFailure.txt");
                    FileWriter w = null;
                    try {
                        w = new FileWriter(f);
                        w.write(msg.toString());
                        w.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public View getRootLayout() {
        return (findViewById(android.R.id.content)).getRootView();
    }
}