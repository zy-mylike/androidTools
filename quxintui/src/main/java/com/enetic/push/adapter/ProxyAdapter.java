package com.enetic.push.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.enetic.esale.R;
import com.enetic.push.activity.agent.ProductDetailActivity;
import com.enetic.push.bean.AgentShareBean;
import com.enetic.push.utils.AsyncImageLoader;


import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by json on 2016/4/6.
 */
@Layout(R.layout.adapter_proxy_items)
public class ProxyAdapter extends HolderAdapter<AgentShareBean> {

    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public ProxyAdapter(final Context context, List<AgentShareBean> data) {
        super(context, data);
        setOnItemClickListener(new OnItemClickListener<AgentShareBean>() {
            @Override
            public void onItemClick(View view, AgentShareBean agentShareBean, int i) {
                context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra("id", agentShareBean.id + ""));
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
        final AgentShareBean bean = mData.get(position);
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        new org.eteclab.base.utils.AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(holders.header_img, bean.icon);
        holders.name.setText(bean.name);
        holders.textView8.setText(bean.shareReward + "");
        holders.share_nums.setText(bean.shareCount + "");
        holders.sucess_sum.setText(bean.sellCount + "");
        holders.sucess_price.setText(bean.totalReward + "");//成交收益


        holders.rl_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { //长按删除
                if (handler == null) {
                    return true;
                }
                if (show&&mData.size()>0) {
                    return true;
                }
                handler.obtainMessage(0, position, 0, bean.shareId+"").sendToTarget();
                return true;
            }
        });


        // 是否显示item_chose_yes_no 控件。给其设置图片。
        if (show&&mData.size()>0) {
            holders.item_chose_yes_no.setVisibility(View.VISIBLE);
        } else {
            holders.item_chose_yes_no.setVisibility(View.GONE);
        }

        holders.item_chose_yes_no.setOnClickListener(new View.OnClickListener() {
            Boolean chose = true;
            @Override
            public void onClick(View view) {
                if (chose) {
                    view.setBackgroundResource(R.mipmap.item_share_record_yes);
                    chose = false;
                    bean.tag = 1;
                } else {
                    view.setBackgroundResource(R.mipmap.item_share_record_no);
                    chose = true;
                    bean.tag = 0;
                }
            }
        });

        //是否全选
        if (choseall) {
            holders.item_chose_yes_no.setBackgroundResource(R.mipmap.item_share_record_yes);
            bean.tag = 1;
        } else {
            holders.item_chose_yes_no.setBackgroundResource(R.mipmap.item_share_record_no);
            bean.tag = 0;
        }

    }

    Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    // 是否显示item_chose_yes_no 控件。
    Boolean show;

    public void setShow(Boolean show) {
        this.show = show;
    }


    //是否全选
    Boolean choseall;

    public void setChoseall(Boolean choseall) {
        this.choseall = choseall;
    }


    // 求得所有选中项的shardId,并拼装为字符串。如：23，24，25
    public String getListString() {
        String str = "";
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).tag == 1) {
                list.add(mData.get(i).shareId + "");
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                str += list.get(i);
            } else {
                str += "," + list.get(i);
            }
        }
        return str;
    }


    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.rl_item)
        private RelativeLayout rl_item;
        @ViewIn(R.id.header_img)
        private ImageView header_img;
        @ViewIn(R.id.name)
        private TextView name;
        @ViewIn(R.id.textView8)
        private TextView textView8;
        @ViewIn(R.id.share_nums)
        private TextView share_nums;
        @ViewIn(R.id.sucess_price)
        private TextView sucess_price;
        @ViewIn(R.id.sucess_sum)
        private TextView sucess_sum;
        @ViewIn(R.id.item_chose_yes_no)
        private ImageView item_chose_yes_no;

        public ViewHolders(View view) {
            super(view);
        }
    }
}