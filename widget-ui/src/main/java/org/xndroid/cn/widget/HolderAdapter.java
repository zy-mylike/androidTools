package org.xndroid.cn.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.xndroid.cn.annotation.init.ViewInobject;

import java.util.List;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public abstract class HolderAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    protected List<T> mData;
    protected OnItemClickListener<T> mItemClickListener;


    public void setListData(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void appendData(List<T> mData) {
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    public HolderAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return generateViewHolder(ViewInobject.initAdapterLayoutViews(this, mContext, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        bindView(holder, position);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> mItemClickListener.setOnItemClickListener(v, mData.get(position), position));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<T> getData() {
        return this.mData;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
            ViewInobject.injectViewField2Obj(this, itemView);
        }
    }

    protected abstract RecyclerView.ViewHolder generateViewHolder(View paramView);

    protected abstract void bindView(RecyclerView.ViewHolder ViewHolder, int paramInt);

    public interface OnItemClickListener<T> {
        void setOnItemClickListener(View view, T o, int position);
    }
}
