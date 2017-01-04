package com.enetic.push.activity.businesses;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.adapter.DealAdapter;
import com.enetic.push.adapter.DealListAdapter;
import com.enetic.push.bean.ShopOrder;
import com.enetic.push.bean.ShopOrderBean;
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

/**
 * Created by moon1 on 2016/6/13.
 */
@Layout(R.layout.activity_deal_list)
public class DealListActivity extends ParentActivity {

    @ViewIn(R.id.deal_lsit)
    private PullToLoadView mDealList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ShopOrder shopOrder = (ShopOrder) getIntent().getSerializableExtra("shopOrder");
        mDealList.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view) != 0){
                    outRect.top = getResources().getDimensionPixelSize(R.dimen.px_4);
                }
            }
        });
        mDealList.setPullCallback(new PullCallbackImpl(mDealList) {
            @Override
            protected void requestData(final int i, final boolean b) {

                httpGet(Constants.APP_shopOrders_URL+Constants.CURRENT_USER.getUserId()+"&productId="+shopOrder.id +"&pager="+i,new HttpCallback(){
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);

                        try {
                            List<ShopOrderBean> beanList = new Gson().fromJson(obj.getString("data"),new TypeToken<List<ShopOrderBean>>(){}.getType());

                            handleData(i, beanList, DealListAdapter.class, b);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        loadOK();
                    }
                });
            }
        });
        mDealList.initLoad();
    }
}
