package com.enetic.push.activity.agent;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.adapter.ChargeAdapter;
import com.enetic.push.bean.Bean;
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

@Layout(R.layout.activity_cash_record)
public class CashRecordActivity extends ParentActivity {
    private static String TITLE = "提现记录";

    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getIntExtra("type", 1) == 0) {
            TITLE = "充值记录";
        }
        setToolTitle(TITLE);
        pullToLoadView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view) != 0) {
                    outRect.top = getResources().getDimensionPixelSize(R.dimen.px_1);
                }
            }
        });
        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView) {
            @Override
            protected void requestData(final int pager, final boolean follow) {
                if (getIntent().getIntExtra("type", 1) == 0)
                    httpGet(Constants.HOST + "userPaycheck?userId=" + Constants.CURRENT_USER.getUserId() + "&pager=" + pager, new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);
                            try {
                                List<Bean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<Bean>>() {
                                }.getType());
                                if (list == null) {
                                    list = new ArrayList();
                                }
                                if (list.size() == 0) {
                                    list.add(null);
                                }
                                handleData(pager, list, ChargeAdapter.class, follow);
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
        pullToLoadView.initLoad();
    }
}