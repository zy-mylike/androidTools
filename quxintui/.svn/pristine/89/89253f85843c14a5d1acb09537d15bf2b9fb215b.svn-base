package com.enetic.push.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.activity.businesses.ProductDetailActivity;
import com.enetic.push.bean.ProductBean;
import com.enetic.push.bean.TaskBean;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/6/7.
 */
@Layout(R.layout.adapter_box)
public class BoxAdapter extends HolderAdapter<TaskBean> {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public BoxAdapter(Context context, List<TaskBean> data) {
        super(context, data);
        setOnItemClickListener(new HolderAdapter.OnItemClickListener<TaskBean>(){
            @Override
            public void onItemClick(View view, TaskBean taskBean, int position) {
                Log.i("lyw","BoxAdapter:taskBean.getProductId():"+taskBean.getProductId());
                mContext.startActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("id", taskBean.getProductId()+""));
            }
        });

    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int position) {
        ViewHolders holders = (ViewHolders) holder;
        final TaskBean bean = mData.get(position);
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(holders.img, bean.getIcon());

        holders.name.setText(bean.getName());
        holders.task_money.setText(bean.getOrderReward() + ""); //成交奖励
        holders.shareReward.setText(bean.getShareReward() + ""); //分享奖励
        holders.finish_num.setText("" + bean.getSellCount() + "个"); //代理商家

        holders.rl_item.setOnLongClickListener(new View.OnLongClickListener() { //按钮删除功能改为长按删除。
            @Override
            public boolean onLongClick(View v) {
                if (handler != null) {
                    handler.obtainMessage(2, bean).sendToTarget();
                }
                return true;
            }
        });

        holders.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        holders.delete.setOnClickListener(new View.OnClickListener() {  //删除按钮
            @Override
            public void onClick(View v) {
                if (handler != null) {
                    handler.obtainMessage(2, bean).sendToTarget();
                }
            }
        });
        holders.share.setOnClickListener(new View.OnClickListener() { //分享按钮
            @Override
            public void onClick(View v) {
                if (handler != null) {
                    handler.obtainMessage(3, bean).sendToTarget();
                }
            }
        });
    }

    Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
        notifyDataSetChanged();
    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.rl_item)
        private RelativeLayout rl_item;
        @ViewIn(R.id.img)
        private ImageView img;
        @ViewIn(R.id.name)
        private TextView name;
        @ViewIn(R.id.finish_num)
        private TextView finish_num;
        @ViewIn(R.id.task_money)
        private TextView task_money;
        @ViewIn(R.id.shareReward)
        private TextView shareReward;
        @ViewIn(R.id.share) //分享按钮
        private Button share;
        @ViewIn(R.id.delete) //删除按钮
        private Button delete;

        public ViewHolders(View view) {
            super(view);
        }
    }
}
