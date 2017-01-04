package com.enetic.push.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.activity.businesses.ProductDetailActivity;
import com.enetic.push.bean.ProductBean;
import com.enetic.push.utils.AppTools;


import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/4/1.
 */
@Layout(R.layout.adapter_product)
public class ProductAdapter extends HolderAdapter<ProductBean> {
    public ProductAdapter(Context context, List<ProductBean> list) {
        super(context, list);

        setOnItemClickListener(new HolderAdapter.OnItemClickListener<ProductBean>() {
            @Override
            public void onItemClick(View view, ProductBean productBean, int position) {
                mContext.startActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("id", productBean.getId()));
            }
        });

    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int position) {
        final ProductBean bean = mData.get(position);
        ViewHolders holders = (ViewHolders) holder;
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(holders.img, bean.getIcon());
        holders.name.setText(bean.getName());
        holders.task_money.setText(bean.getPrice() + "元"); //价格
        holders.task_nums.setText(AppTools.numFormat(bean.getRepertory()) + "件"); //库存
        holders.product_sum.setText(AppTools.numFormat(bean.getSellCount()) + "件"); //销售
        holders.profile.setText(bean.getDesp()); //商品描述


        holders.rl_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(handler==null){return true;}
                handler.obtainMessage(0,bean).sendToTarget();
                return true;
            }
        });
    }

    Handler handler=null;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void removeItem(ProductBean bean) {
        mData.remove(bean);
        notifyDataSetChanged();
    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.rl_item)
        RelativeLayout rl_item;
        @ViewIn(R.id.img)
        ImageView img;
        @ViewIn(R.id.name)
        TextView name;
        @ViewIn(R.id.profile)
        TextView profile;
        @ViewIn(R.id.task_money)
        TextView task_money;
        @ViewIn(R.id.task_nums)
        TextView task_nums;
        @ViewIn(R.id.product_sum)
        TextView product_sum;

        public ViewHolders(View view) {
            super(view);
        }
    }
}