package com.enetic.push.activity;

import android.os.Bundle;


import com.enetic.esale.R;

import org.eteclab.base.annotation.Layout;


/**
 * 套餐详情
 */
@Layout(R.layout.activity_page_detail)
public class PageDetailActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle("套餐一详情");
    }
}