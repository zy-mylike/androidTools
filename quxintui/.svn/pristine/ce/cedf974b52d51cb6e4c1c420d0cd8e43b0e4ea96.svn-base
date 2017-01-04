package com.enetic.push.activity.businesses;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.bean.IMGBean;
import com.enetic.push.bean.ProductBean;
import com.enetic.push.dialog.LoadingDialog;
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

@Layout(R.layout.activity_business_product_detail)
public class ProductDetailActivity extends ParentActivity {

    private final static String TITLE = "商品详情";
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

    private ProductBean mProBean;
    ArrayList<IMGBean> imgs;

    private LoadingDialog dialog;

    @Override
    public void setToolTitle(String mTitle) {
        super.setToolTitle(mTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        setToolRight(R.mipmap.nav_more);
        dialog = new LoadingDialog(this);
        dialog.show(-1);

        //根据商品ID，获取商品详情。
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

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
                if (dialog != null) {
                    dialog.dismiss();
                }

                Toast.makeText(ProductDetailActivity.this, "商品详情请求失败！", Toast.LENGTH_SHORT).show();

            }



        });

        //根据商品ID，获取图片。（获取所有图片的URL路径），AsyncImageLoader再根据URL加载图片。
        httpGet(Constants.URL_DETAIL_IMG_PRODUCT + getIntent().getStringExtra("id"), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    imgs = new Gson().fromJson(obj.getString("data"), new TypeToken<List<IMGBean>>() {
                    }.getType());
                    List<View> bannerViews = new ArrayList<View>();

                    if(imgs.size()==0){
                        ImageView v = new ImageView(ProductDetailActivity.this);
                        v.setScaleType(ImageView.ScaleType.FIT_XY);
                        new AsyncImageLoader(ProductDetailActivity.this, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(v, "");
                        bannerViews.add(v);
                    }else {
                        for (int i = 0; i < (imgs.size()); i++) {
                            ImageView v = new ImageView(ProductDetailActivity.this);
                            v.setScaleType(ImageView.ScaleType.FIT_XY);
                            final IMGBean bean = imgs.get(i);
                            new AsyncImageLoader(ProductDetailActivity.this, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(v, bean.url);
                            bannerViews.add(v);
                        }
                    }

                    bannerView.setViewPagerViews(bannerViews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void setViews() {
        if(dialog!=null){
            dialog.dismiss();
        }
        mProName.setText(mProBean.getName());
        mProPrice.setText("￥ " + mProBean.getPrice() + "元");
        mSellNums.setText(mProBean.getSellCount()+"件");
        mShareNums.setText(mProBean.getShareCount() + "次");
        product_desp.setText(mProBean.getDesp());//商品描述
        mSharereward.setText(mProBean.getShareReward() + "元");
        mDealreward.setText(mProBean.getOrderReward() + "%");
    }


    @TrackClick(value = R.id.share_wx, location = TITLE, eventName = "分享微信好友")
    private void clickShareWx(View view) {
        String utl = Constants.getProductShareUrl("0", Constants.CURRENT_USER.getUserId(), mProBean.getId());
        String url = Constants.URL_BUSINESS_SHARE_ADD + Constants.CURRENT_USER.getUserId() + "&productId=" + mProBean.getId();
        int scene = 1;
        ShareManage.shareWeb(ProductDetailActivity.this, utl, mProBean.getName(), mProBean.getDesp(), "", scene, url);
    }

    @TrackClick(value = R.id.share_w_friend, location = TITLE, eventName = "分享微信好友")
    private void clickShareWxF(View view) {
        String utl = Constants.getProductShareUrl("0", Constants.CURRENT_USER.getUserId(), mProBean.getId());
        String url = Constants.URL_BUSINESS_SHARE_ADD + Constants.CURRENT_USER.getUserId() + "&productId=" + mProBean.getId();
        int scene = 2;
        ShareManage.shareWeb(ProductDetailActivity.this, utl, mProBean.getName(), mProBean.getDesp(), "", scene, url);
    }

    @TrackClick(value = R.id.share_agent, location = TITLE, eventName = "分享至代理")
    private void clickShageAgengt(View view) {
        startActivity(new Intent(this, SelectAgentActivity.class).putExtra("id", mProBean.getId() + "")); //ProductBean
    }

    //tooltitle_right
    @TrackClick(value = R.id.tooltitle_right, location = TITLE, eventName = "更多")
    private void clickMore(View view) {
        if (mProBean != null)
            showLogout(view);
        else {

        }
    }

    private void showLogout(View view) {
        final PopupWindow builder = new PopupWindow(this);
        View layout = getLayoutInflater().inflate(R.layout.layout_search_type, null);
        builder.setBackgroundDrawable(new BitmapDrawable());
        builder.setFocusable(true);
        builder.setOutsideTouchable(true);
        builder.setContentView(layout);
        builder.setWidth(-2);
        builder.setHeight(-2);

        int[] location = new int[2];
        view.getLocationOnScreen(location);


        //设置显示的位置  xoffset 应该通过界面计算得出。
        builder.showAsDropDown(view,-80,15);


        TextView mPopBtnOne = (TextView) layout.findViewById(R.id.carToon1);//编辑
        TextView mPopBtnTwo = (TextView) layout.findViewById(R.id.carToon2);//删除

        mPopBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                Intent intent=new Intent(ProductDetailActivity.this, ProductEditActivity.class);
                intent.putExtra("bean",mProBean);
                intent.putExtra("imgs", imgs);
                intent.putExtra("startFrom","edit");
                startActivity(intent);
                finish();
            }
        });

        mPopBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                httpGet(Constants.URL_PRODUCT_DELETE + mProBean.getId(), new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        try {
                            String code=obj.getString("code");
                            String msg="删除失败";
                            if(code.equals("2000")){
                                msg="已分享至代理的商品不能删除";
                            }
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        builder.showAsDropDown(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}