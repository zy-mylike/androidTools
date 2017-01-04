package com.enetic.push.activity.agent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.enetic.push.activity.SeacherActivity;
import com.enetic.push.adapter.TaskAdpter;
import com.enetic.push.bean.AgentTaskBean;
import com.enetic.push.bean.BusinessBean;
import com.enetic.push.dialog.ShareDialog;
import com.enetic.push.share.ShareManage;
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
 * 代理：任务页 商品列表
 */
@Layout(R.layout.activity_product_list)
public class ProductListActivity extends ParentActivity {

    private static final String TITLE = "商品列表";
    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;

    BusinessBean businessBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        businessBean=(BusinessBean)getIntent().getSerializableExtra("bussinessBean");
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
                httpGet(Constants.URL_SHOP_LIST +"?userId="+ getIntent().getStringExtra("id") +
                        "&page=" + pager, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        List<AgentTaskBean> list = null;
                        try {
                            list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<AgentTaskBean>>() {
                            }.getType());
                            if (list == null) {
                                list = new ArrayList();

                            }
                            if (list.size() == 0) {
                                list.add(null);
                            }
                            TaskAdpter adapter = (TaskAdpter) handleData(pager, list, TaskAdpter.class, follow);
                            adapter.setHandler(mHandler);
                            adapter.setDeleteVisibility(View.GONE);
                            adapter.setLl_shareVisibility(View.GONE);
                            adapter.setType(2);

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

    @TrackClick(value = R.id.layout_seacher, location = TITLE, eventName = "搜索")
    private void clickSeacher(View view) {
        Intent intent=new Intent(this,SeacherActivity.class);
        intent.putExtra("type",1);
        intent.putExtra("bussinessBean",businessBean);
        startActivity(intent);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final AgentTaskBean bean = (AgentTaskBean) msg.obj;
            switch (msg.what) {
                case 0:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProductListActivity.this);
                    builder.setMessage("确定要删除吗?").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            HttpUtil.httpGet(ProductListActivity.this, Constants.URL_AGENT_TASK_DELETE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getBusinessId() + "&productId=" + bean.getId(), new HttpCallback() {
                                @Override
                                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthSuccess(result, obj);
                                    try {
                                        Toast.makeText(ProductListActivity.this, obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthFailure(result, obj);
                                    try {
                                        Toast.makeText(ProductListActivity.this, obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();

                    break;
                case 1:    //       http%3A%2F%2Fesales.test.mobioa.cn%2F%23%2Ftab%2Fdetails%2F1_12_14

                    final ShareDialog dialog = new ShareDialog(ProductListActivity.this);
                    dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int scene = 1;
                            if (v.getId() == R.id.images) {
                                scene = 1;
                            } else if (v.getId() == R.id.take_images) {
                                scene = 2;
                            }
                            final String url = Constants.getProductShareUrl(Constants.CURRENT_USER.getUserId(), bean.getBusinessId() + "", bean.getId() + "");
                            String utl = Constants.URL_AGENT_ADDSHARE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getBusinessId() + "&productId=" + bean.getId();
                            ShareManage.shareWeb(ProductListActivity.this, url, bean.getName(), bean.getDesp(), bean.getIcon(), scene, utl);
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                    break;
            }
        }
    };
}
