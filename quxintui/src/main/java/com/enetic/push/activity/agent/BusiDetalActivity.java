package com.enetic.push.activity.agent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.bean.BusinessBean;
import com.enetic.push.dialog.ShareDialog;
import com.enetic.push.share.ShareResultCall;
import com.enetic.push.share.ShareWX;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.annotation.TrackClick;
import org.json.JSONException;
import org.json.JSONObject;

@Layout(R.layout.activity_busi_detal)
public class BusiDetalActivity extends ParentActivity {

    private final static String TITLE = "商家资料";

    @ViewIn(R.id.user_img)
    private ImageView mHeaderView;
    @ViewIn(R.id.user_name)
    private TextView mNameView;
    @ViewIn(R.id.proxy_num)
    private TextView mProxyNumText;
    @ViewIn(R.id.product_sum)
    private TextView mProductNumText;

    @ViewIn(R.id.layout2)
    private View mLayout2;
    @ViewIn(R.id.layout)
    private View mLayout;
    @ViewIn(R.id.img)
    private ImageView mLayoutImg;
    @ViewIn(R.id.text)
    private TextView mLayoutText;

    BusinessBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        bean = (BusinessBean) getIntent().getSerializableExtra("bean");
        if ("0".equals(bean.getIsFriend())) {
            mLayout2.setVisibility(View.GONE);
            mLayoutImg.setImageResource(R.mipmap.icon_add_agent);
            mLayoutText.setText("申请代理");
        }

        httpGet(Constants.HOST + "user/" + bean.getUserId(), new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    obj = new JSONObject(obj.getString("data"));
                    new AsyncImageLoader(getApplicationContext(), R.mipmap.us_default_header, R.mipmap.us_default_header).display(mHeaderView, obj.getString("portrait"));
                    mNameView.setText(obj.getString("nickName"));
                    mProxyNumText.setText(obj.getString("agentCount"));
                    mProductNumText.setText(obj.getString("productCount"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @TrackClick(value = R.id.layout3, location = TITLE, eventName = "分享商家")
    private void clickShare(View view) {

        final String url = Constants.getProductShareUrl("0", "12", "132");
        final ShareDialog dialog = new ShareDialog(this);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareWX wx = new ShareWX(BusiDetalActivity.this);
                wx.addCall(new ShareResultCall() {
                    @Override
                    public void onShareSucess() {
                        super.onShareSucess();
                    }

                    @Override
                    public void onShareFailure(String errStr, int errCode) {
                        super.onShareFailure(errStr, errCode);
                    }

                    @Override
                    public void onShareCancel() {
                        super.onShareCancel();
                    }
                });
                if (v.getId() == R.id.images) {
                    wx.setScene(1);
                }
                if (v.getId() == R.id.take_images) {
                    wx.setScene(2);
                }
                dialog.dismiss();
                wx.shareWeb(url, bean.getNickName(), url, "");
            }
        });
        dialog.show();
    }

    @TrackClick(value = R.id.layout4, location = TITLE, eventName = "商品列表")
    private void clickList(View view) {
        Intent intent=new Intent(this,ProductListActivity.class);
        intent.putExtra("bussinessBean",bean);
        intent.putExtra("id", bean.getUserId());
        startActivity(intent);
    }

    @TrackClick(value = R.id.layout2, location = TITLE, eventName = "加入黑名单")
    private void clickHei(View view) {

        httpGet(Constants.URL_AGENT_BUSINESS_BLACK + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getUserId(), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    Toast.makeText(getApplicationContext(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                try {
                    Toast.makeText(getApplicationContext(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @TrackClick(value = R.id.layout, location = TITLE, eventName = "申请代理或者取消代理")
    private void clickCancelAndadd(View view) {
        if ("0".equals(bean.getIsFriend())) {
            HttpUtil.httpGet(getApplicationContext(), Constants.URL_AGENT_BUSINESS_ADD + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getUserId(), new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);

                    try {
                        Toast.makeText(getApplicationContext(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    try {
                        Toast.makeText(getApplicationContext(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定删除?").setTitle("删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().dismiss();
                httpGet(Constants.URL_AGENT_BUSINESS_DELETE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getUserId(), new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            Toast.makeText(getApplicationContext(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        try {
                            Toast.makeText(getApplicationContext(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().dismiss();
            }
        }).create().show();
    }
}
