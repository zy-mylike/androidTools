package com.enetic.push.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.businesses.SelectAgentActivity;
import com.enetic.push.adapter.AgentBusinessAdapter;
import com.enetic.push.adapter.BoxAdapter;
import com.enetic.push.adapter.Box_searchAdapter;
import com.enetic.push.adapter.ProductAdapter;
import com.enetic.push.adapter.SeacherAdapter;
import com.enetic.push.adapter.SelectAdapter;
import com.enetic.push.adapter.SortAgentAdapter;
import com.enetic.push.adapter.TaskAdpter;
import com.enetic.push.bean.AgentBean;
import com.enetic.push.bean.AgentBean;
import com.enetic.push.bean.AgentTaskBean;
import com.enetic.push.bean.BusinessBean;
import com.enetic.push.bean.HotWordBean;
import com.enetic.push.bean.ProductBean;
import com.enetic.push.bean.SeacherBean;
import com.enetic.push.bean.TaskBean;
import com.enetic.push.db.WoDbUtils;
import com.enetic.push.dialog.AddProxyDialog;
import com.enetic.push.dialog.ShareDialog;
import com.enetic.push.share.ShareManage;
import com.enetic.push.share.ShareResultCall;
import com.enetic.push.share.ShareWX;
import com.enetic.push.view.FlowLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.CommonUtil;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.NoScrollListView;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_seacher)
public class SeacherActivity extends ParentActivity {

    private final static String TITLE = "搜索界面";
    @ViewIn(R.id.text)
    private EditText mDataText;

    @ViewIn(R.id.clear_layout) //清空搜索记录
    private View mClearView;

    @ViewIn(R.id.clear)
    private View mClear;

    @ViewIn(R.id.pull_list)
    PullToLoadView pullToLoadView;  //数据列表

    @ViewIn(R.id.layout_views)
    private View mLayout;  //搜索记录的那部分页面。

    @ViewIn(R.id.flow)
    private FlowLayout layouts;

    @ViewIn(R.id.hostory_list)
    private NoScrollListView hostoryList;

    private SeacherAdapter adapter;

    ArrayList mData = new ArrayList<>();

    private List<AgentBean> lists = new ArrayList<>(); //选中的数据。

    private int type;
    String key = "";

    BusinessBean businessBean; //商家信息。


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hostoryList.setItemDecoration(new NoScrollListView.ItemDecoration() {
            @Override
            public View getView() {
                View rect = new View(getApplicationContext());
                rect.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
                //rect.setPadding(10, 10, 10, 10);
                rect.setBackgroundColor(Color.parseColor("#00000000"));
                return rect;
            }

            @Override
            public Rect getRect() {
                return null;
            }
        });

        mData.clear();
        /**
         * 1 TaskAdpter agent ProductListActivity 商品
         * 2 SortAgentAdapter businesses AgentAllActivity 代理
         * 3 AgentBusinessAdapter agent BusinessFragment 商家
         * 4 ProductAdapter businesses HomeFragment 商品
         * 5 BoxAdapter businesses OutboxFragment 发件箱
         * 6 BoxAdapter businesses AgentFragment 代理
         */


        //type=? ［4:首页 ，5:发件箱，6:代理］
        type = getIntent().getIntExtra("type", -1);

        if (type == 1) {
            businessBean = (BusinessBean) getIntent().getSerializableExtra("bussinessBean");
        } else {
            businessBean = null;
        }

        if (type == 7) {
            mData = (ArrayList<AgentBean>) getIntent().getSerializableExtra("data");
        }
        uphostortList();
        mDataText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //开始搜索
                    startSeacher(mDataText.getText().toString());
                    return true;
                }
                return false;
            }
        });

        mDataText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               // pullToLoadView.setVisibility(View.GONE);
                mLayout.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                mBut.setText(s.length() > 0 ? "搜索" : "取消");
            }
        });
        pullToLoadView.setLoadingColor(R.color.colorPrimary);
        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView) {
            @Override
            protected void requestData(final int page, final boolean b) {
                /**
                 * 1 TaskAdpter agent ProductListActivity 商品
                 * 2 SortAgentAdapter businesses AgentAllActivity 代理
                 * 3 AgentBusinessAdapter agent BusinessFragment 商家
                 * 4 ProductAdapter businesses HomeFragment 商品 首页
                 * 5 BoxAdapter businesses OutboxFragment 商品  发件箱
                 */
                int types = 0;
                if (type == 1) { //代理 商品列表
                    types = 1;
                    handleData(page, mData, TaskAdpter.class, b);
                } else if (type == 2 || type == 6) { // 代理列表
                    types = 3;

                } else if (type == 3) { //商家列表
                    types = 2;
                   // handleData(page, mData, AgentBusinessAdapter.class, b);
                } else if (type == 4) { //商品列表
                    types = 1;
                    handleData(page, mData, ProductAdapter.class, b);
                } else if (type == 5) { //商品列表 发件箱
                    types = 1;
                    // handleData(page, mData, BoxAdapter.class, b);
                } else if (type == 7) { // 商品详情分享至代理列表－>搜索。
                    types = 3;
                }
                // String userId=(type==1?businessBean.getUserId()+"":Constants.CURRENT_USER.getUserId());
                //key:搜索关键字
                httpGet(Constants.URL_SEARCH + key + "&page=" + page + "&userId=" + (type == 1 ? businessBean.getUserId() + "" : Constants.CURRENT_USER.getUserId()) + "&type=" + types, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            if (type == 1) { //代理 商品列表
                                mData = new Gson().fromJson(obj.getString("data"), new TypeToken<ArrayList<AgentTaskBean>>() {
                                }.getType());
                                if (mData == null) {
                                    mData = new ArrayList();

                                }
                                if (mData.size() == 0) {
                                    mData.add(null);
                                }
                                TaskAdpter adpter = (TaskAdpter) handleData(page, mData, TaskAdpter.class, b);
                                adpter.setHandler(mHandlers);
                                adpter.setLl_shareVisibility(View.GONE);
                                adpter.setType(3);
                            } else if (type == 2 || type == 6) { //商户端代理页搜索返回的 代理列表。
                                mData = new Gson().fromJson(obj.getString("data"), new TypeToken<ArrayList<AgentBean>>() {
                                }.getType());
                                if (mData == null) {
                                    mData = new ArrayList();

                                }
                                if (mData.size() == 0) {
                                    mData.add(null);
                                }
                                SortAgentAdapter adapter = (SortAgentAdapter) handleData(page, mData, SortAgentAdapter.class, b);
                                adapter.setHandler(mHandler);
                                adapter.setType(type == 6 ? 0 : 1);
                            } else if (type == 3) { //商家列表
                                mData = new Gson().fromJson(obj.getString("data"), new TypeToken<ArrayList<BusinessBean>>() {
                                }.getType());
                                if (mData == null) {
                                    mData = new ArrayList();

                                }
                                if (mData.size() == 0) {
                                    mData.add(null);
                                }
                                AgentBusinessAdapter agentBusinessAdapter = (AgentBusinessAdapter) handleData(page, mData, AgentBusinessAdapter.class, b);
                                agentBusinessAdapter.setType(2);
                                agentBusinessAdapter.setHandler(businessHandler);
                            } else if (type == 4) { //商户端首页搜索返回的商品列表。
                                mData = new Gson().fromJson(obj.getString("data"), new TypeToken<ArrayList<ProductBean>>() {
                                }.getType());
                                if (mData == null) {
                                    mData = new ArrayList();

                                }
                                if (mData.size() == 0) {
                                    mData.add(null);
                                }
                                handleData(page, mData, ProductAdapter.class, b);
                            } else if (type == 5) { //商户端发件箱页搜索返回的商品列表。
//                               mData = new Gson().fromJson(obj.getString("data"), new TypeToken<ArrayList<TaskBean>>() {
//                                }.getType());
//                               BoxAdapter adapter = (BoxAdapter) handleData(page, mData, BoxAdapter.class, b);
                                mData = new Gson().fromJson(obj.getString("data"), new TypeToken<ArrayList<ProductBean>>() {
                                }.getType());
                                if (mData == null) {
                                    mData = new ArrayList();

                                }
                                if (mData.size() == 0) {
                                    mData.add(null);
                                }
                                Box_searchAdapter adapter = (Box_searchAdapter) handleData(page, mData, Box_searchAdapter.class, b);
                                adapter.setHandler(mHandlerBox);
                            } else if (type == 7) {
                                List native_Data = new ArrayList<AgentBean>(); //此处 native_Data不宜设为全局变量，会发生引用问题
                                for (int i = 0; i < mData.size(); i++) {
                                    String name = ((AgentBean) mData.get(i)).getNickName();
                                    if (name.contains(key)) {
                                        native_Data.add(mData.get(i));
                                    }
                                }
                                SelectAdapter adapter = (SelectAdapter) handleData(page, native_Data, SelectAdapter.class, b);
                                adapter.setType(1);
                                //adapter.setHandler(sendToAgent);

                            }
                            if (adapter.getItemCount() <= 0) {
                                mLayout.setVisibility(View.GONE);
                            } else {
                                mLayout.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void doRequestFailure(Exception exception, String msg) {
                        super.doRequestFailure(exception, msg);
                        Log.i("lyw", "搜索页，网络请求失败");
                        loadOK();
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        Log.i("lyw", "搜索页，网络请求失败:doAuthFailure");
                        loadOK();
                    }
                });
            }
        });
    }


    @TrackClick(R.id.clear)
    private void clear(View v) {
        mDataText.setText("");
    }


    @TrackClick(R.id.clear_jilu) //清空搜索记录
    private void clearData(View v) {
        try {
            WoDbUtils.initialize(getApplicationContext()).delete(SeacherBean.class, WhereBuilder.b().and("type", "=", type));
            uphostortList();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void uphostortList() {

        List<SeacherBean> list = WoDbUtils.find(this, type);

        adapter = new SeacherAdapter(this, list);
        adapter.setOnItemClickListener(new HolderAdapter.OnItemClickListener<SeacherBean>() {
            @Override
            public void onItemClick(View view, SeacherBean seacherBean, int i) {
                startSeacher(seacherBean.getContent());
            }
        });
        mClearView.setVisibility(list != null && list.size() > 0 ? View.VISIBLE : View.GONE); //清空搜索记录
        hostoryList.setAdapter(adapter);


    }

    @ViewIn(R.id.start_sou)
    private TextView mBut;

    @TrackClick(R.id.start_sou) //搜索｜取消
    private void submint(View v) {
        if (!TextUtils.isEmpty(mDataText.getText().toString())) {
            startSeacher(mDataText.getText().toString());
        } else {
            //将lists 中的数据返回上一页面。
            Intent intent=new Intent();
            intent.putExtra("dataResult",(Serializable)lists);
            this.setResult(1,intent);
            finish();
        }
    }

    private void saveSeacherHos(String data) {
        try {
            SeacherBean seacherBean = new SeacherBean();
            seacherBean.setContent(data);
            seacherBean.setType(this.type);
            seacherBean.setCreateTime(System.currentTimeMillis());
            WoDbUtils.initialize(getApplicationContext()).delete(SeacherBean.class, WhereBuilder.b().and("content", "=", seacherBean.getContent()).and("type", "=", type));
            WoDbUtils.initialize(getApplicationContext()).save(seacherBean);
            uphostortList();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void startSeacher(final String key) {
        if (TextUtils.isEmpty(key.trim())) {
            Toast.makeText(getApplicationContext(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        saveSeacherHos(key);
        mDataText.setText(key);
        CommonUtil.hideKeyboard(getApplicationContext(), mDataText);
        mLayout.setVisibility(View.GONE);
        pullToLoadView.setVisibility(View.VISIBLE);
        this.key = key;

        pullToLoadView.initLoad();
    }

    public void addSelect(AgentBean bean) {

        lists.add(bean);
    }

    public void removeSelect(AgentBean bean) {
        lists.remove(bean);
    }

    android.os.Handler mHandlers = new android.os.Handler() { //
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final AgentTaskBean bean = (AgentTaskBean) msg.obj;
            switch (msg.what) {
                case 0:
                    AlertDialog.Builder builder = new AlertDialog.Builder(SeacherActivity.this);
                    builder.setMessage("确定要删除吗?").setTitle("提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            httpGet(Constants.URL_AGENT_TASK_DELETE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getBusinessId() + "&productId=" + bean.getId(), new HttpCallback() {
                                @Override
                                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthSuccess(result, obj);
                                    try {
                                        Toast.makeText(SeacherActivity.this, obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthFailure(result, obj);
                                    try {
                                        Toast.makeText(SeacherActivity.this, obj.getString("message") + "", Toast.LENGTH_SHORT).show();
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
                case 1:
                    final ShareDialog dialog = new ShareDialog(SeacherActivity.this);
                    dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ShareWX wx = new ShareWX(SeacherActivity.this);
                            wx.addCall(new ShareResultCall() {
                                @Override
                                public void onShareSucess() {
                                    super.onShareSucess();
                                    String utl = Constants.URL_AGENT_ADDSHARE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getBusinessId() + "&productId=" + bean.getId();
                                    httpGet(utl, new HttpCallback());
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
            }
        }
    };
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            final AgentBean bean = (AgentBean) msg.obj;
            switch (msg.what) {
                case 0: //添加
                    httpGet(Constants.URL_ADD_AGENT + bean.getUserId() + "&businessId=" + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);
                            pullToLoadView.initLoad();
                            try {
                                //保存成功。
                                Toast.makeText(getApplicationContext(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthFailure(result, obj);
                            try {
                                //不能重复添加
                                Toast.makeText(getApplicationContext(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case 1: //删除

                    final AlertDialog.Builder builder = new AlertDialog.Builder(SeacherActivity.this);
                    builder.setMessage("确定删除?").setTitle("删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().dismiss();
                            HttpUtil.httpGet(SeacherActivity.this, Constants.URL_DELETE_AGENT + bean.getUserId() + "&businessId=" + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
                                @Override
                                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthSuccess(result, obj);
                                    pullToLoadView.initLoad();
                                    try {
                                        Toast.makeText(SeacherActivity.this, obj.getString("message") + "lyw", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthFailure(result, obj);
                                    try {
                                        Toast.makeText(SeacherActivity.this, obj.getString("message") + "lwylyw", Toast.LENGTH_SHORT).show();
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
                case 2: //提示不能删除
                    Toast.makeText(getApplicationContext(), "您不是该代理的好友，不能执行删除操作", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    Handler mHandlerBox = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final TaskBean bean = (TaskBean) msg.obj;
            switch (msg.what) {
                case 2:
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SeacherActivity.this);
                    builder.setMessage("确定删除?").setTitle("删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().dismiss();
                            HttpUtil.httpGet(SeacherActivity.this, Constants.URL_TASK_DELETE + Constants.CURRENT_USER.getUserId() + "&productId=" + bean.getProductId(), new HttpCallback() {
                                @Override
                                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthSuccess(result, obj);
                                    pullToLoadView.initLoad();
                                    try {
                                        Toast.makeText(SeacherActivity.this, obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthFailure(result, obj);
                                    try {
                                        Toast.makeText(SeacherActivity.this, obj.getString("message") + "", Toast.LENGTH_SHORT).show();
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
                    final AddProxyDialog dialog = new AddProxyDialog(SeacherActivity.this);
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
                                    ShareManage.shareWeb(SeacherActivity.this, utl, bean.getName(), utl, "", scene, url);
                                    break;
                                case R.id.take_images:
                                    startActivity(new Intent(SeacherActivity.this, SelectAgentActivity.class).putExtra("id", bean.getId() + ""));
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

    Handler businessHandler = new Handler() { //代理端，商家搜索
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final BusinessBean bean = (BusinessBean) msg.obj;
            switch (msg.what) {
                case 0: //长按删除
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SeacherActivity.this);
                    builder.setMessage("确定删除?").setTitle("删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().dismiss();

                            HttpUtil.httpGet(SeacherActivity.this, Constants.URL_AGENT_BUSINESS_DELETE + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getUserId(), new HttpCallback() {
                                @Override
                                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthSuccess(result, obj);
                                    pullToLoadView.initLoad();
//                                    try {
//                                        //保存成功。
//                                        Toast.makeText(getActivity(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
                                }

                                @Override
                                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthFailure(result, obj);
                                    try {
                                        Toast.makeText(SeacherActivity.this, obj.getString("message") + "", Toast.LENGTH_SHORT).show();
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
                    HttpUtil.httpGet(getApplicationContext(), Constants.URL_AGENT_BUSINESS_ADD + Constants.CURRENT_USER.getUserId() + "&businessId=" + bean.getUserId(), new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);
                            pullToLoadView.initLoad();
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
                case 2:
                    Toast.makeText(getApplicationContext(), "您不是该商家的好友，不能执行删除操作", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };

    Handler sendToAgent=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(),"lalalal",Toast.LENGTH_SHORT).show();
        pullToLoadView.initLoad();
    }
}
