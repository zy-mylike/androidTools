package com.enetic.push.activity.businesses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.bean.AgentBean;
import com.enetic.push.dialog.ShareDialog;
import com.enetic.push.share.ShareResultCall;
import com.enetic.push.share.ShareWX;
import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.annotation.TrackClick;
import org.json.JSONException;
import org.json.JSONObject;

/***
 * 代理详情页
 */
@Layout(R.layout.activity_agent_detal)
public class AgentDetalActivity extends ParentActivity {

    private final static String TITLE = "代理资料";

    @ViewIn(R.id.user_img)
    private ImageView mHeaderView;
    @ViewIn(R.id.user_name)
    private TextView mNameView;
    @ViewIn(R.id.proxy_num)
    private TextView mProxyNumText;
    @ViewIn(R.id.visit)
    private TextView mVisit;
    @ViewIn(R.id.product_sum)
    private TextView product_sum;

    @ViewIn(R.id.layout2)
    private View mLayout2;
    @ViewIn(R.id.layout)
    private View mLayout;
    @ViewIn(R.id.is_proxy)
    private View is_proxy;

    AgentBean taskBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        taskBean = (AgentBean) getIntent().getSerializableExtra("bean");
        setViews();
    }


    private void setViews() {
        new AsyncImageLoader(this, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(mHeaderView, taskBean.getPortrait());
        mNameView.setText(taskBean.getNickName());

        mProxyNumText.setText(taskBean.getBusinessCount()+"人");
        mVisit.setText(taskBean.getVisitCount()+"人");
        product_sum.setText(taskBean.getBusinessCount()+"元");

        //Toast.makeText(getApplicationContext(), taskBean.getIsFriend() + "", 0).show();
        if ("0".equals(taskBean.getIsFriend())) { //如果 未代理该商家
            mLayout2.setVisibility(View.GONE);
            mLayout.setVisibility(View.VISIBLE);
        } else {
            mLayout2.setVisibility(View.VISIBLE);
            mLayout.setVisibility(View.GONE);
        }
    }


    @TrackClick(value = R.id.layout4, location = TITLE, eventName = "加入黑名单")
    private void clickHei(View view) {
        httpGet(Constants.URL_BLACK_AGENT + taskBean.getUserId() + "&businessId=" + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
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

    @TrackClick(value = R.id.layout3, location = TITLE, eventName = "分享代理")
    private void clickShare(View view) {
        String URL = "http%3A%2F%2Fesales.test.mobioa.cn%2F%23%2Ftab%2Fdetails%2F" + 0 + "_" + 12 + "_" + 132;
        final String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd5a6d7e4a353b84c&redirect_uri=" + URL + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

        final ShareDialog dialog = new ShareDialog(this);
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareWX wx = new ShareWX(AgentDetalActivity.this);
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
                wx.shareWeb(url, taskBean.getNickName(), url, "");
            }
        });
        dialog.show();
    }

    @TrackClick(value = R.id.layout2, location = TITLE, eventName = "删除代理")
    private void clickDelete(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定删除?").setTitle("删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().dismiss();
                httpGet(Constants.URL_DELETE_AGENT + taskBean.getUserId() + "&businessId=" + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);

                        try {
                            Toast.makeText(getApplicationContext(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                            taskBean.setIsFriend("0");
                            setViews();
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

    @TrackClick(value = R.id.layout, location = TITLE, eventName = "添加代理")
    private void clickCancelAndadd(View view) {
        httpGet(Constants.URL_ADD_AGENT + taskBean.getUserId() + "&businessId=" + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    Toast.makeText(getApplicationContext(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                    taskBean.setIsFriend("1");
                    setViews();
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
}
