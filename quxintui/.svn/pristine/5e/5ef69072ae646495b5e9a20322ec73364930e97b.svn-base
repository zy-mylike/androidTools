package com.enetic.push.activity;

import android.os.Bundle;

import com.enetic.esale.R;
import com.enetic.push.adapter.ProxyAdapter;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_proxylists)
public class ProxylistsActivity extends ParentActivity {


    private final static String TITLE = "推广记录";
    @ViewIn(R.id.proxy_listed)
    private PullToLoadView mProxyListed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolTitle(TITLE);
        final List list2 = new ArrayList();
        list2.add(new Object());
        list2.add(new Object());
        list2.add(new Object());
        list2.add(new Object());
        list2.add(new Object());
        list2.add(new Object());
        list2.add(new Object());
        list2.add(new Object());
        list2.add(new Object());
        list2.add(new Object());
        list2.add(new Object());
        list2.add(new Object());


        mProxyListed.setPullCallback(new PullCallbackImpl(mProxyListed) {
            @Override
            protected void requestData(int pager, boolean follow) {
                handleData(pager, list2, ProxyAdapter.class, follow);
            }
        });
        mProxyListed.initLoad();
    }
}