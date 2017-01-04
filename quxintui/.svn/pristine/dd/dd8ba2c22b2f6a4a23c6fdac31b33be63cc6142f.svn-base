package com.enetic.push.fragment.agent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.SeacherActivity;
import com.enetic.push.activity.agent.NewBusinessActivity;
import com.enetic.push.adapter.AgentBusinessAdapter;
import com.enetic.push.adapter.SortAgentAdapter;
import com.enetic.push.bean.AgentBean;
import com.enetic.push.bean.BusinessBean;
import com.enetic.push.utils.CharacterParser;
import com.enetic.push.view.SideBarView;
import com.enetic.push.view.TextShapeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.track.fragment.TrackFragment;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by json on 2016/4/1.
 */
@Layout(R.layout.fragment_index)
public class BusinessFragment extends TrackFragment {
    @ViewIn(R.id.country_lvcountry)
    private PullToLoadView mlistView;
    SortAgentAdapter adapter;
    AgentBusinessAdapter agentBusinessAdapter;
    @ViewIn(R.id.sidrbar)
    SideBarView sideBar;
    @ViewIn(R.id.dialog)
    TextView dialog;

    @ViewIn(R.id.nums)
    TextShapeView textShapeView;

    @Override
    protected void setDatas(Bundle bundle) {
        sideBar.setTextView(dialog);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBarView.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                if (agentBusinessAdapter == null) {
                    return;
                }
                int position = agentBusinessAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mlistView.getRecyclerView().scrollToPosition(position);
                }
            }
        });


        mlistView.setPullCallback(new PullCallbackImpl(mlistView) {
            @Override
            protected void requestData(final int pager, final boolean follow) {
                HttpUtil.httpGet(getActivity(), Constants.URL_AGENT_BUSINESS_LIST + Constants.CURRENT_USER.getUserId() + "&page=" + pager, new HttpCallback() {

                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            List<BusinessBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<BusinessBean>>() {
                            }.getType());
                            Collections.sort(list, new PinyinComparator()); //先对数据进行排序。
                            if (list == null)
                                list = new ArrayList();
                            if (list.size() == 0)
                                list.add(null);
                            agentBusinessAdapter = (AgentBusinessAdapter) handleData(pager, list, AgentBusinessAdapter.class, follow);
                            agentBusinessAdapter.setType(1);
                            agentBusinessAdapter.setHandler(mHandler);
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

    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            final BusinessBean bean = (BusinessBean) msg.obj;
            switch (msg.what) {
                case 0: //长按删除
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("确定删除?").setTitle("删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().dismiss();

                            HttpUtil.httpGet(getActivity(), Constants.URL_AGENT_BUSINESS_DELETE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getUserId(), new HttpCallback() {
                                @Override
                                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthSuccess(result, obj);
                                    agentBusinessAdapter.removeItem(bean);
                                    try {
                                        //保存成功。:obj.getString("message") + ""
                                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
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
                case 1:
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        mlistView.initLoad();
        getCount();
    }

    //新的商家：返回个数。
    public void getCount() {
        textShapeView.setVisibility(View.INVISIBLE);
        HttpUtil.httpGet(getActivity(), Constants.URL_AGENT_BUSINESS_COUNT + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    String mss = obj.getString("data");
                    if (TextUtils.isEmpty(mss) || "0".equals(mss)) {

                    } else {
                        if (mss.length() >= 2) {
                            textShapeView.setText("9+");
                        } else
                            textShapeView.setText(mss);
                        textShapeView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                textShapeView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @TrackClick(value = R.id.new_layout, location = "商家", eventName = "新商家")
    private void clickNew(View view) {
        startActivity(new Intent(getActivity(), NewBusinessActivity.class));
    }

    @TrackClick(value = R.id.search_layout, location = "商家", eventName = "搜索")
    private void clickSeacher(View view) {
        startActivity(new Intent(getActivity(), SeacherActivity.class).putExtra("type", 3));
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

    //
    public class PinyinComparator implements Comparator<BusinessBean> {
        public int compare(BusinessBean o1, BusinessBean o2) {

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
}