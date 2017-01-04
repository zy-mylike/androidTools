package com.enetic.push.activity.businesses;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.activity.SeacherActivity;
import com.enetic.push.adapter.SelectAdapter;
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
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Layout(R.layout.activity_select_agent)
public class SelectAgentActivity extends ParentActivity {

    @ViewIn(R.id.country_lvcountry)
    private PullToLoadView mlistView;
    SelectAdapter adapter;
    @ViewIn(R.id.sidrbar)
    SideBarView sideBar;
    @ViewIn(R.id.dialog)
    TextView tv_dialog;

    List<AgentBean> list = null; //接口请求来的全部数据

    private List<AgentBean> lists = new ArrayList<>(); //选中的数据。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle("选择代理");
        setToolRight("确定");
        lists.clear();
        sideBar.setTextView(tv_dialog);
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
            protected void requestData(final int pager, final boolean follow) { //pager:页 follow:isloading.
                String utl = Constants.URL_AGENT_LIST + Constants.CURRENT_USER.getUserId() + "&page=" + pager;
                httpGet(utl, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);

                        try {
                            list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<AgentBean>>() {
                            }.getType());
                            ArrayList new_list=new ArrayList();
                            new_list.addAll(list);
                            Collections.sort(new_list, new PinyinComparator()); //list中的数据先进行排一下序，再传入adapter。
                            adapter = (SelectAdapter) handleData(pager, new_list, SelectAdapter.class, follow);
                            //adapter.notifyDataSetChanged();
                            adapter.setType(2);
                            adapter.setOnItemClickListener(new HolderAdapter.OnItemClickListener<AgentBean>() {
                                @Override
                                public void onItemClick(View view, AgentBean agentBean, int position) {
                                    startActivity(new Intent(getApplicationContext(), AgentDetalActivity.class).putExtra("bean", agentBean));
                                }
                            });
                        } catch (Exception w) {
                            {

                            }
                        }
                    }

                    @Override
                    public void doRequestFailure(Exception exception, String msg) {
                        super.doRequestFailure(exception, msg);
                        Toast.makeText(getApplicationContext(), "代理数据请求失败", Toast.LENGTH_SHORT).show();
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


    @TrackClick(value = R.id.layout_seacher, location = "搜索", eventName = "搜索")
    private void clickSearch(View view) {
        Intent intent = new Intent(getApplicationContext(), SeacherActivity.class);
        intent.putExtra("type", 7);
        intent.putExtra("data", (Serializable) list);
        startActivityForResult(intent, 0); //请求码设为 0；
    }


    @TrackClick(value = R.id.comment, location = "选择代理", eventName = "确定")
    private void clickComment(View view) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChosed == 1) {
                lists.add(list.get(i));
            }
        }
        if(lists.size()==0){
            Toast.makeText(getApplicationContext(),"选中代理为空",Toast.LENGTH_SHORT).show();
            return;
        }
//        else {
//            Toast.makeText(getApplicationContext(),lists.size()+"",Toast.LENGTH_SHORT).show();
//        }
        String utl = Constants.URL_ADDTASK + Constants.CURRENT_USER.getUserId() + "&productId=" + getIntent().getStringExtra("id"); //商品ID
        for (AgentBean bean : lists) {
            utl += ("&agentId=" + bean.getUserId());
        }
        httpGet(utl, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                //保存成功。
                Toast.makeText(getApplicationContext(), "发送至代理成功", Toast.LENGTH_SHORT).show();
                //finish();
                //发送至代理成功，然后调用 添加到发件箱的接口。
                handler.obtainMessage(0, getIntent().getStringExtra("id")).sendToTarget();
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

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
                try {
                    //bad request.
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String id = (String) (msg.obj);
                    String url = Constants.URL_TASK_ADD + id;
                    httpGet(url, new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);
                            Toast.makeText(getApplicationContext(), "成功添加至发件箱", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthFailure(result, obj);
                            try {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            List<AgentBean> list_return = (List<AgentBean>) data.getSerializableExtra("dataResult");

            for (int i = 0; i < list_return.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if ((list_return.get(i)).getUserId().equals(list.get(j).getUserId())) {
                        list.remove(list.get(j));
                        list.add(list_return.get(i));
                        break;
                    }
                }
            }
            //list.addAll(list_return);
            Collections.sort(list, new PinyinComparator());

            adapter.notifyDataSetChanged(list);
        }
    }

    public void addSelect(AgentBean bean) {
        lists.add(bean);
    }

    public void removeSelect(AgentBean bean) {
        lists.remove(bean);
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
