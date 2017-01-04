package com.enetic.push.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.enetic.esale.R;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.track.annotation.TrackClick;
/**
 * 渠道代理 
 */
@Layout(R.layout.activity_proxy)
public class ProxyActivity extends ParentActivity {

    private final static String TITLE = "渠道代理";

    @ViewIn(R.id.money_sum)
    private TextView mSumMoneyView;
    @ViewIn(R.id.rebate)
    private TextView mRebateView;//返点比例

    @ViewIn(R.id.month_days)
    private TextView mDayView;

    @ViewIn(R.id.rebate)
    private TextView mRebate;//返点比例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);

    }


    @TrackClick(R.id.submit_btn)
    private void clickSubmit(View v) {
        //同意
//        ProductTipDialog dialog = new
//                ProductTipDialog(this);
//        dialog.show();
    }

    @TrackClick(R.id.cancel)
    private void clickCancel(View v) {
        finish();
    }

    public void addProxy() {
        startActivity(new Intent(this, ContactsActivity.class));
    }
}