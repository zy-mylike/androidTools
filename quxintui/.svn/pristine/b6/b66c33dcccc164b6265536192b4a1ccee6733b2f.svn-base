package com.enetic.push.activity.agent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.adapter.AgentNEwBusinessAdapter;
import com.enetic.push.bean.AgentBean;
import com.enetic.push.bean.BusinessBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 新的商家
 */
@Layout(R.layout.activity_new_business)
public class NewBusinessActivity extends ParentActivity {

    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle("新的商家");
        httpGet(Constants.URL_AGENT_BUSINESS_READ + Constants.CURRENT_USER.getUserId(), new HttpCallback());
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
                HttpUtil.httpGet(getApplicationContext(), Constants.URL_AGENT_BUSINESS_NEW + Constants.CURRENT_USER.getUserId() + "&page=" + pager, new HttpCallback() {

                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            List<BusinessBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<BusinessBean>>() {
                            }.getType());
                            if (list == null)
                                list = new ArrayList();
                            if (list.size() == 0)
                                list.add(null);
                            AgentNEwBusinessAdapter adapter = (AgentNEwBusinessAdapter) handleData(pager, list, AgentNEwBusinessAdapter.class, follow);
                            adapter.setHandler(mHandler);
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
        pullToLoadView.initLoad();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final BusinessBean bean = (BusinessBean) msg.obj;
            switch (msg.what) {
                case 1:

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
                    break;
                case 2: //删除
                    final AlertDialog.Builder builder = new AlertDialog.Builder(NewBusinessActivity.this);
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
                    break;
            }
        }
    };
}
