package com.enetic.push.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.activity.businesses.DealListActivity;
import com.enetic.push.bean.ShopOrder;
import com.enetic.push.utils.AsyncImageLoader;


import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;


/**
 * Created by moon1 on 2016/6/8.
 */
@Layout(R.layout.adapter_deal)
public class DealAdapter extends HolderAdapter<ShopOrder> {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public DealAdapter(Context context, List<ShopOrder> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        final ShopOrder order = mData.get(position);
        ViewHolders holders = (ViewHolders) holder;
        if (order == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(holders.img, order.icon);

        holders.name.setText(order.name);
        holders.task_money.setText(order.sellCount + "");
        holders.task_nums.setText(order.sellCount + "");
        holders.mConvertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, DealListActivity.class).putExtra("shopOrder", order));
            }
        });

    }


    class ViewHolders extends HolderAdapter.BaseViewHolder {
        @ViewIn(R.id.deal_img)
        ImageView img;
        @ViewIn(R.id.name)
        TextView name;
        @ViewIn(R.id.agent_num)
        TextView agent_num;

        @ViewIn(R.id.read_num)
        TextView read_num;

        @ViewIn(R.id.task_nums)
        TextView task_nums;
        @ViewIn(R.id.task_money)
        TextView task_money;

        public ViewHolders(View view) {
            super(view);
        }
    }
}
