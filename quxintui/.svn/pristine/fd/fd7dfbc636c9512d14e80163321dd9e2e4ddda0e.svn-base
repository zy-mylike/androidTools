package com.enetic.push.activity.agent;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.utils.IDCard;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.annotation.TrackClick;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

@Layout(R.layout.activity_verify_card)
public class VerifyCardActivity extends ParentActivity {
    private final static String TITLE = "身份验证";

    @ViewIn(R.id.card_name)
    private EditText mNameView;
    @ViewIn(R.id.card_number)
    private EditText mNUmberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
    }

    @TrackClick(value = R.id.submint, location = TITLE, eventName = "完成")
    public void clickPhone(View view) {

        String name = mNameView.getText().toString();
        String number = mNUmberView.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
            Toast.makeText(getApplicationContext(), "姓名和身份证号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        IDCard cc = new IDCard();
        try {
            if (!TextUtils.isEmpty(cc.IDCardValidate(number))) {
                Toast.makeText(getApplicationContext(), "身份证号码格式错误", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject object = new JSONObject();
        try {
            object.put("userId", Constants.CURRENT_USER.getUserId());
            object.put("idCard", number);
            object.put("realName", name);

            Log.e("",""+object.toString());
            httpPost(Constants.HOST + "user/editAuthentication", object, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    Toast.makeText(getApplicationContext(), "绑定成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    Toast.makeText(getApplicationContext(), "绑定失败", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
