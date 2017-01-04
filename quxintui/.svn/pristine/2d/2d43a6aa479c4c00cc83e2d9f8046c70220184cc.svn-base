package com.enetic.push.fragment.agent;

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
import com.enetic.push.adapter.TaskAdpter;
import com.enetic.push.bean.AgentTaskBean;
import com.enetic.push.dialog.ShareDialog;
import com.enetic.push.share.ShareResultCall;
import com.enetic.push.share.ShareWX;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.fragment.TrackFragment;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2016/4/1.
 */
@Layout(R.layout.fragment_task)
public class TaskFragment extends TrackFragment {

    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;

    @Override
    protected void setDatas(final Bundle bundle) {
        pullToLoadView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildPosition(view) != 0) {
                    outRect.top = getResources().getDimensionPixelSize(R.dimen.px_4);
                }
            }
        });
        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView) {
            @Override
            protected void requestData(final int i, final boolean b) {
                HttpUtil.httpGet(getActivity(), Constants.URL_AGENT_TASK_LIST + Constants.CURRENT_USER.getUserId() + "&page=" + i, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            List<AgentTaskBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<AgentTaskBean>>() {
                            }.getType());
                            if (list == null) {
                                list = new ArrayList();

                            }
                            if (list.size() == 0) {
                                list.add(null);
                            }
                            TaskAdpter adpter = (TaskAdpter) handleData(i, list, TaskAdpter.class, b);
                            adpter.setType(1);
                            adpter.setHandler(mHandler);
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

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final AgentTaskBean bean = (AgentTaskBean) msg.obj;
            switch (msg.what) {
                case 0:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("确定要删除吗?").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            HttpUtil.httpGet(getActivity(), Constants.URL_AGENT_TASK_DELETE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getBusinessId() + "&productId=" + bean.getId(), new HttpCallback() {
                                @Override
                                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthSuccess(result, obj);
                                    try {
                                        pullToLoadView.initLoad();
                                        if ("1000".equals(obj.getString("code"))) {
                                            Toast.makeText(getActivity(), "\u5220\u9664\u6210\u529f", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "\u5220\u9664\u5931\u8d25", Toast.LENGTH_SHORT).show();
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthFailure(result, obj);
                                    Toast.makeText(getActivity(), "\u5220\u9664\u5931\u8d25", Toast.LENGTH_SHORT).show();
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
                    final ShareDialog dialog = new ShareDialog(getActivity());
                    dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShareWX wx = new ShareWX(getActivity());
                            wx.addCall(new ShareResultCall() {
                                @Override
                                public void onShareSucess() {
                                    super.onShareSucess();
                                    String utl = Constants.URL_AGENT_ADDSHARE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getBusinessId() + "&productId=" + bean.getId();
                                    HttpUtil.httpGet(getActivity(), utl, new HttpCallback());
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
                            wx.shareWeb(Constants.getProductShareUrl(Constants.CURRENT_USER.getUserId(), bean.getBusinessId() + "", bean.getId() + ""), bean.getName(), bean.getDesp(), "");
                        }
                    });
                    dialog.show();
                    break;
                case 2: //分享至朋友圈。
                    ShareWX wx = new ShareWX(getActivity());
                    wx.addCall(new ShareResultCall() {
                        @Override
                        public void onShareSucess() {
                            super.onShareSucess();
                            String utl = Constants.URL_AGENT_ADDSHARE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getBusinessId() + "&productId=" + bean.getId();
                            HttpUtil.httpGet(getActivity(), utl, new HttpCallback());
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
                    wx.setScene(2);
                    wx.shareWeb(Constants.getProductShareUrl(Constants.CURRENT_USER.getUserId(), bean.getBusinessId() + "", bean.getId() + ""), bean.getName(), bean.getDesp(), "");
                    break;
                case 3: //分享至微信好友。
                    ShareWX wx_agent = new ShareWX(getActivity());
                    wx_agent.addCall(new ShareResultCall() {
                        @Override
                        public void onShareSucess() {
                            super.onShareSucess();
                            String utl = Constants.URL_AGENT_ADDSHARE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getBusinessId() + "&productId=" + bean.getId();
                            HttpUtil.httpGet(getActivity(), utl, new HttpCallback());
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
                    wx_agent.setScene(1);
                    wx_agent.shareWeb(Constants.getProductShareUrl(Constants.CURRENT_USER.getUserId(), bean.getBusinessId() + "", bean.getId() + ""), bean.getName(), bean.getDesp(), "");
                    break;
            }
        }
    };
}