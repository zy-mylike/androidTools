package com.enetic.push.share;

import android.content.Context;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;


/**
 * Created by json on 2016/06/25.
 */
public class ShareManage {

    /**
     * 分享产品
     *
     * @param context
     * @param utl
     * @param title
     * @param desp
     * @param thumImg
     * @param scene
     * @param url
     */
    public static void shareWeb(final Context context, String utl, String title, String desp, String thumImg, int scene, final String url) {
        ShareWX shareWX = new ShareWX(context);
        shareWX.setScene(scene);
        shareWX.addCall(new ShareResultCall() {
            @Override
            public void onShareSucess() {
                super.onShareSucess();
                HttpUtil.httpGet(context, url, new HttpCallback());
            }
        });

        shareWX.shareWeb(utl, title, desp, thumImg);
    }
}