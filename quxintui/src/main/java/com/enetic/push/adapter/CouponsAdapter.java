package com.enetic.push.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.bean.CouponBean;
import com.enetic.push.utils.AppTools;


import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/6/8.
 */
@Layout(R.layout.adapter_coupons)
public class CouponsAdapter extends HolderAdapter<CouponBean> {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public CouponsAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        ViewHolders holders = (ViewHolders) holder;
        CouponBean bean = mData.get(position);
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        Drawable drawable= mContext.getResources().getDrawable(R.mipmap.icon_cirle);
        if(bean.status !=1){
            drawable= mContext.getResources().getDrawable(R.drawable.gray_cirle);
            int color = Color.argb(0xff,0x92,0x93,0x92);
            holders.status.setText(bean.status ==2?"已使用":"不可用");
            holders.status.setTextColor(color);
            holders.title.setTextColor(color);

            holders.money.setTextColor(color);
            holders.mLimit.setTextColor(color);
            holders.mCondition.setTextColor(color);
            holders.mTime.setTextColor(color);
            holders.mConvertView.setBackgroundResource(R.mipmap.icon_coupons_haved);
        }
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        holders.mTime.setCompoundDrawablePadding(10);
        holders.mLimit.setCompoundDrawablePadding(10);
        holders.mCondition.setCompoundDrawablePadding(10);
        holders.mLimit.setCompoundDrawables(drawable,null,null,null);
        holders.mCondition.setCompoundDrawables(drawable,null,null,null);
        holders.mTime.setCompoundDrawables(drawable,null,null,null);



        holders.money.setText("￥ "+ AppTools.moneyFormat(bean.amount)+"元");
        holders.mCondition.setText("满"+bean.enough+"元可用");
        holders.mLimit.setText("限尾号 "+bean.limitPhone+" 的手机使用");
        holders.mTime.setText(AppTools.getNowDate(bean.startTime)+" 至 " + AppTools.getNowDate(bean.endTime));
    }

    class ViewHolders extends BaseViewHolder{

        @ViewIn(R.id.text1)
        private TextView title;
        @ViewIn(R.id.status)
        private TextView status;
        @ViewIn(R.id.coupons_money)
        private TextView money;
        @ViewIn(R.id.coupons_tiaojian)
        private TextView mCondition;
        @ViewIn(R.id.coupons_xianzhi)
        private TextView mLimit;
        @ViewIn(R.id.coupons_time)
        private TextView mTime;

        public ViewHolders(View view) {
            super(view);
        }
    }
}