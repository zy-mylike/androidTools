package com.enetic.push.activity.agent;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.utils.AppTools;
import com.enetic.push.view.CashierInputFilter;
import com.enetic.push.view.InputNumberView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.CommonUtil;
import org.eteclab.track.annotation.TrackClick;

@Layout(R.layout.activity_cash_money)
public class CashMoneyActivity extends ParentActivity {
    private final static String TITLE = "提现";
    @ViewIn(R.id.sum_money)
    private TextView mAllMoneyView;
    @ViewIn(R.id.sum_all)
    private TextView mAllMoney;
    @ViewIn(R.id.money)
    private EditText mEditMoney;
    InputNumberView numberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        mAllMoney.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mEditMoney.setFilters(new InputFilter[]{new CashierInputFilter()});
        mEditMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.hideKeyboard(v.getContext(), (EditText) v);
                numberView = new InputNumberView().show(CashMoneyActivity.this, mContentView, mEditMoney);
            }
        });
    }

    @TrackClick(value = R.id.submint, location = TITLE, eventName = "下一步")
    public void clickNext(View view) {
        Toast.makeText(getApplicationContext(), "", 0).show();
    }

    @TrackClick(value = R.id.sum_all, location = TITLE, eventName = "提取全部")
    public void clickAll(View view) {
        mEditMoney.setText(mAllMoneyView.getText().toString());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (numberView != null) {
            numberView.dismiss();
        }
        return super.onTouchEvent(event);
    }
}
