package com.enetic.push.activity.agent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.bean.IMGBean;
import com.enetic.push.bean.ProductBean;
import com.enetic.push.dialog.ShareDialog;
import com.enetic.push.share.ShareManage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.viewpager.BannerView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_product_detail)
public class ProductDetailActivity extends ParentActivity {

    private static final String TITLE = "任务详情";
    @ViewIn(R.id.product_name)
    private TextView mProName;
    @ViewIn(R.id.product_price)
    private TextView mProPrice;

    @ViewIn(R.id.sell_nums)
    private TextView mSellNums;
    @ViewIn(R.id.share_nums)
    private TextView mShareNums;

    @ViewIn(R.id.deal_reward)
    private TextView mDealreward;
    @ViewIn(R.id.share_reward)
    private TextView mSharereward;

    @ViewIn(R.id.product_desp)
    private TextView product_desp;

    @ViewIn(R.id.banner)
    private BannerView bannerView;
    ArrayList<IMGBean> imgs;
    private ProductBean mProBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        httpGet(Constants.URL_DETAIL_PRODUCT + getIntent().getStringExtra("id"), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    mProBean = new Gson().fromJson(obj.getString("data"), ProductBean.class);
                    setViews();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });
        httpGet(Constants.URL_DETAIL_IMG_PRODUCT + getIntent().getStringExtra("id"), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    imgs = new Gson().fromJson(obj.getString("data"), new TypeToken<List<IMGBean>>() {
                    }.getType());
                    List<View> bannerViews = new ArrayList<View>();
                    for (int i = 0; i < (imgs.size()); i++) {
                        ImageView v = new ImageView(ProductDetailActivity.this);
                        v.setScaleType(ImageView.ScaleType.FIT_XY);
                        final IMGBean bean = imgs.get(i);
                        new AsyncImageLoader(ProductDetailActivity.this, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(v, bean.url);
                        bannerViews.add(v);
                    }
                    bannerView.setViewPagerViews(bannerViews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void setViews() {
        mProName.setText(mProBean.getName());
        mProPrice.setText(mProBean.getPrice());
        mSellNums.setText(mProBean.getSellCount());
        mShareNums.setText(mProBean.getShareCount());
        product_desp.setText(mProBean.getDesp());


        mSharereward.setText(mProBean.getShareReward());
        mDealreward.setText(mProBean.getOrderReward());
    }

    @TrackClick(value = R.id.share_wx, location = TITLE, eventName = "分享至微信")
    private void clickShareWx(View view) {
        final ShareDialog dialog = new ShareDialog(this);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String utl = Constants.getProductShareUrl(Constants.CURRENT_USER.getUserId(), mProBean.getBusinessId(), mProBean.getId());
                String url = Constants.URL_AGENT_ADDSHARE + Constants.CURRENT_USER.getUserId() + "&businessId=" + mProBean.getBusinessId() + "&productId=" + mProBean.getId();
                int scene = 1;
                if (v.getId() == R.id.images) {
                    scene = 1;
                } else if (v.getId() == R.id.take_images) {
                    scene = 2;
                }
                ShareManage.shareWeb(ProductDetailActivity.this, utl, mProBean.getName(), mProBean.getDesp(), "", scene, url);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    @TrackClick(value = R.id.tv_share_friends,location = TITLE,eventName = "分享至朋友圈")
    private void shareToFriends(View view){
        String utl = Constants.getProductShareUrl(Constants.CURRENT_USER.getUserId(), mProBean.getBusinessId(), mProBean.getId());
        String url = Constants.URL_AGENT_ADDSHARE + Constants.CURRENT_USER.getUserId() + "&businessId=" + mProBean.getBusinessId() + "&productId=" + mProBean.getId();
        int scene = 2;
        ShareManage.shareWeb(ProductDetailActivity.this, utl, mProBean.getName(), mProBean.getDesp(), "", scene, url);

    }

    @TrackClick(value = R.id.tv_share_agent,location = TITLE,eventName = "分享至微信好友")
    private void shareToAgent(View view){
        String utl = Constants.getProductShareUrl(Constants.CURRENT_USER.getUserId(), mProBean.getBusinessId(), mProBean.getId());
        String url = Constants.URL_AGENT_ADDSHARE + Constants.CURRENT_USER.getUserId() + "&businessId=" + mProBean.getBusinessId() + "&productId=" + mProBean.getId();
        int scene = 1;
        ShareManage.shareWeb(ProductDetailActivity.this, utl, mProBean.getName(), mProBean.getDesp(), "", scene, url);
    }

}