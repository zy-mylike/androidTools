package com.enetic.push.activity.agent;

import android.os.Bundle;

import com.enetic.esale.R;
import com.enetic.push.activity.ParentActivity;

import org.eteclab.base.annotation.Layout;


@Layout(R.layout.activity_income)
public class IncomeActivity extends ParentActivity {
    private final static String TITLE = "收入计算";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
    }
}
