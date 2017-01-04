package com.enetic.push.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.activity.SeacherActivity;
import com.enetic.push.bean.SeacherBean;
import com.enetic.push.db.WoDbUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.List;

/**
 * Created by json on 2016/5/5.
 */
@Layout(R.layout.adapter_seacher)
public class SeacherAdapter extends HolderAdapter<SeacherBean> {

    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public SeacherAdapter(Context context, List<SeacherBean> data) {
        super(context, data);
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolders holders = (ViewHolders) viewHolder;
        final SeacherBean bn = mData.get(i);
        if (bn == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        holders.content.setText(bn.getContent()+"");
        holders.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    WoDbUtils.initialize(mContext).delete(SeacherBean.class, WhereBuilder.b("content","=",bn.getContent()));
                    ((SeacherActivity)(mContext)).uphostortList();
                } catch (DbException e) {
                    e.printStackTrace();
                }

               //Log.i("lyw","haahha");

            }
        });
    }

    class ViewHolders extends  BaseViewHolder{
        @ViewIn(R.id.conntent)
        private TextView content;
        @ViewIn(R.id.delete)
        private ImageView delete;
        public ViewHolders(View view) {
            super(view);
        }
    }
}
