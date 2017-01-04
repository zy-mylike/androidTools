package com.enetic.push.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.activity.agent.BusiDetalActivity;
import com.enetic.push.bean.BusinessBean;
import com.enetic.push.utils.CharacterParser;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Layout(R.layout.adapter_proxy_select)
public class AgentBusinessAdapter extends HolderAdapter<BusinessBean> implements SectionIndexer {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public AgentBusinessAdapter(Context context, List<BusinessBean> data) {
        super(context, data);
        Collections.sort(data, new PinyinComparator());
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

        if (type == 1) {
            holders.icon_delete.setVisibility(View.GONE);
        }
        if (type == 2) {
            holders.icon_delete.setVisibility(View.VISIBLE);
        }
        if ("1".equals(bean.getIsFriend())) {
            holders.icon_delete.setImageResource(R.mipmap.icon_haved);
        } else {
            holders.icon_delete.setImageResource(R.mipmap.icon_add);
        }
        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(holder.getPosition());
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (holder.getPosition() == getPositionForSection(section)) {
            holders.contact_tag.setVisibility(View.VISIBLE);
            holders.contact_tag.setText(getChar(bean.getNickName()));
        } else {
            holders.contact_tag.setVisibility(View.GONE);
        }

        holders.icon_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type == 1) { //商家页 icon_delete被隐藏
                    if ("0".equals(bean.getIsFriend())) { //不是好友 (此中情景不应该出现)
                        mHandler.obtainMessage(0, position, 0, bean).sendToTarget();
                    } else {
                        mHandler.obtainMessage(0, position, 0, bean).sendToTarget(); //删除 （）
                    }

                } else if (type == 2) { //搜索页

                    if ("0".equals(bean.getIsFriend())) { //不是好友
                        mHandler.obtainMessage(1, position, 0, bean).sendToTarget(); // 添加
                    } else {
                        mHandler.obtainMessage(1, position, 0, bean).sendToTarget(); // 不能重复添加
                    }
                }

            }
        });

        holders.ll_item.setOnLongClickListener(new View.OnLongClickListener() { //长按删除。
            @Override
            public boolean onLongClick(View v) {
                Log.i("lyw", "代理－商家：列表：onLongClick");
                if ("0".equals(bean.getIsFriend())) { //不是好友
                    mHandler.obtainMessage(2, position, 0, bean).sendToTarget(); //非好友不能删除
                } else { //是好友
                    mHandler.obtainMessage(0, position, 0, bean).sendToTarget(); //长按删除
                }
                return true;
            }
        });
    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.ll_item)
        private LinearLayout ll_item;
        @ViewIn(R.id.header_img)
        private ImageView header_img;
        @ViewIn(R.id.name)
        private TextView name;
        @ViewIn(R.id.textView8)
        private TextView textView8;
        @ViewIn(R.id.product_sum)
        private TextView product_sum;
        @ViewIn(R.id.contact_tag)
        private TextView contact_tag;
        @ViewIn(R.id.icon_delete)
        private ImageView icon_delete;

        public ViewHolders(View view) {
            super(view);
        }
    }

    public void setList(List<BusinessBean> list) {
        Collections.sort(list, new PinyinComparator());
        setListData(list);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return getChar(mData.get(position).getNickName()).charAt(0);
    }
//

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = getChar(mData.get(i).getNickName());
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    //
//    /**
//     * 提取英文的首字母，非英文字母用#代替。
//     *
//     * @param str
//     * @return
//     */
//    private String getAlpha(String str) {
//        String sortStr = str.trim().substring(0, 1).toUpperCase();
//        // 正则表达式，判断首字母是否是英文字母
//        if (sortStr.matches("[A-Z]")) {
//            return sortStr;
//        } else {
//            return "#";
//        }
//    }

    Handler mHandler;

    public void setHandler(Handler hnadler) {
        this.mHandler = hnadler;
    }

    int type = 1;

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

    public void removeItem(BusinessBean bean) {
        mData.remove(bean);
        notifyDataSetChanged();
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    private String getChar(String name) {
        CharacterParser characterParser = new CharacterParser();
        String pinyin = characterParser.getSelling(name);
        String sortString = pinyin.substring(0, 1).toUpperCase();

        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {

            sortString = sortString.toUpperCase();
        } else {
            sortString = "#";
        }

        return sortString;

    }

    //
    public class PinyinComparator implements Comparator<BusinessBean> {
        public int compare(BusinessBean o1, BusinessBean o2) {

            String s1 = getChar(o1.getNickName());
            String s2 = getChar(o2.getNickName());

            if (s1.equals("@")
                    || s2.equals("#")) {
                return -1;
            } else if (s1.equals("#")
                    || s2.equals("@")) {
                return 1;
            } else {
                return s1.compareTo(s2);
            }
        }
    }
}