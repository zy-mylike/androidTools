package com.enetic.push.activity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.util.LogUtils;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.track.utils.EneticConstance;
import org.json.JSONException;
import org.json.JSONObject;

@Layout(R.layout.activity_feek_back)
public class FeekBackActivity extends ParentActivity {
    private final static String TITLE = "意见反馈";
    @ViewIn(R.id.content)
    private EditText mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        setToolRight("提交");
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }

    @TrackClick(value = R.id.comment, location = TITLE, eventName = "提交")
    public void btnSubmit(View view) {

        TelephonyManager tm = (TelephonyManager) this.getSystemService("phone");
        String did = tm.getDeviceId();
        String mac = EneticConstance.getMacAddress();
        String udid = EneticConstance.UDID(did, mac);
        String uuid = EneticConstance.UUID(/*((PushApplication)getApplication()).appKey()*/"", udid);

        if (mContent.getText().toString().equals("")) {
            Toast.makeText(this, "提交信息为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = Constants.URL_FEEDBACK;
        JSONObject obj = new JSONObject();
        try {
//            byte[] b = Base64.encode(("android." + getApplicationContext().getPackageName()).getBytes(), Base64.DEFAULT);
//            byte[] b = Base64.decode("android."+getApplicationContext().getPackageName(), Base64.DEFAULT);

            obj.put("appkey", "android." + getApplicationContext().getPackageName());
//            obj.put("appkey1", new String(b));
//            obj.put("appkey", byte2HexStr(b));
            obj.put("udid", udid);
            obj.put("uuid", uuid);
            if (Constants.CURRENT_USER != null)
                obj.put("userId", Constants.CURRENT_USER.getUserId());

            obj.put("content", mContent.getText().toString());

            LogUtils.e(obj.toString());
            httpPost(url, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        Toast.makeText(FeekBackActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        mContent.setText("");
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
