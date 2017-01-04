package com.enetic.push.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.bean.GoodsBean;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by moon1 on 2016/4/5.
 */
@Layout(R.layout.adapter_item_goods_about)
public class GoodsAboutAdapter extends HolderAdapter<GoodsBean> {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public GoodsAboutAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new Holder(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        GoodsBean bean = mData.get(position);
        if (bean == null) {
            ((Holder)holder).mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        ((Holder)holder).mGoodsName.setText(bean.getName());

    }
    class Holder extends BaseViewHolder{

        @ViewIn(R.id.goods_img)
        private ImageView mGoodsImg;
        @ViewIn(R.id.goods_name)
        private TextView mGoodsName;
        @ViewIn(R.id.goods_desp)
        private TextView mGoodsDesp;
        @ViewIn(R.id.goods_delete)
        private ImageView mGoodsDelete;
        @ViewIn(R.id.goods_share)
        private ImageView mGoodsShare;
        @ViewIn(R.id.goods_price)
        private TextView mGoodsPrice;
        public Holder(View view) {
            super(view);
        }
    }
}
