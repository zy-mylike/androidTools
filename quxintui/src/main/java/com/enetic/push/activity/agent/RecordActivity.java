package com.enetic.push.activity.agent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.adapter.ProductAdapter;
import com.enetic.push.adapter.ProxyAdapter;
import com.enetic.push.bean.AgentShareBean;
import com.enetic.push.bean.ProductBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_record)
public class RecordActivity extends ParentActivity {


    private final static String TITLE = "推广记录";
    @ViewIn(R.id.proxy_listed)
    private PullToLoadView mProxyListed;

    ProxyAdapter proxyAdapter;

    @ViewIn(R.id.comment)
    public TextView comment;  //编辑

    @ViewIn(R.id.ll_chose)
    public LinearLayout ll_chose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolTitle(TITLE);
        setToolRight("编辑");
        final List list2 = new ArrayList();


        mProxyListed.setPullCallback(new PullCallbackImpl(mProxyListed) {
            @Override
            protected void requestData(final int pager, final boolean follow) {
                httpGet(Constants.URL_AGENT_SHARE_LIST + Constants.CURRENT_USER.getUserId() + "&page=" + pager, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            List<AgentShareBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<AgentShareBean>>() {
                            }.getType());
                            if (list == null) {
                                list = new ArrayList();
                            }
                            if (list.size() == 0) {
                                list.add(null);
                            }
                            proxyAdapter = (ProxyAdapter) handleData(pager, list, ProxyAdapter.class, follow);
                            proxyAdapter.setHandler(mHandler);
                            //设置item_chose_yes_no 是否显示。
                            proxyAdapter.setShow(false);
                            //设置是否全选。
                            proxyAdapter.setChoseall(false);
                            if (list.size() == 0) {
                                comment.setEnabled(false);
                                proxyAdapter.setListData(list);
                            } else {
                                comment.setEnabled(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                    }

                    @Override
                    public void doRequestFailure(Exception exception, String msg) {
                        super.doRequestFailure(exception, msg);
                    }
                });

            }
        });
        mProxyListed.initLoad();
    }

    @TrackClick(value = R.id.comment, location = TITLE, eventName = "编辑")
    private void clickComment(View view) {
        String str = comment.getText().toString().trim();
        if (str.equals("编辑")) {
            comment.setText("完成");
            ll_chose.setVisibility(View.VISIBLE);
            proxyAdapter.setShow(true);
            proxyAdapter.notifyDataSetChanged();
        } else {
            comment.setText("编辑");
            ll_chose.setVisibility(View.GONE);
            proxyAdapter.setShow(false);
            proxyAdapter.notifyDataSetChanged();
        }
    }

    @TrackClick(value = R.id.tb_chose_cancel, location = TITLE, eventName = "全选")
    private void clickChoseCancle(ToggleButton view) {
        if (view.isChecked()) {
            proxyAdapter.setChoseall(true);
        } else {
            proxyAdapter.setChoseall(false);
        }
        proxyAdapter.notifyDataSetChanged();
    }

    @TrackClick(value = R.id.tb_deleteall, location = TITLE, eventName = "删除")
    private void clickDeleteAll(ToggleButton view) {

        String str = proxyAdapter.getListString();
        //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
        mHandler.obtainMessage(0, str).sendToTarget();
        // 实现点击编辑按钮
        comment.performClick();

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final String str = (String) msg.obj;
            switch (msg.what) {
                case 0: //删除(多条：包含单条)
                    //final AgentShareBean bean = (AgentShareBean) msg.obj;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RecordActivity.this);
                    builder.setMessage("确定要删除吗?").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            httpGet(Constants.URL_SHARE_RECOR_DELETE_AGENT + "shareId=" + str, new HttpCallback() {
                                @Override
                                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthSuccess(result, obj);
                                    try {
                                        mProxyListed.initLoad(); //列表重新加载
                                        Toast.makeText(RecordActivity.this, obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthFailure(result, obj);
                                    try {
                                        Toast.makeText(RecordActivity.this, obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void doRequestFailure(Exception exception, String msg) {
                                    super.doRequestFailure(exception, msg);
                                    try {
                                        Toast.makeText(RecordActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
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
                case 1:
                    break;
                default:
                    break;
            }
        }
    };


}