package com.enetic.push.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.enetic.esale.R;
import com.enetic.push.activity.SeacherActivity;
import com.enetic.push.activity.businesses.AgentAllActivity;
import com.enetic.push.activity.businesses.SelectAgentActivity;
import com.enetic.push.bean.AgentBean;
import com.enetic.push.utils.CharacterParser;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.adapter.HolderAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by json on 2016/6/20.
 */
@Layout(R.layout.adapter_select_agent)
public class SelectAdapter extends HolderAdapter<AgentBean> implements SectionIndexer {
    /**
     * 使用Holder进行优化的Adapter，继承于RecyclerView.Adapter，适用于RecyclerView
     *
     * @param context
     * @param data    泛型数据
     */
    public SelectAdapter(Context context, List<AgentBean> data) {
        super(context, data);
        Collections.sort(data, new PinyinComparator());
    }

    @Override
    protected RecyclerView.ViewHolder generateViewHolder(View view) {
        return new ViewHolders(view);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int position) {
        final ViewHolders holders = (ViewHolders) holder;
        final AgentBean bean = mData.get(position);
        if (bean == null) {
            holders.mConvertView.setVisibility(View.INVISIBLE);
            return;
        }
        holders.visit.setText(bean.getVisitCount());
        holders.textView8.setText(bean.getBusinessCount());
        holders.name.setText(bean.getNickName());
        new AsyncImageLoader(mContext, R.mipmap.ic_launcher, R.mipmap.ic_launcher).display(holders.header_img, bean.getPortrait());

        if (bean.isChosed == 0) {
            holders.box.setChecked(false);
        } else {
            holders.box.setChecked(true);
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

        holders.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) { //发送至代理搜索进入此adapter.
                    if (holders.box.isChecked()) {
                        bean.isChosed = 1;
                    } else {
                        bean.isChosed = 0;
                        //((SeacherActivity) (mContext)).removeSelect(bean);
                    }
                    ((SeacherActivity) (mContext)).addSelect(bean);
                } else if (type == 2) { //发送至代理进入此adapter.
                    if (holders.box.isChecked()) {
                        bean.isChosed = 1;
                        //((SelectAgentActivity) (mContext)).addSelect(bean);
                    } else {
                        bean.isChosed = 0;
                        //((SelectAgentActivity) (mContext)).removeSelect(bean);
                    }
                }
            }
        });
    }

    int type = 1;

    public void setType(int type) {
        this.type = type;
    }

    public void notifyDataSetChanged(List<AgentBean> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }


    class ViewHolders extends BaseViewHolder {
        @ViewIn(R.id.select)
        private CheckBox box;
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

    public void setList(List<AgentBean> list) {
        Collections.sort(list, new PinyinComparator());
        setListData(list);
    }
//
//    @Override
//    protected void setViews(Viewholder holder,
//                            SortModel t) {
//        final SortModel mContent = t;
//        //根据position获取分类的首字母的Char ascii值
//        int section = getSectionForPosition(holder.getPosition());
//
//        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
//        if (holder.getPosition() == getPositionForSection(section)) {
//            holder.getView(R.id.contact_tag).setVisibility(View.VISIBLE);
//            holder.setText(R.id.contact_tag, mContent.getSortLetters());
//        } else {
//            holder.getView(R.id.contact_tag).setVisibility(View.GONE);
//        }
//        holder.setText(R.id.name, mContent.getObj().toString());
//        holder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        }, R.id.delete);
//    }
//

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
}