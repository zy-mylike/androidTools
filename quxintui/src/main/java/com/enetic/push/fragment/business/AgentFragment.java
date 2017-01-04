package com.enetic.push.fragment.business;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.SeacherActivity;
import com.enetic.push.activity.businesses.AgentAllActivity;
import com.enetic.push.activity.businesses.AgentDetalActivity;
import com.enetic.push.activity.businesses.NewAgentActivity;
import com.enetic.push.adapter.SortAgentAdapter;
import com.enetic.push.bean.AgentBean;
import com.enetic.push.share.ShareWX;
import com.enetic.push.utils.CharacterParser;
import com.enetic.push.view.SideBarView;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by json on 2016/4/1.
 */
@Layout(R.layout.fragment_agent)
public class AgentFragment extends TrackFragment {
    @ViewIn(R.id.country_lvcountry)
    private PullToLoadView mlistView;

    SortAgentAdapter adapter;

    @ViewIn(R.id.sidrbar)
    SideBarView sideBar; //布局中隐藏掉，但相关代码还有其功能。若需要，再显示出此控件即可。

    @ViewIn(R.id.dialog)
    TextView dialog;

    @TrackClick(value = R.id.layout_seacher, location = "首页", eventName = "搜索")
    public void clickSeacher(View view) {
        startActivity(new Intent(getActivity(), SeacherActivity.class).putExtra("type", 6));
    }

    @Override
    protected void setDatas(Bundle bundle) {
        sideBar.setTextView(dialog);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBarView.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                if (adapter == null) {
                    return;
                }
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mlistView.getRecyclerView().scrollToPosition(position);
                }
            }
        });




        mlistView.setPullCallback(new PullCallbackImpl(mlistView) {
            @Override
            protected void requestData(final int pager, final boolean follow) {
                String utl = Constants.URL_AGENT_LIST + Constants.CURRENT_USER.getUserId() + "&page=" + pager;
                HttpUtil.httpGet(getActivity(), utl, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        List<AgentBean> list = null;
                        try {
                            list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<AgentBean>>() {
                            }.getType());
                            Collections.sort(list, new PinyinComparator()); //先对list进行排序，再传入adapter。
                            adapter = (SortAgentAdapter) handleData(pager, list, SortAgentAdapter.class, follow);
                            adapter.setHandler(mHandler);
                            adapter.setType(0);
                            adapter.setOnItemClickListener(new HolderAdapter.OnItemClickListener<AgentBean>() {
                                @Override
                                public void onItemClick(View view, AgentBean taskBean, int position) {
                                    startActivity(new Intent(getActivity(), AgentDetalActivity.class).putExtra("bean", taskBean));
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
                        loadOK();
                    }
                });
            }
        });

    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                final AgentBean bean = (AgentBean) msg.obj;
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定删除?").setTitle("删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.create().dismiss();
                        HttpUtil.httpGet(getActivity(), Constants.URL_DELETE_AGENT + bean.getUserId() + "&businessId=" + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
                            @Override
                            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                super.doAuthSuccess(result, obj);
                                adapter.removeItem(bean);
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

            }

        }
    };


    @Override
    public void onResume() {
        super.onResume();
        mlistView.initLoad();
    }

    @TrackClick(value = R.id.new_layout, location = "代理", eventName = "新代理")
    private void clickNew(View view) {
        startActivity(new Intent(getActivity(), NewAgentActivity.class));

    }

    @TrackClick(value = R.id.share_Circle, location = "代理", eventName = "分享至朋友圈")
    private void clickCircle(View view) {
        ShareWX wx = new ShareWX(getActivity());
        wx.setScene(2);
        wx.shareWeb("http://www.baidu.com", "邀请代理", "http://www.baidu.com", "");
    }

    @TrackClick(value = R.id.share_friend, location = "代理", eventName = "分享至微信好友")
    private void clickFriend(View view) {
        ShareWX wx = new ShareWX(getActivity());
        wx.setScene(1);
        wx.shareWeb("http://www.baidu.com", "邀请代理", "http://www.baidu.com", "");
    }

    @TrackClick(value = R.id.share_agent, location = "代理", eventName = "添加已注册代理")
    private void clickAgent(View view) {
        startActivity(new Intent(getActivity(), AgentAllActivity.class));
    }




    public class PinyinComparator implements Comparator<AgentBean> {
        public int compare(AgentBean o1, AgentBean o2) {

            String s1 = getChar(o1.getNickName());
            String s2 = getChar(o2.getNickName());

            if (s1.equals("@")
                    || s2.equals("#")) {
                return -1;
            } else if (s1.equals("#")
                    || s2.equals("@")) {
                return 1;
            } else {
                return s1.compareTo(s2);
            }
        }
    }

    private String getChar(String name) {
        CharacterParser characterParser = new CharacterParser();
        String pinyin = characterParser.getSelling(name);
        String sortString = pinyin.substring(0, 1).toUpperCase();

        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {

            sortString = sortString.toUpperCase();
        } else {
            sortString = "#";
        }
        return sortString;
    }
}