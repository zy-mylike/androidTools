package com.enetic.push.activity.agent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

@Layout(R.layout.activity_user_card)
public class UserCardActivity extends ParentActivity {

    private final static String TITLE = "身份信息";
    @ViewIn(R.id.img_status)
    private ImageView mHaveImg;
    @ViewIn(R.id.text_status)
    private TextView mHaveTv;
    @ViewIn(R.id.card_name)
    private TextView mCardNameTv;
    @ViewIn(R.id.card_number)
    private TextView mCardNumTv;
    @ViewIn(R.id.phone_number)
    private TextView mNumberTv;

    @ViewIn(R.id.card_btn)
    private Button mCarBtn;

    @ViewIn(R.id.phone_btn)
    private Button mPhoneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);

//        mHaveImg.setImageResource(R.mipmap.have_infos);
//        mHaveTv.setText(R.string.card_status_have);
//        mCarBtn.setText(R.string.check_card);
//        mPhoneBtn.setText(R.string.check_phone);
    }


    @TrackClick(value = R.id.card_btn, location = TITLE, eventName = "验证身份")
    public void clickCarNumber(View view) {
        startActivity(new Intent(this, VerifyCardActivity.class));
    }

    @TrackClick(value = R.id.phone_btn, location = TITLE, eventName = "绑定手机号")
    public void clickPhone(View view) {
        startActivity(new Intent(this, BindPhoneActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCardInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void getCardInfo() {
//        http://123.56.105.87:10040/api/v1/user/getAuthentication

        httpGet(Constants.HOST + "user/getAuthentication?userId=" + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    String json = obj.getString("data");
                    if (TextUtils.isEmpty(json) || "null".equals(json)) {


                    } else {
                        obj = new JSONObject(json);
                        String mobile = obj.getString("mobile");
                        String idCard = obj.getString("idCard");
                        mCardNameTv.setText(obj.getString("realName"));
                        mCardNumTv.setText(idCard);
                        mNumberTv.setText(mobile);
                        if (!TextUtils.isEmpty(idCard)) {
                            mHaveImg.setImageResource(R.mipmap.have_infos);
                            mHaveTv.setText(R.string.card_status_have);
                            mCarBtn.setText(R.string.check_card);
                        }
                        if (!TextUtils.isEmpty(mobile))
                            mPhoneBtn.setText(R.string.check_phone);
                    }
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

}