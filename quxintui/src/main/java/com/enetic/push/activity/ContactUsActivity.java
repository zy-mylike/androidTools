package com.enetic.push.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.enetic.esale.R;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;


@Layout(R.layout.activity_contact_us)
public class ContactUsActivity extends ParentActivity {

    private final static String Title = "关于我们";
    @ViewIn(R.id.version)
    private TextView mVersion;

    @ViewIn(R.id.copyright)
    private TextView copyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(Title);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            mVersion.setText("V"+packageInfo.versionName);

            copyright.setText("Copyright ©2016 Quanxintui All Rights Reserved");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}