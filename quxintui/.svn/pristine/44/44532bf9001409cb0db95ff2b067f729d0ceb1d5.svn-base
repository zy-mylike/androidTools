package com.enetic.push.activity.agent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.annotation.TrackClick;
import org.json.JSONException;
import org.json.JSONObject;

@Layout(R.layout.activity_account)
public class OurAccountActivity extends ParentActivity {
    private final static String TITLE = "我的账户";

    @ViewIn(R.id.money_sum)
    private TextView mMoneyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        httpGet(Constants.URL_AGENT_ACCOUNT + Constants.CURRENT_USER.getUserId(),new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    obj = new JSONObject(obj.getString("data"));
                    mMoneyView.setText(obj.getString("lucreCount") +"元");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
    }

    @TrackClick(value = R.id.layout_count, location = TITLE, eventName = "收入计算")
    public void clickCount(View view) {
        startActivity(new Intent(this, IncomeActivity.class));
    }

    @TrackClick(value = R.id.tixian, location = TITLE, eventName = "提现")
    public void clickGetMoney(View view) {
        startActivity(new Intent(this, CashMoneyActivity.class));
    }

    @TrackClick(value = R.id.recorder_tixian, location = TITLE, eventName = "提现记录")
    public void clickrRecorder(View view) {
        startActivity(new Intent(this, CashRecordActivity.class).putExtra("type",1));
    }
}
