package com.enetic.push.activity.agent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.utils.AppTools;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.track.annotation.TrackClick;

@Layout(R.layout.activity_binding_phone)
public class BindPhoneActivity extends ParentActivity {
    private final static String TITLE = "绑定手机";

    @ViewIn(R.id.card_name)
    private EditText mNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
    }

    @TrackClick(value = R.id.submint, location = TITLE, eventName = "下一步")
    public void clickPhone(View view) {
        String phone = mNameView.getText().toString();
        if (!AppTools.isMobileValid(phone)) {
            Toast.makeText(this, "手机号码格式错误", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(this, PhoneCodeActivity.class).putExtra("phone", phone));
        finish();

    }
}
