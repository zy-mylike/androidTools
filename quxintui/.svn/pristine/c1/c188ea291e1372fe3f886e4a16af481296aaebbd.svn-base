package com.enetic.push.fragment.business;

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
import com.enetic.push.activity.SeacherActivity;
import com.enetic.push.adapter.ProductAdapter;
import com.enetic.push.bean.ProductBean;
import com.enetic.push.dialog.LoadingDialog;
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
import java.util.List;

/**
 * Created by json on 2016/4/1.
 */
@Layout(R.layout.fragment_home)
public class HomeFragment extends TrackFragment {

    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;

    ProductAdapter adapter;
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
            protected void requestData(final int i, final boolean b) {//i:页 b:isloading
                //http://hivedrp.com/api/v1/business/listProduct?userId=150&page=1
                String utl = Constants.URL_SHOP_LIST + "?userId=" + Constants.CURRENT_USER.getUserId() + "&page=" + i;
                HttpUtil.httpGet(getActivity(), utl, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);

                        try {
                            List<ProductBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ProductBean>>() {}.getType());
                            if (list == null) {
                                list = new ArrayList();
                            }
                            if (list.size() == 0) {
                                list.add(null);
                            }
                            adapter = (ProductAdapter) handleData(i, list, ProductAdapter.class, b);
                            adapter.setHandler(handler);
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

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    final ProductBean bean_handler=(ProductBean)msg.obj;
                    //Toast.makeText(getActivity(),bean.getId(),Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("确定删除?").setTitle("删除").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().dismiss();
                            HttpUtil.httpGet(getActivity(), Constants.URL_PRODUCT_DELETE + bean_handler.getId(), new HttpCallback() {
                                @Override
                                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthSuccess(result, obj);
                                    adapter.removeItem(bean_handler);
                                    try {
                                        //删除成功
                                        Toast.makeText(getActivity(), obj.getString("message") + "", Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                    super.doAuthFailure(result, obj);
                                    try {
                                        String code=obj.getString("code");
                                        String msg="删除失败";
                                        if(code.equals("2000")){
                                            msg="已分享至代理的商品不能删除";
                                        }
                                        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void doRequestFailure(Exception exception, String msg) {
                                    super.doRequestFailure(exception, msg);
                                    Toast.makeText(getActivity(),"删 除 失 败",Toast.LENGTH_SHORT).show();
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
                default:
                    break;
            }
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        pullToLoadView.initLoad();
    }

    @TrackClick(value = R.id.layout_seacher, location = "首页", eventName = "搜索")
    public void clickSeacher(View view) {
        startActivity(new Intent(getActivity(), SeacherActivity.class).putExtra("type", 4));
    }
}