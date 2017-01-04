package com.enetic.push.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by moon1 on 2016/2/17.
 */
public class TabPagerAdapter extends PagerAdapter {
    private List<View> pagerViews;

    public TabPagerAdapter(List<View> pagerViews) {
        this.pagerViews = pagerViews;
    }

    public void setPagerViews(List<View> pagerViews) {
        this.pagerViews = pagerViews;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pagerViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View frameLayout = pagerViews.get(position);
        container.addView(frameLayout);
        return frameLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(position>=0 && position<getCount())
        container.removeView(pagerViews.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
