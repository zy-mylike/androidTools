package com.enetic.push.activity.businesses;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.activity.SeacherActivity;
import com.enetic.push.adapter.SortAgentAdapter;
import com.enetic.push.bean.AgentBean;
import com.enetic.push.utils.CharacterParser;
import com.enetic.push.view.SideBarView;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 已注册代理
 */
@Layout(R.layout.activity_agent_all)
public class AgentAllActivity extends ParentActivity {

    private final static String TITLE = "添加已注册代理";

    @ViewIn(R.id.country_lvcountry)
    private PullToLoadView mlistView;
    SortAgentAdapter adapter;
    @ViewIn(R.id.sidrbar)
    SideBarView sideBar;
    @ViewIn(R.id.dialog)
    TextView dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
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
                String utl = Constants.URL_ALLAGENT_LIST + "?page=" + pager + "&userId=" + Constants.CURRENT_USER.getUserId();
                httpGet(utl, new HttpCallback() {
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
                            adapter.setType(1);

                        } catch (Exception w) {
                            {
                                w.printStackTrace();
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
        mlistView.initLoad();
    }


    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                final AgentBean bean = (AgentBean) msg.obj;
                httpGet(Constants.URL_ADD_AGENT + bean.getUserId() + "&businessId=" + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        mlistView.initLoad();
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
        }
    };

    @TrackClick(value = R.id.layout_seacher, location = TITLE, eventName = "搜索")
    private void clickSeacher(View view) {
        startActivity(new Intent(this, SeacherActivity.class).putExtra("type",2));
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

    @Override
    protected void onResume() {
        super.onResume();
        mlistView.initLoad();
    }
}