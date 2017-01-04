package com.enetic.push.activity.businesses;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.adapter.DealAdapter;
import com.enetic.push.bean.ShopOrder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moon1 on 2016/6/8.
 */
@Layout(R.layout.activity_mine_deal)
public class MineDealActivity extends ParentActivity {

    @ViewIn(R.id.deal_lsit)
    private PullToLoadView mDealList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle("我的交易");
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
                httpGet(Constants.APP_shopOrder_URL+"170"/*Constants.CURRENT_USER.getUserId()*/+"pager="+i,new HttpCallback(){
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        List<ShopOrder> list = null;
                        try {
                            list = new Gson().fromJson(obj.getString("data"),new TypeToken<List<ShopOrder>>(){}.getType());
                            if (list == null) {
                                list = new ArrayList();
                            }
                            if (list.size() == 0) {
                                list.add(null);
                            }
                            HolderAdapter adapter = handleData(i, list, DealAdapter.class, b);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                    }
                });



            }
        });
        mDealList.initLoad();
    }
}
