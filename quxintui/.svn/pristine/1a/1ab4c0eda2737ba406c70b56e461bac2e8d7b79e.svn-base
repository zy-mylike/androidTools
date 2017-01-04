package com.enetic.push.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.activity.agent.ProductDetailActivity;
import com.enetic.push.bean.AgentTaskBean;


import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/5/30.
 */
@Layout(R.layout.adapter_task)
public class TaskAdpter extends HolderAdapter<AgentTaskBean> {
    public TaskAdpter(final Context context, List<AgentTaskBean> data) {
        super(context, data);
        setOnItemClickListener(new OnItemClickListener<AgentTaskBean>() {
            @Override
            public void onItemClick(View view, AgentTaskBean bean, int i) {
                context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra("id", bean.getId() + ""));
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolders holders = (ViewHolders) viewHolder;
        final AgentTaskBean bean = mData.get(i);
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(holders.img, bean.getIcon());
        holders.taskName.setText(bean.getName());
        holders.profile.setText(bean.getDesp());
        holders.taskNums.setText(bean.getSellCount() + "");
        holders.taskMoney.setText(bean.getOrderReward() + "");

        holders.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHandler != null) {
                    mHandler.obtainMessage(1, bean).sendToTarget();
                }
            }
        });
        holders.delete.setVisibility(this.deleteVisibility);
        holders.ll_share.setVisibility(this.ll_shareVisibility);
        holders.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHandler != null) {
                    mHandler.obtainMessage(0, bean).sendToTarget();
                }
            }
        });

        holders.rl_item.setOnLongClickListener(new View.OnLongClickListener() {  //长按删除
            @Override
            public boolean onLongClick(View v) {

                if (mHandler == null) {
                    return true;
                }
                if (type == 2) { //商品列表，无长按事件
                    return true;
                }
                if (type == 3) { //商品列表搜索，无长按事件
                    return true;
                }

                mHandler.obtainMessage(0, bean).sendToTarget();

                return true;
            }
        });

        holders.iv_share_friends.setOnClickListener(new View.OnClickListener() { //分享至朋友圈。
            @Override
            public void onClick(View v) {

                if (mHandler != null) {
                    mHandler.obtainMessage(2, bean).sendToTarget();
                }
            }
        });

        holders.iv_share_agent.setOnClickListener(new View.OnClickListener() { //分享至微信好友。
            @Override
            public void onClick(View v) {
                if (mHandler != null) {
                    mHandler.obtainMessage(3, bean).sendToTarget();
                }
            }
        });

    }

    int type = 1;

    public void setType(int i) {
        this.type = i;
    }

    Handler mHandler;

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    //int deleteVisibility = View.VISIBLE;
    int deleteVisibility = View.GONE;

    public void setDeleteVisibility(int deleteVisibility) {
        this.deleteVisibility = deleteVisibility;
    }

    int ll_shareVisibility = View.VISIBLE;

    public void setLl_shareVisibility(int ll_shareVisibility) {
        this.ll_shareVisibility = ll_shareVisibility;
    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.rl_item)
        private RelativeLayout rl_item;
        @ViewIn(R.id.iv_share_friends)
        private ImageView iv_share_friends; //分享至朋友圈。
        @ViewIn(R.id.iv_share_agent)
        private ImageView iv_share_agent; //分享至微信好友。
        @ViewIn(R.id.img)
        private ImageView img;
        @ViewIn(R.id.name)
        private TextView taskName;
        @ViewIn(R.id.delete)
        private ImageView delete;

        @ViewIn(R.id.ll_share)
        private LinearLayout ll_share;
        @ViewIn(R.id.profile)
        private TextView profile;
        @ViewIn(R.id.share)
        private Button share;
        @ViewIn(R.id.task_nums)
        private TextView taskNums;
        @ViewIn(R.id.task_money)
        private TextView taskMoney;

        public ViewHolders(View view) {
            super(view);
        }
    }
}
