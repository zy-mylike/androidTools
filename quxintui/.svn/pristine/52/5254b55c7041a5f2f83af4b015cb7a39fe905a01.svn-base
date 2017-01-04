package com.enetic.push.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.bean.AgentBean;


import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/6/6.
 */
@Layout(R.layout.adapter_business)
public class BusinessAdapter extends HolderAdapter<AgentBean> {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public BusinessAdapter(Context context, List<AgentBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        ViewHolders holders = (ViewHolders) holder;
        AgentBean bean =
                mData.get(position);
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        holders.visit.setText(bean.getVisitCount());
        holders.textView8.setText(bean.getBusinessCount());
        holders.name.setText(bean.getNickName());
        new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(holders.header_img, bean.getPortrait());
        holders.icon_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"icon_add",0).show();

            }
        });
        holders.icon_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"icon_delete",0).show();
            }
        });
    }

    class ViewHolders extends BaseViewHolder {
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
        @ViewIn(R.id.visit)
        private TextView visit;
        @ViewIn(R.id.contact_tag)
        private TextView contact_tag;

        public ViewHolders(View view) {
            super(view);
        }
    }
}
