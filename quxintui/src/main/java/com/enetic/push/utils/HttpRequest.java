package com.enetic.push.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonWriter;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

/**
 * Created by json on 2016/6/14.
 */
public class HttpRequest {

    /**
     * @param ctx
     * @param name
     * @param price
     * @param repertory 库存量
     * @param desp 描述
     * @param shelves
     * @param shareReward
     * @param orderReward
     * @param businessId
     * @param paths
     * @param callback
     */
    public static void post(Context ctx, String id, String name, String price, String repertory, String desp,
                            String shelves, String shareReward, String orderReward, String businessId,
                            List<String> paths, final RequestCallBack callback) {
//        id 商品 id 添加商品 id 为空，修改商品 id 不能为空
//        name 商品名称
//        price 价格
//        spec 规格
//        repertory 库存
//        desp 介绍
//        shelves 上下架 1 上架 0 下架
//        shareReward 分享奖励
//        orderReward 成交奖励
//        businessId 商家用户 id
//        file1 文件类型 图片最多九张
//        file2 文件类型 图片
//        ... ... ...
//        file9 文件类型 图片
//        RequestParams params = new RequestParams();


        try {
            JSONObject obj = new JSONObject();
            if (!TextUtils.isEmpty(id))
                obj.put("id", id);

            obj.put("name", name);
            obj.put("price", price);
            obj.put("repertory", repertory);
            obj.put("desp", desp);
            obj.put("shelves", shelves);
            obj.put("shareReward", shareReward);
            obj.put("orderReward", orderReward);
            obj.put("businessId", businessId);

            IdentityHashMap<String, File> files = new IdentityHashMap<>();

            for (int index = 0; index < (paths.size() < 10 ? paths.size() : 10); index++) {
                String path = paths.get(index);
                if (!path.startsWith("http://"))
                    files.put("file" + (index + 1), new File(path));
            }
            com.enetic.push.utils.HttpUtil.upload(ctx, Constants.URL_PRODUCT_EDIT, obj, files, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void userInfos(Context context, final HttpCallback callback, String... keyVlalue) {

        if (keyVlalue.length % 2 != 0) {
            new Throwable("keyVlalue length not Valid");
        }
        try {
            JSONArray array = new JSONArray();
            for (int index = 0; index < keyVlalue.length; index += 2) {
                JSONObject ob = new JSONObject();
                ob.put("modifyName",keyVlalue[index]);
                ob.put("modifyValue",keyVlalue[index + 1 ]);
                array.put(ob);
            }
            JSONObject obj = new JSONObject();
            obj.put("dto", array);
            obj.put("profileId", Constants.CURRENT_USER.getUserId());
            Log.e("mmm", obj.toString());
            ((ParentActivity) context).httpPost(Constants.APP_USERINFO_URL, obj, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;


    }


    static class Value {
        String modifyName;
        String modifyValue;

        public Value(String modifyName, String modifyValue) {
            this.modifyName = modifyName;
            this.modifyValue = modifyValue;
        }
    }

}