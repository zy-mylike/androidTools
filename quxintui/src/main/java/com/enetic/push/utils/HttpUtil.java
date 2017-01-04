//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.enetic.push.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.util.PreferencesCookieStore;
import java.io.File;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.http.entity.StringEntity;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

public class HttpUtil {
    private static HttpUtils mHttpUtils;

    private HttpUtil() {
    }

    public static HttpUtils initialize(Context context) {
        if(mHttpUtils == null) {
            mHttpUtils = new HttpUtils(15000);
            mHttpUtils.configResponseTextCharset("UTF-8");
            mHttpUtils.configCurrentHttpCacheExpiry(0L);
            mHttpUtils.configDefaultHttpCacheExpiry(0L);
            mHttpUtils.configRequestThreadPoolSize(10);
            PreferencesCookieStore cookieStore = new PreferencesCookieStore(context);
            cookieStore.clear();
            mHttpUtils.configCookieStore(cookieStore);
        }

        return mHttpUtils;
    }

    public static RequestParams generateEntityParams(JSONObject body, JSONObject header) {
        RequestParams params = new RequestParams();

        try {
            if(header != null) {
                Iterator e = header.keys();

                while(e.hasNext()) {
                    String key = (String)e.next();
                    params.addHeader(key, header.getString(key));
                }
            }

            if(body != null) {
                params.setBodyEntity(new StringEntity(body.toString(), "UTF-8"));
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return params;
    }

    public static void sendGet(Context context, String url, RequestCallBack<String> callBack) {
        initialize(context);
        mHttpUtils.send(HttpMethod.GET, url, callBack);
    }

    public static void sendGet(Context context, String url, RequestParams params, RequestCallBack<String> callBack) {
        initialize(context);
        mHttpUtils.send(HttpMethod.GET, url, params, callBack);
    }

    public static void sendPost(Context context, String url, RequestParams params, RequestCallBack<String> callBack) {
        initialize(context);
        mHttpUtils.send(HttpMethod.POST, url, params, callBack);
    }

    public static void download(Context context, String url, String savePath, RequestCallBack<File> callBack) {
        initialize(context);
        mHttpUtils.download(url, savePath, callBack);
    }

    public static void upload(Context context, String url, JSONObject body, IdentityHashMap<String, File> files, RequestCallBack<String> callBack) {
        initialize(context);
        RequestParams params = new RequestParams();
        params.addHeader("enctype","multipart/form-data");
        MultipartEntity multipartEntity = new MultipartEntity();

        Iterator e;
        try {
            if(body != null) {
                e = body.keys();

                while(e.hasNext()) {
                    String entry = (String)e.next();
                    multipartEntity.addPart(entry, new StringBody(body.getString(entry)));
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        e = files.entrySet().iterator();

        while(e.hasNext()) {
            Entry entry1 = (Entry)e.next();
            multipartEntity.addPart((String)entry1.getKey(), new FileBody((File)entry1.getValue()));
        }

        params.setBodyEntity(multipartEntity);
        mHttpUtils.send(HttpMethod.POST, url, params, callBack);
    }

    public static void httpGet(Context context, String url, final HttpCallback callback) {
        LogUtils.i("请求地址：" + url);
        LogUtils.i("请求方式：GET");
        sendGet(context, url, new RequestCallBack() {
            public void onSuccess(ResponseInfo  result) {
                LogUtils.i("请求成功");

                try {
                    JSONObject e = new JSONObject((String)result.result);
                    String code = e.getString("code");
                    String msg = e.getString("message");
                    LogUtils.i("响应码:" + code);
                    if("1000".equals(code)) {
                        LogUtils.i("操作成功：" + msg);
                        callback.doAuthSuccess(result, e);
                    } else {
                        LogUtils.i("操作失败：" + msg);
                        callback.doAuthFailure(result, e);
                    }
                } catch (Exception var5) {
                    LogUtils.i((String)result.result);
                    callback.doRequestFailure(var5, var5.getMessage());
                    var5.printStackTrace();
                }

            }

            public void onFailure(HttpException exception, String msg) {
                LogUtils.i("请求失败\n" + msg);
                callback.doRequestFailure(exception, msg);
            }
        });
    }

    public static void httpGet(Context context, String url, JSONObject header, final HttpCallback callback) {
        LogUtils.i("请求地址：" + url);
        LogUtils.i("请求方式：GET");
        LogUtils.i("Head info：" + header.toString());
        sendGet(context, url, generateEntityParams((JSONObject)null, header), new RequestCallBack() {
            public void onSuccess(ResponseInfo result) {
                LogUtils.i("请求成功");

                try {
                    JSONObject e = new JSONObject((String)result.result);
                    String code = e.getString("code");
                    String msg = e.getString("message");
                    LogUtils.i("响应码:" + code);
                    if("1000".equals(code)) {
                        LogUtils.i("操作成功：" + msg);
                        callback.doAuthSuccess(result, e);
                    } else {
                        LogUtils.i("操作失败：" + msg);
                        callback.doAuthFailure(result, e);
                    }
                } catch (Exception var5) {
                    LogUtils.i((String)result.result);
                    callback.doRequestFailure(var5, var5.getMessage());
                    var5.printStackTrace();
                }

            }

            public void onFailure(HttpException exception, String msg) {
                LogUtils.i("请求失败\n" + msg);
                callback.doRequestFailure(exception, msg);
            }
        });
    }

    public static void httpPost(Context context, String url, JSONObject body, JSONObject header, final HttpCallback callback) {
        try {
            LogUtils.i("请求地址：" + url);
            LogUtils.i("请求方式：POST");
            LogUtils.i("Head info：" + header.toString());
            sendPost(context, url, generateEntityParams(body, header), new RequestCallBack() {
                public void onSuccess(ResponseInfo result) {
                    try {
                        JSONObject e = new JSONObject((String)result.result);
                        String code = e.getString("code");
                        String msg = e.getString("message");
                        LogUtils.i("响应码:" + code);
                        if("1000".equals(code)) {
                            LogUtils.i("操作成功：" + msg);
                            callback.doAuthSuccess(result, e);
                        } else {
                            LogUtils.i("操作失败：" + msg);
                            callback.doAuthFailure(result, e);
                        }
                    } catch (Exception var5) {
                        LogUtils.i((String)result.result);
                        var5.printStackTrace();
                    }

                }

                public void onFailure(HttpException exception, String msg) {
                    LogUtils.i("请求失败\n" + msg);
                    callback.doRequestFailure(exception, msg);
                }
            });
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static void httpDownload(Context context, String url, String savePath, final HttpCallback callback) {
        download(context, url, savePath, new RequestCallBack<File>() {
            public void onSuccess(ResponseInfo<File> result) {
                LogUtils.i("下载成功");

                try {
                    callback.doDownloadSuccess((File)result.result);
                } catch (Exception var3) {
                    var3.printStackTrace();
                }

            }

            public void onLoading(long total, long current, boolean isUploading) {
                int progress = (int)(current * 100L / total);
                LogUtils.i("下载中...");
                LogUtils.i("当前进度" + progress + "%");
                callback.doDownloadLoading(progress);
            }

            public void onFailure(HttpException exception, String msg) {
                LogUtils.i("请求失败\n" + msg);
                callback.doRequestFailure(exception, msg);
            }
        });
    }

    public static void httpUpload(Context context, String url, JSONObject body, IdentityHashMap<String, File> files, final HttpCallback callback) {
        LogUtils.i("请求地址：" + url);
        LogUtils.i("请求方式：POST");
        upload(context, url, body, files, new RequestCallBack() {
            public void onSuccess(ResponseInfo result) {
                LogUtils.i("上传成功");

                try {
                    JSONObject e = new JSONObject((String)result.result);
                    String code = e.getString("code");
                    String msg = e.getString("message");
                    LogUtils.i("操作成功：" + msg);
                    callback.doUploadSuccess(result, e);
                } catch (Exception var5) {
                    LogUtils.i((String)result.result);
                    var5.printStackTrace();
                }

            }

            public void onFailure(HttpException exception, String msg) {
                LogUtils.i("请求失败\n" + msg);
                callback.doRequestFailure(exception, msg);
            }
        });
    }

    public static boolean isNetworkConnected(Context context) {
        if(context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if(mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    public boolean isWifiConnected(Context context) {
        if(context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(1);
            if(mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    public boolean isMobileConnected(Context context) {
        if(context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(0);
            if(mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        if(connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if(networkInfo != null && networkInfo.length > 0) {
                for(int i = 0; i < networkInfo.length; ++i) {
                    if(networkInfo[i].getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public static String getNetworkType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if(info == null) {
            return "NULL";
        } else if(info.getType() == 1) {
            return "WIFI";
        } else {
            if(info.getType() == 0) {
                switch(info.getSubtype()) {
                case 1:
                    return "GPRS";
                case 2:
                    return "EDGE";
                case 3:
                    return "UMTS";
                case 4:
                    return "CDMA";
                case 5:
                    return "EVDO0";
                case 6:
                    return "EVDOA";
                case 7:
                default:
                    break;
                case 8:
                    return "HSDPA";
                case 9:
                    return "HSUPA";
                case 10:
                    return "HSPA";
                }
            }

            return "UNKNOWN";
        }
    }
}
