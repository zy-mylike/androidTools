package com.hivedrp.esale.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.enetic.push.Constants;
import com.enetic.push.share.Wx;
import com.enetic.push.share.ShareResultCall;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());

        api = WXAPIFactory.createWXAPI(this, Constants.WXConstant.WXAPP_ID, false);
        api.registerApp(Constants.WXConstant.WXAPP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    private void handleIntent(Intent intent) {
        SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());

        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            //用户同意
//            Toast.makeText(getApplicationContext(), "wx   ok", 0).show();
            Wx.init(getApplicationContext()).sendHttp(resp);
        } else {
//            Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (Constants.CURRENT_USER == null || ShareResultCall.call == null) {
            finish();
            return;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                ShareResultCall.call.onShareSucess();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                ShareResultCall.call.onShareCancel();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享失败";
                ShareResultCall.call.onShareFailure(resp.errStr, resp.errCode);
                break;
            default:
                result = "分享错误" + resp.errStr;
                ShareResultCall.call.onShareError(resp.errStr, resp.errCode);
                break;
        }
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        finish();
    }
}