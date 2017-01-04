package com.enetic.push.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.enetic.esale.R;
import com.enetic.push.activity.businesses.AgentDetalActivity;
import com.enetic.push.bean.AgentBean;
import com.enetic.push.utils.CharacterParser;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Layout(R.layout.adapter_proxy)
public class SortAgentAdapter extends HolderAdapter<AgentBean> implements SectionIndexer {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public SortAgentAdapter(Context context, List<AgentBean> data) {
        super(context, data);
        Collections.sort(mData, new PinyinComparator());
        setOnItemClickListener(new HolderAdapter.OnItemClickListener<AgentBean>() {
            @Override
            public void onItemClick(View view, AgentBean agentBean, int position) {
                mContext.startActivity(new Intent(mContext, AgentDetalActivity.class).putExtra("bean", agentBean));
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
        final AgentBean bean = mData.get(position);
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        holders.visit.setText(bean.getVisitCount());
        holders.textView8.setText(bean.getBusinessCount());
        holders.name.setText(bean.getNickName());
        new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(holders.header_img, bean.getPortrait());

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(holder.getPosition());
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (holder.getPosition() == getPositionForSection(section)) {
            holders.contact_tag.setVisibility(View.VISIBLE);
            holders.contact_tag.setText(getChar(bean.getNickName()));
        } else {
            holders.contact_tag.setVisibility(View.GONE);
        }

        if (this.type == 0) {
           //holders.delete.setImageResource(R.mipmap.icon_delete);
           holders.delete.setVisibility(View.GONE);
        } else {
            if ("1".equals(bean.getIsFriend())) {
                holders.delete.setImageResource(R.mipmap.icon_haved);
            }else{
                holders.delete.setImageResource(R.mipmap.icon_add);
            }
        }

        holders.delete.setOnClickListener(new View.OnClickListener() { //监听的item中此ImageView的点击事件。
            @Override
            public void onClick(View v) {
                if (mHandler == null) {
                    return;
                }
                if (type == 0 || "0".equals(bean.getIsFriend())) {
                    mHandler.obtainMessage(0, position, 0, bean).sendToTarget();
                }
                if (type == 1) {
                    mHandler.obtainMessage(0, position, 0, bean).sendToTarget();
                }
            }
        });

        holders.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { //长按删除功能
                //Log.i("lyw","OnLongClickListener");
                if (mHandler == null) {
                    return true;
                }
                if(type==0){ //有长按删除功能
                    if("0".equals(bean.getIsFriend())){ //不是代理的情况
                        //提示不能删除。
                        mHandler.obtainMessage(2, position, 0, bean).sendToTarget();
                    }else { //是代理的情况
                        //弹出提示是否删除。
                        mHandler.obtainMessage(1, position, 0, bean).sendToTarget();
                    }
                }else if(type==1){
                    return true;
                }else{
                    mHandler.obtainMessage(1, position, 0, bean).sendToTarget();
                }
                return true;
            }
        });

    }

    Handler mHandler;

    public void setHandler(Handler hnadler) {
        this.mHandler = hnadler;
    }

    int type = 1;

    public void setType(int type) {
        this.type = type;
        notifyDataSetChanged();
    }

    public void removeItem(AgentBean bean) {
        mData.remove(bean);
        notifyDataSetChanged();
    }

    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.delete)
        private ImageView delete;
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
        @ViewIn(R.id.ll_item)
        private LinearLayout ll_item;

        public ViewHolders(View view) {
            super(view);
        }
    }

    public void setList(List<AgentBean> list) {
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
//
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
    public class PinyinComparator implements Comparator<AgentBean> {
        public int compare(AgentBean o1, AgentBean o2) {

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


//    public class PinyinComparator implements Comparator<SortModel> {
//
//        public int compare(SortModel o1, SortModel o2) {
//            if (o1.getSortLetters().equals("@")
//                    || o2.getSortLetters().equals("#")) {
//                return -1;
//            } else if (o1.getSortLetters().equals("#")
//                    || o2.getSortLetters().equals("@")) {
//                return 1;
//            } else {
//                return o1.getSortLetters().compareTo(o2.getSortLetters());
//            }
//        }
}