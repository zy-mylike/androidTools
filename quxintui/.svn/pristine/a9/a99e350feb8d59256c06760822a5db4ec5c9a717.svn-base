package com.enetic.push.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enetic.esale.R;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.track.annotation.TrackClick;

/**
 * 代理资料
 */
@Layout(R.layout.activity_proxyinfo)
public class ProxyinfoActivity extends ParentActivity {

    @ViewIn(R.id.user_img)
    private ImageView mHeaderImg;

    @ViewIn(R.id.proxy_num)
    private TextView mProxyNumTv;//代理
    @ViewIn(R.id.sucess_sum)
    private TextView mSucessNumTv;//成功
    @ViewIn(R.id.extension_sum)
    private TextView mExtenNumTv;//推广

    @ViewIn(R.id.user_name)
    private TextView mUserTv;
    @ViewIn(R.id.name)
    private TextView mUsersTv;
    @ViewIn(R.id.sex)
    private TextView mSexTv;
    @ViewIn(R.id.age)
    private TextView mAgeTv;
    @ViewIn(R.id.address)
    private TextView mAddressTv;
    @ViewIn(R.id.school)
    private TextView mSchoolTv;
    @ViewIn(R.id.diploma)
    private TextView mDiplomaTv;//学历
    @ViewIn(R.id.major)
    private TextView mMajorTv;//专业
    @ViewIn(R.id.wx_count)
    private TextView mWxNumTv;
    @ViewIn(R.id.describe)
    private TextView mDescribeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @TrackClick(R.id.submit_btn)
    private void clickSubmit(View v) {
        //同意
    }

    @TrackClick(R.id.cancel)
    private void clickCancel(View v) {
        finish();
    }
}
