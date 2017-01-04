package com.enetic.push.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.bean.Bean;
;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/6/3.
 */
@Layout(R.layout.adapter_cash)
public class CashAdapter extends HolderAdapter<Bean> {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public CashAdapter(Context context, List<Bean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        ViewHolders holders = (ViewHolders) holder;
        if(position %3==0){
            holders.layout.setVisibility(View.VISIBLE);
            holders.month.setText("2016年"+(12 - 1)+"月");
        }else{
            holders.layout.setVisibility(View.GONE);
        }

    }

    class ViewHolders extends BaseViewHolder{
        @ViewIn(R.id.layout_top)
        View layout;
        @ViewIn(R.id.month)
        TextView month;
        @ViewIn(R.id.sum_money)
        TextView sum_money;
        @ViewIn(R.id.type)
        TextView type;
        @ViewIn(R.id.time)
        TextView time;
        @ViewIn(R.id.money)
        TextView money;

        public ViewHolders(View view) {
            super(view);
        }
    }
}
