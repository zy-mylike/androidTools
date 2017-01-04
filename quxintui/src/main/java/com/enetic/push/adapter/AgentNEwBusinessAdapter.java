package com.enetic.push.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.activity.agent.BusiDetalActivity;
import com.enetic.push.bean.BusinessBean;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

@Layout(R.layout.adapter_proxy_new)
public class AgentNEwBusinessAdapter extends HolderAdapter<BusinessBean> {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public AgentNEwBusinessAdapter(Context context, List<BusinessBean> data) {
        super(context, data);
        setOnItemClickListener(new OnItemClickListener<BusinessBean>() {
            @Override
            public void onItemClick(View view, BusinessBean businessBean, int position) {
                mContext.startActivity(new Intent(mContext, BusiDetalActivity.class).putExtra("bean", businessBean));
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
        final BusinessBean bean = mData.get(position);
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        holders.product_sum.setText(bean.getProductCount() + "");
        holders.textView8.setText(bean.getAgentCount() + "");
        holders.name.setText(bean.getNickName());
        new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(holders.header_img, bean.getPortrait());

        if ("1".equals(bean.getIsFriend())) {
            holders.icon_delete.setImageResource(R.mipmap.icon_haved);
        }else{
            holders.icon_delete.setImageResource(R.mipmap.icon_add);
        }
        holders.icon_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handler != null) {
                    handler.obtainMessage(1, bean).sendToTarget();
                }
            }
        });
        holders.icon_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.obtainMessage(1, bean).sendToTarget(); // 添加好友。
            }
        });

        holders.rl_item.setOnLongClickListener(new View.OnLongClickListener() { //长按删除
            @Override
            public boolean onLongClick(View v) {
                if(handler==null){return true;}
                if("1".equals(bean.getIsFriend())){ //是好友
                    handler.obtainMessage(2, bean).sendToTarget();
                }else{ //不是好友，界面可以给出提示（看需求）

                }
                return true;
            }
        });
    }

    Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.rl_item)
        private RelativeLayout rl_item;
        @ViewIn(R.id.icon_add)
        private ImageView icon_add;
        @ViewIn(R.id.icon_delete)
        private ImageView icon_delete;
        @ViewIn(R.id.header_img)
        private ImageView header_img;
        @ViewIn(R.id.name)
        private TextView name;
        @ViewIn(R.id.textView8)
        private TextView textView8;
        @ViewIn(R.id.product_sum)
        private TextView product_sum;


        public ViewHolders(View view) {
            super(view);
        }
    }
}