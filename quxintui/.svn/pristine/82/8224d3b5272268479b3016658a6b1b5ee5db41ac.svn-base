package com.enetic.push.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.bean.Bean;
import com.enetic.push.utils.AppTools;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * 充值记录
 * Created by json on 2016/10/08.
 */

@Layout(R.layout.adapter_charge)
public class ChargeAdapter extends HolderAdapter<Bean> {
    public ChargeAdapter(Context context, List<Bean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolers(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolers holders = (ViewHolers) viewHolder;
        Bean bean = mData.get(i);
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        holders.month.setText(bean.date);
        if (bean.paychecks != null) {
            for (Bean.PaychecksBean bn : bean.paychecks) {
                View layout = View.inflate(mContext, R.layout.layout_charge_type, null);
                TextView type = (TextView) layout.findViewById(R.id.type);
                TextView money = (TextView) layout.findViewById(R.id.money);
                TextView time = (TextView) layout.findViewById(R.id.time);
                if (bn.payType != null && bn.payType.equals("1"))
                    type.setText("微信");
                else type.setText("其他");
                if ("null".equals(bn.amount) || TextUtils.isEmpty(bn.amount + "")) {
                    money.setText("￥0.00");
                } else {
                    money.setText("￥" + bn.amount);
                }
                time.setText("" + AppTools.getNowTime(bn.createTime));
                ((LinearLayout) holders.mConvertView).addView(layout);
            }
        }


    }

    class ViewHolers extends BaseViewHolder {

        @ViewIn(R.id.month)
        TextView month;

        public ViewHolers(View view) {
            super(view);
        }
    }
}
