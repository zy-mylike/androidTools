package com.enetic.push.share;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.enetic.push.Constants;
import com.enetic.push.activity.LoginActivity;
import com.enetic.push.utils.FileUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by json on 2016/6/14.
 */
public class Wx {

    public static IWXAPI api;
    private static Wx w;
    private Context mCtx;
    private WxBean bean;
    com.lidroid.xutils.HttpUtils httpUtils;

    private Wx(Context context) {
        mCtx = context;
        httpUtils = new HttpUtils();
    }

    public static Wx init(Context context) {
        if (api == null) {
            api = WXAPIFactory.createWXAPI(context, Constants.WXConstant.WXAPP_ID, false);
            api.registerApp(Constants.WXConstant.WXAPP_ID);
            w = new Wx(context);
            return w;
        }
        return w;
    }

    public void sendAuthRequest() {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(mCtx, "未安装微信客户端，请先下载", Toast.LENGTH_LONG).show();
            return;
        }
        Constants.CURRENT_USER = null;
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

    public void sendHttp(SendAuth.Resp resp) {


        final String utl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.WXConstant.WXAPP_ID + "&secret=" + Constants.WXConstant.WXAPP_SELECT + "&code=" + resp.code + "&grant_type=authorization_code";
        // w.write(utl);
        httpUtils.send(HttpRequest.HttpMethod.GET, utl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                bean = new Gson().fromJson(responseInfo.result, WxBean.class);
                check(bean.access_token, bean.openid);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                // Toast.makeText(,msg,Toast.LENGTH_SHORT).show();

                Log.i("lyw", msg);
            }

        });


    }

    /**
     * 检查token是否有效
     *
     * @param token
     * @param openid
     */
    public void check(final String token, final String openid) {
        String utl =
                "https://api.weixin.qq.com/sns/auth?access_token=" + token + "&openid=" + openid;
        httpUtils.send(HttpRequest.HttpMethod.GET, utl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {


                    JSONObject obj = new JSONObject(responseInfo.result);
                    if (obj.getString("errcode").equals("0")) {
                        //有效  获取用户信息
                        getWxUserInfo(token, openid);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {

                Log.i("lyw", msg);
            }
        });
    }

    public void getWxUserInfo(String token, String openid) {
        String utl =
                "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid;
        httpUtils.send(HttpRequest.HttpMethod.GET, utl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                WxUserBean bean = new Gson().fromJson(responseInfo.result, WxUserBean.class);
                ((LoginActivity) (mCtx)).startLogin(bean);

            }

            @Override
            public void onFailure(HttpException error, String msg) {

            }
        });
    }

    public final class WxUserBean {
        public String openid = "";
        public String nickname = "";
        public int sex = 1;
        public String province = "";
        public String city = "";
        public String country = "";
        public String headimgurl = "";
        public String unionid = "";
        public List<String> privilege = null;
//        {
//            "openid":"OPENID",
//                "nickname":"NICKNAME",
//                "sex":1,
//                "province":"PROVINCE",
//                "city":"CITY",
//                "country":"COUNTRY",
//                "headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
//                "privilege":[
//            "PRIVILEGE1",
//                    "PRIVILEGE2"
//            ],
//            "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
//
//        }

    }

    private final class WxBean {
        String access_token;
        int expires_in;
        String refresh_token;
        String openid;
        String scope;
        String unionid;
//        "access_token": "Yy4pB-QiIfHCSaiR7m7174DcI_YYNNliCS7pfjiWT1C9zlW4yhzs5Inkn4zS7jF1_5bwyyH0-K_PfwS3P0yL_BBiHtzAUMlh8GVCu0APy1A",
//                "expires_in": 7200,
//                "refresh_token": "YPe6JejL18B01eBma5q8dkE1UclQJmB8ldcChLYtqLH4Aqd2uQXNL4mAGERAxEBIj-xdEovCPjmmOKHmH6bozq2LEPSw37l17lklmdAhUzU",
//                "openid": "oS8cWwlT2gJDMkOW4dRy1E3pDWyE",
//                "scope": "snsapi_userinfo",
//                "unionid": "or4VUt_cLjXgUS49VxE3uTm2ySnQ"
    }


}