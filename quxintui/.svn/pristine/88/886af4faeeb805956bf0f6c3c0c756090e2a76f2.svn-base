package com.enetic.push.activity.businesses;

import android.os.Bundle;
import android.view.View;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.adapter.CouponsAdapter;
import com.enetic.push.bean.CouponBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_coupons)
public class CouponsActivity extends ParentActivity {
    private final static String TITLE = "优惠卷";
    @ViewIn(R.id.layout_null)
    private View mNullLayout;
    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        mNullLayout.setVisibility(View.GONE);

        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView) {
            @Override
            protected void requestData(final int pager, final boolean follow) {
                httpGet(Constants.APP_COUPON_URL+Constants.CURRENT_USER.getUserId()+"&page="+pager+"&size=20",new HttpCallback(){
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                           String data = obj.getString("data");


                            List<CouponBean> datas = new Gson().fromJson(data,new TypeToken<List<CouponBean>>(){}.getType());
                            if (datas == null) {
                                datas = new ArrayList();
                            }
                            if (datas.size() == 0) {
                                datas.add(null);
                            }
                            handleData(pager,datas, CouponsAdapter.class,follow);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        pullToLoadView.initLoad();
    }
}
