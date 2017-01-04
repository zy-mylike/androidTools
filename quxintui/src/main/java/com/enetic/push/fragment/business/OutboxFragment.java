package com.enetic.push.fragment.business;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.SeacherActivity;
import com.enetic.push.activity.businesses.SelectAgentActivity;
import com.enetic.push.adapter.BoxAdapter;
import com.enetic.push.bean.TaskBean;
import com.enetic.push.dialog.AddProxyDialog;
import com.enetic.push.share.ShareManage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.track.fragment.TrackFragment;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.fragment_outbox)
public class OutboxFragment extends TrackFragment {

    private final static String TITLE = "发件箱";
    @ViewIn(R.id.add_product)
    private TextView mAddBox;
    @ViewIn(R.id.layout_null)
    private View mNullLayout;
    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;

    @Override
    protected void setDatas(Bundle bundle) {
        mAddBox.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mNullLayout.setVisibility(View.VISIBLE);


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
            protected void requestData(final int i, final boolean b) {
                String utl = Constants.URL_TASK_LIST + Constants.CURRENT_USER.getUserId() + "&page=" + i;
                HttpUtil.httpGet(getActivity(), utl, new HttpCallback() {

                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        List<TaskBean> list = null;
                        loadOK();
                        try {
                            list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<TaskBean>>() {
                            }.getType());
                            if (list == null) {
                                list = new ArrayList();
                            }

                            if (list.size() == 0) {
                                list.add(null);
                            }
                            BoxAdapter adapter = (BoxAdapter) handleData(i, list, BoxAdapter.class, b);
                            if (adapter == null) {
                                return;
                            }
                            if (list.size() == 0 || list.get(0) == null) { //解决 最后一条记录删除时，页面不刷新的问题。
                                adapter.setListData(list);
                            }
                            adapter.setHandler(mHandler);
                            if (pullToLoadView.getRecyclerView().getAdapter().getItemCount() <= 0)
                                mNullLayout.setVisibility(View.GONE);
                            else {
                                mNullLayout.setVisibility(View.VISIBLE);
                            }
                            mNullLayout.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        loadOK();
                        mNullLayout.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    @TrackClick(value = R.id.layout_seacher, location = "首页", eventName = "搜索")
    public void clickSeacher(View view) {
        startActivity(new Intent(getActivity(), SeacherActivity.class).putExtra("type", 5));
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final TaskBean bean = (TaskBean) msg.obj;
            switch (msg.what) {
                case 2:
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("确定删除?").setTitle("删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().dismiss();
                            HttpUtil.httpGet(getActivity(), Constants.URL_TASK_DELETE + Constants.CURRENT_USER.getUserId() + "&productId=" + bean.getProductId(), new HttpCallback() {
                                @Override
                                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthSuccess(result, obj);
                                    pullToLoadView.initLoad();
                                    try {
                                        Toast.makeText(getActivity(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthFailure(result, obj);
                                    try {
                                        Toast.makeText(getActivity(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
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
                case 3:
                    final AddProxyDialog dialog = new AddProxyDialog(getActivity());
                    dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            switch (v.getId()) {
                                case R.id.wx:
                                case R.id.share_wx:
                                    int scene = 1;
                                    String utl = Constants.getProductShareUrl("0", Constants.CURRENT_USER.getUserId(), bean.getProductId() + "");
                                    String url = Constants.URL_BUSINESS_SHARE_ADD + Constants.CURRENT_USER.getUserId() + "&productId=" + bean.getProductId();
                                    if (v.getId() == R.id.wx) {
                                        scene = 1;
                                    } else if (v.getId() == R.id.share_wx) {
                                        scene = 2;
                                    }
                                    ShareManage.shareWeb(getActivity(), utl, bean.getName(), utl, "", scene, url);
                                    break;
                                case R.id.take_images:
                                    startActivity(new Intent(getActivity(), SelectAgentActivity.class).putExtra("id", bean.getProductId() + ""));

                                    break;
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                    break;
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        pullToLoadView.initLoad();
    }
}