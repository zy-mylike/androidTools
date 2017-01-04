package com.enetic.push.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.enetic.push.Constants;
import com.enetic.push.bean.UserBean;
import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by json on 2016/5/3.
 */
public abstract class UpdataUserInfo {

    public void getUserInfo(final Context ctx, String userId,String tp) {
         String type = "&type="+tp;
        if(TextUtils.isEmpty(tp)){
            type ="";
        }
        final String utl =  Constants.URL_USERINFO + userId +type;


        File f = new File(FileUtils.SDPATH ,"logins.txt");
        FileWriter w = null;
        try {
            w = new FileWriter(f);

            w.write(utl.toString());
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpUtil.httpGet(ctx, utl, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    UserBean bean = new Gson().fromJson(obj
                            .getString("data"), UserBean.class);
                    getUserInfo(bean);
//                    Toast.makeText(ctx,"doRequestFailure user--" + obj.getString("data") +utl,0).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
//                Toast.makeText(ctx,"doRequestFailure user--" + msg +utl,0).show();
                getUserInfo(null);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
//                Toast.makeText(ctx,"doAuthFailure user--" + obj.toString()+utl,0).show();
                getUserInfo(null);
            }
        });
    }

    public abstract void getUserInfo(UserBean bean);

    public static Boolean isLogIn(Context ctx, Boolean isLogin) {
        if (isLogin && Constants.CURRENT_USER == null) {
//            ctx.startActivity(new Intent(ctx, LoginRegistActivity.class));
        }
        return Constants.CURRENT_USER != null;
    }
}