package com.enetic.push.activity.businesses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.activity.ParentActivity;
import com.enetic.push.dialog.LoadingDialog;
import com.enetic.push.utils.AppTools;
import com.enetic.push.utils.FileUtils;
import com.enetic.push.utils.MD5;
import com.enetic.push.view.CashierInputFilter;
import com.enetic.push.view.InputNumberView;
import com.lidroid.xutils.http.ResponseInfo;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.CommonUtil;
import org.eteclab.track.annotation.TrackClick;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

@Layout(R.layout.activity_recharge)
public class RechargeActivity extends ParentActivity {
    private final static String TITLE = "充值";

    @ViewIn(R.id.money)
    private EditText mEditMoney;
    InputNumberView numberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle(TITLE);
        mEditMoney.setFilters(new InputFilter[]{new CashierInputFilter()});
        mEditMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.hideKeyboard(v.getContext(), (EditText) v);
                numberView = new InputNumberView().show(RechargeActivity.this, mContentView, mEditMoney);
            }
        });
    }

    LoadingDialog dialog;

    @TrackClick(value = R.id.submint, location = TITLE, eventName = "下一步")
    public void clickNext(View view) {
        if (dialog == null)
            dialog = new LoadingDialog(this);
        final String money = mEditMoney.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            Toast.makeText(this, "格式错误", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.show(20);
        httpGet(Constants.GET_ORDERUNMBRT_URL + Constants.CURRENT_USER.getUserId(), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    WxPay(money, obj.getString("data"));
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

    public void WxPay(final String money, String orderNumber) {
        JSONObject object = new JSONObject();
        try {
            this.orderNumber = orderNumber;
//            orderNumber = MD5.stringMD5("1005" + System.currentTimeMillis());
            object.put("appid", Constants.WXConstant.WXAPP_ID);
            object.put("out_trade_no", this.orderNumber);
            object.put("total_fee", money);
            object.put("body", "\u5168\u65b0\u63a8\u8d26\u6237\u5145\u503c");

            httpPost("http://pay.enetic.cn/pay/app/qxt/unifiedorder", object, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        mMoney = money;
                        obj = new JSONObject(new JSONObject(result.result).getString("data"));
                        final IWXAPI msgApi = WXAPIFactory.createWXAPI(getApplicationContext(), Constants.WXConstant.WXAPP_ID);
                        msgApi.registerApp(Constants.WXConstant.WXAPP_ID);
                        if (!msgApi.isWXAppInstalled()) {
                            Toast.makeText(getApplicationContext(), "未安装微信客户端，请先下载", Toast.LENGTH_LONG).show();
                            return;
                        }
                        FileUtils.saveFile("datass2", obj.toString());
                        PayReq request = new PayReq();
                        request.appId = Constants.WXConstant.WXAPP_ID;
                        request.partnerId = Constants.WXConstant.WXPartnerId;
                        request.prepayId = obj.getString("prePayId");
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = obj.getString("nonceStr");
                        request.timeStamp = obj.getString("timeStamp");
                        request.sign = genPayReq(obj.getString("appId"), request.partnerId, request.prepayId, request.nonceStr, request.timeStamp);
                        msgApi.sendReq(request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    Toast.makeText(getApplicationContext(), "\u5145\u503c\u5931\u8d25", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取sign签名
     *
     * @param PARENTID
     * @param appId
     * @return
     */
    private String genPayReq(String appId, String PARENTID, String prepayId, String nonceStr, String timeStamp) {

        // 获取参数的值
        PayReq request = new PayReq();
        request.appId = appId;
        request.partnerId = Constants.WXConstant.WXPartnerId;// 微信支付分配的商户号
        request.prepayId = prepayId;
        request.packageValue = "Sign=WXPay";
        request.nonceStr = nonceStr;
        request.timeStamp = timeStamp;

        // 把参数的值传进去SortedMap集合里面
        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();

        parameters.put("appid", request.appId);
        parameters.put("noncestr", request.nonceStr);
        parameters.put("package", request.packageValue);
        parameters.put("partnerid", request.partnerId);
        parameters.put("prepayid", request.prepayId);
        parameters.put("timestamp", request.timeStamp);

        String characterEncoding = "UTF-8";
        String mySign = createSign(characterEncoding, parameters);
        FileUtils.saveFile("datass4", mySign);
        System.out.println("我的签名是：" + mySign);
        return mySign;
    }

    /**
     * 微信支付签名算法sign
     *
     * @param characterEncoding
     * @param parameters
     * @return
     */
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + Constants.WXConstant.WXAPIKey);
        String sign = AppTools.MD5(sb.toString()).toUpperCase();
        return sign;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (numberView != null) {
            numberView.dismiss();
        }
        return super.onTouchEvent(event);
    }


    String orderNumber = "";
    String mMoney = "0.00";

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter f = new IntentFilter();
        f.addAction(getPackageName() + ".pay.wx");
        registerReceiver(r, f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(r);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (dialog != null)
                dialog.dismiss();
            if (msg.what == 0) {
                JSONObject json = new JSONObject();
                try {
                    json.put("out_trade_no", orderNumber);
                    httpPost("http://pay.enetic.cn/pay/app/qxt/pay/result", json, new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);

                            try {
                                obj = new JSONObject(obj.getString("data"));
                                String message = "";


//                            if("SUCCESS")
                                if ("success".equals(obj.getString("return_code").toLowerCase()) && "success".equals(obj.getString("result_code").toLowerCase())) {
                                    if ("success".equals(obj.getString("trade_state").toLowerCase())) {
                                        message = "充值成功";
                                    } else if ("refund".equals(obj.getString("trade_state").toLowerCase())) {
                                        message = "转入退款";
                                    } else if ("notpay".equals(obj.getString("trade_state").toLowerCase())) {
                                        message = "未支付";
                                    } else if ("closed".equals(obj.getString("trade_state").toLowerCase())) {
                                        message = "已关闭";
                                    } else if ("revoked".equals(obj.getString("trade_state").toLowerCase())) {
                                        message = "已撤销（刷卡支付）";
                                    } else if ("userpaying".equals(obj.getString("trade_state").toLowerCase())) {
                                        message = "用户支付中";
                                    } else if ("payerror".equals(obj.getString("trade_state").toLowerCase())) {
                                        message = "支付失败";
                                    }


//                                SUCCESS—支付成功
//                                REFUND—转入退款
//                                NOTPAY—未支付
//                                CLOSED—已关闭
//                                REVOKED—已撤销（刷卡支付）
//                                USERPAYING--用户支付中
//                                PAYERROR--支付失败(其他原因，如银行返回失败)
                                }


                                AlertDialog.Builder dialog = new AlertDialog.Builder(RechargeActivity.this);
                                dialog.setTitle("提示");
                                dialog.setMessage(message);
                                dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                dialog.show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthFailure(result, obj);
                            Toast.makeText(getApplicationContext(), "\u5145\u503c\u5931\u8d25", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (msg.what == 0x22) {
                JSONObject object = new JSONObject();
                try {
                    object.put("userId", Constants.CURRENT_USER.getUserId());
                    object.put("orderSn", orderNumber);
                    object.put("amount", mMoney);
                    object.put("payType", 1);
                    Integer status = (Integer) msg.obj;
                    if (status == 0) {
                        object.put("orderStatus", 2);
                    } else if (status == -2) {
                        object.put("orderStatus", 3);
                    } else {
                        object.put("orderStatus", -1);
                    }
                    httpPost(Constants.SAVE_RECHARGE_URL, object, new HttpCallback());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(RechargeActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("充值失败");
                dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }


        }
    };

    BroadcastReceiver r = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null)
                if ((getPackageName() + ".pay.wx").equals(intent.getAction())) {
                    int errCode = intent.getIntExtra("result", -1);
                    mHandler.sendEmptyMessage(0);
                    mHandler.obtainMessage(0x22, errCode).sendToTarget();
                }
        }
    };
}