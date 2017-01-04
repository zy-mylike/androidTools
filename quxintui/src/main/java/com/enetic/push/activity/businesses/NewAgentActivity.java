package com.enetic.push.activity.businesses;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.adapter.BusinessAdapter;
import com.enetic.push.bean.AgentBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_new_agent)
public class NewAgentActivity extends ParentActivity {

    private final static String TITLE = "新的代理";
    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        httpGet(Constants.URL_BUSINESS_READ + Constants.CURRENT_USER.getUserId(),new HttpCallback());

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
                String utl = Constants.URL_NEWAGENT_LIST+ Constants.CURRENT_USER.getUserId() + "&page=" + pager;
                httpGet(utl, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        List<AgentBean> list = null;
                        try {
                            list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<AgentBean>>() {
                            }.getType());
                            if (list == null) {
                                list = new ArrayList();
                            }
                            if (list.size() == 0) {
                                list.add(null);
                            }
                            BusinessAdapter adapter = (BusinessAdapter) handleData(pager, list, BusinessAdapter.class, follow);
                            adapter.setOnItemClickListener(new HolderAdapter.OnItemClickListener<AgentBean>() {
                                @Override
                                public void onItemClick(View view, AgentBean taskBean, int position) {
                                    startActivity(new Intent(getApplicationContext(), AgentDetalActivity.class).putExtra("type",1));
                                }
                            });
                        } catch (Exception w) {
                            {
                            }
                        }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                    }
                });
            }
        });
        pullToLoadView.initLoad();
    }
}
