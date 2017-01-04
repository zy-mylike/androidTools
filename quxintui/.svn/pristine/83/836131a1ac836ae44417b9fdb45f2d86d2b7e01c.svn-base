package com.enetic.push.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.enetic.push.utils.AppTools;
import com.enetic.esale.R;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.track.Tracker;
import org.eteclab.track.annotation.TrackClick;


@Layout(R.layout.activity_register_password)
public class RegisterPasswordActivity extends ParentActivity {

    private static String TITLE = "注册";

    int type = -1;
    @ViewIn(R.id.email)
    private EditText mPhoneText;
    @ViewIn(R.id.password)
    private EditText mPasswordText;
    @ViewIn(R.id.code)
    private EditText mAuthCodeText;
    @ViewIn(R.id.get_authCode)
    private Button mAuthCodeBtn;
    @ViewIn(R.id.email_sign_in_button)
    private Button mSignBtn;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 0);
        mSignBtn.setText(type == 1 ? R.string.action_register_in : R.string.action_password_in);
        TITLE = (type == 1 ? getString(R.string.action_register_in) : getString(R.string.action_password_in));
    }

    @TrackClick(R.id.email_sign_in_button)
    private void clickSubmit(View v) {
        Tracker.getInstance(this).trackMethodInvoke(TITLE, type == 1 ? "点击找回密码" : "点击注册");
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
        mPhoneText.setError(null);
        mAuthCodeText.setError(null);
        mPasswordText.setError(null);
        mPhone = mPhoneText.getText().toString();
        if (!AppTools.isMobileValid(mPhone)) {
            mPhoneText.setError(getString(R.string.error_invalid_email));
            return;
        }
        String code = mAuthCodeText.getText().toString();
        if (!AppTools.isMobileValid(code)) {
            mAuthCodeText.setError(getString(R.string.prompt_errer_code));
            return;
        }
        String password = mPasswordText.getText().toString();
        if (!AppTools.isMobileValid(password)) {
            mPasswordText.setError(getString(R.string.prompt_errer_password));
            return;
        }

    }

    @TrackClick(R.id.email_sign_in_button)
    private void clickAuthCode(View v) {
        Tracker.getInstance(this).trackMethodInvoke(TITLE, "获取验证码");
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
        mPhoneText.setError(null);
        mPhone = mPhoneText.getText().toString();
        if (!AppTools.isMobileValid(mPhone)) {
            mPhoneText.setError(getString(R.string.error_invalid_email));
            return;
        }
    }
}
