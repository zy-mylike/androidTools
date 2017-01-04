package com.enetic.push.activity;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enetic.esale.R;
import com.lidroid.xutils.http.RequestParams;

import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.TrackApplication;
import org.eteclab.ui.activity.MaterialActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.IdentityHashMap;
import java.util.List;

public class ParentActivity extends MaterialActivity {

    @ViewIn(R.id.toolbar)
    protected Toolbar mToolBar;

    @ViewIn(R.id.toolbar_title)
    private TextView mTitle;

    @ViewIn(R.id.comment)
    private TextView mComment;

    @ViewIn(R.id.tooltitle_right)
    private ImageView mTitleRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            setTitle("");
        }
    }


    public void setToolTitle(String mTitle) {
        if (this.mTitle != null)
            if(mTitle.equals("首页")){
                this.mTitle.setText("商品");
            }else{
                this.mTitle.setText(mTitle);
            }

    }

    protected View setToolRight(@DrawableRes int resId) {
        if (this.mTitleRight != null) {
            this.mTitleRight.setVisibility(resId == 0 ? View.GONE : View.VISIBLE);
            this.mTitleRight.setImageResource(resId);
            return mTitleRight;
        }
        return null;
    }

    protected View setToolRight(String text) {
        if (this.mComment != null) {
            this.mTitleRight.setVisibility(View.GONE);
            this.mComment.setText(text);
            this.mComment.setVisibility(View.VISIBLE);
            return mComment;
        }
        return null;
    }

    public void httpGet(String url, final HttpCallback callback) {
//        HttpUtil.httpGet(this, url, TrackApplication.REQUEST_HEADER, callback);
        JSONObject object = new JSONObject();
        try {
            object.put("Content-Type", "application/json");
            HttpUtil.httpGet(this, url, object, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void httpDownload(String url, String savePath, final HttpCallback callback) {
        HttpUtil.httpDownload(this, url, savePath, callback);
    }

    public void httpUpload(String url, JSONObject body, File file, final HttpCallback callback) {
        IdentityHashMap<String, File> files = new IdentityHashMap<>();
        files.put("file", file);
        HttpUtil.httpUpload(this, url, body, files, callback);
    }

    public void httpUpload(String url, JSONObject body, List<File> fileList, final HttpCallback callback) {
        RequestParams params = new RequestParams();
//        params.add
        IdentityHashMap<String, File> files = new IdentityHashMap<>();
        for (int i = 0; i < 9; i++) {
            if (i < fileList.size()) {
                files.put("file" + (i + 1), fileList.get(i));
            }
        }
        HttpUtil.httpUpload(this, url, body, files, callback);
    }

    public void httpPost(String url, JSONObject body, final HttpCallback callback) {
//
        JSONObject object = new JSONObject();
        try {
            object.put("Content-Type", "application/json");
            HttpUtil.httpPost(this, url, body, object, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
