package com.enetic.push.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.enetic.esale.R;
import com.enetic.push.Constants;
import com.enetic.push.bean.ShopOrderBean;
import com.enetic.push.utils.AppTools;
import com.enetic.push.utils.HttpUtil;
import com.lidroid.xutils.http.ResponseInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by moon1 on 2016/6/13.
 */
@Layout(R.layout.adapter_deal_list)
public class DealListAdapter extends HolderAdapter<ShopOrderBean> {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public DealListAdapter(Context context, List<ShopOrderBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        final ShopOrderBean bean = mData.get(position);
        final ViewHolders holders = (ViewHolders) holder;
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        holders.address.setText(bean.address);
        holders.mobile.setText(bean.mobile);
        holders.buy_date.setText(AppTools.getNowDate(bean.createTime));
        new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(((ViewHolders) holder).img, bean.icon);
        holders.name.setText(bean.memo);
        if (bean.shippingStatus == 0) { // 未发货
            holders.send_deal.setText("发货");
            holders.send_deal.setEnabled(true);
        } else if (bean.shippingStatus == 1) { // 部分发货
            holders.send_deal.setText("部分发货");
            holders.send_deal.setEnabled(false);
        } else if (bean.shippingStatus == 2) { // 已发货
            holders.send_deal.setText("已发货");
            holders.send_deal.setEnabled(false);
        } else if (bean.shippingStatus == 3) { // 部分退货
            holders.send_deal.setText("部分退货");
            holders.send_deal.setEnabled(false);
        } else if (bean.shippingStatus == 3) { // 已退货
            holders.send_deal.setText("已退货");
            holders.send_deal.setEnabled(false);
        }
        holders.send_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.httpGet(mContext, Constants.APP_Send_URL + bean.id + "&expressSn=" + bean.expressSn, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        holders.send_deal.setEnabled(false);
                        holders.send_deal.setText("已发货");
                    }
                });
            }
        });
        holders.number.setText(bean.goodsCount+"");

    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.address)
        TextView address;
        @ViewIn(R.id.mobile)
        TextView mobile;
        @ViewIn(R.id.buy_date)
        TextView buy_date;
        @ViewIn(R.id.number)
        TextView number;
        @ViewIn(R.id.send_deal)
        TextView send_deal;
        @ViewIn(R.id.name)
        TextView name;
        @ViewIn(R.id.img)
        ImageView img;

        public ViewHolders(View view) {
            super(view);
        }
    }
}
