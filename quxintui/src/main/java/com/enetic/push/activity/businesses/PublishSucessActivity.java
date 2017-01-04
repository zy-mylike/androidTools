package com.enetic.push.activity.businesses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.bean.ProductBean;
import com.enetic.push.images.util.FileUtils;
import com.enetic.push.share.ShareManage;
import com.enetic.push.share.ShareResultCall;
import com.enetic.push.share.ShareWX;
import com.google.gson.Gson;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.annotation.TrackClick;


/**
 * 发布成功
 */
@Layout(R.layout.activity_publish_sucess)
public class PublishSucessActivity extends ParentActivity {

    private final static String TITLE = "发布成功";

    private ProductBean mProBean;

    public String startFrom;//从新增商品 或 编辑商品 进入此页面。


    @ViewIn(R.id.tv_title)
    private TextView tv_title;
    @ViewIn(R.id.tv_desc)
    private TextView tv_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startFrom=getIntent().getStringExtra("startFrom");
        if(startFrom.equals("add")){
            setToolTitle(TITLE);
            tv_title.setText("已成功添加商品");
            tv_desc.setText("商品已成功上架，你现在可以选择一键分享至朋友圈或生成推广链接发给您的渠道代理。");
        }else { //"edit"
            setToolTitle("编辑成功");
            tv_title.setText("已成功编辑商品");
            tv_desc.setText("商品已编辑成功，你现在可以选择一键分享至朋友圈或生成推广链接发给您的渠道代理。");
        }



        mToolBar.setNavigationIcon(null);
        TextView mLeftTitle = (TextView) mToolBar.findViewById(R.id.toolleft_title);
        mLeftTitle.setText("完成");
        String datas = getIntent().getStringExtra("datas");
        mProBean = new Gson().fromJson(datas,ProductBean.class);
        FileUtils.deleteDir();
    }


    @TrackClick(value = R.id.toolleft_title, location = TITLE, eventName = "完成")
    private void clickFinish(View view) {
        finish();
    }

    @TrackClick(value = R.id.wx, location = TITLE, eventName = "分享微信好友")
    private void clickShare3(View view) {
        int scene = 1;
        String utl = Constants.getProductShareUrl("0", Constants.CURRENT_USER.getUserId(), mProBean.getId());
        String url = Constants.URL_BUSINESS_SHARE_ADD + Constants.CURRENT_USER.getUserId() + "&productId=" + mProBean.getId();
        ShareManage.shareWeb(this, utl, getIntent().getStringExtra("name"), utl, "", scene, url);
    }

    @TrackClick(value = R.id.share_wx, location = TITLE, eventName = "分享至朋友圈")
    private void clickShare2(View view) {
        int scene = 2;
        String utl = Constants.getProductShareUrl("0", Constants.CURRENT_USER.getUserId(), mProBean.getId() + "");
        String url = Constants.URL_BUSINESS_SHARE_ADD + Constants.CURRENT_USER.getUserId() + "&productId=" + mProBean.getId();
        ShareManage.shareWeb(this, utl, getIntent().getStringExtra("name"), utl, "", scene, url);
    }

    @TrackClick(value = R.id.send_proxy, location = TITLE, eventName = "发送至代理")
    private void clickShare4(View view) {
        startActivity(new Intent(getApplicationContext(), SelectAgentActivity.class).putExtra("id", mProBean.getId()+""));
        finish();
    }

    @TrackClick(value = R.id.add_proxy, location = TITLE, eventName = "继续添加")
    private void clickShare1(View view) {

        startActivity(new Intent(getApplicationContext(), ProductEditActivity.class).putExtra("startFrom","add"));
        finish();
    }
}