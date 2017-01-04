//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.xndroid.cn.widget;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

class RecyclerViewPositionHelper {
    final RecyclerView recyclerView;
    final LayoutManager layoutManager;

    RecyclerViewPositionHelper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.layoutManager = recyclerView.getLayoutManager();
    }

    public static RecyclerViewPositionHelper createHelper(RecyclerView recyclerView) {
        if(recyclerView == null) {
            throw new NullPointerException("Recycler View is null");
        } else {
            return new RecyclerViewPositionHelper(recyclerView);
        }
    }

    public int getItemCount() {
        return this.layoutManager == null?0:this.layoutManager.getItemCount();
    }

    public int findFirstVisibleItemPosition() {
        View child = this.findOneVisibleChild(0, this.layoutManager.getChildCount(), false, true);
        return child == null?-1:this.recyclerView.getChildAdapterPosition(child);
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View child = this.findOneVisibleChild(0, this.layoutManager.getChildCount(), true, false);
        return child == null?-1:this.recyclerView.getChildAdapterPosition(child);
    }

    public int findLastVisibleItemPosition() {
        View child = this.findOneVisibleChild(this.layoutManager.getChildCount() - 1, -1, false, true);
        return child == null?-1:this.recyclerView.getChildAdapterPosition(child);
    }

    public int findLastCompletelyVisibleItemPosition() {
        View child = this.findOneVisibleChild(this.layoutManager.getChildCount() - 1, -1, true, false);
        return child == null?-1:this.recyclerView.getChildAdapterPosition(child);
    }

    public View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible, boolean acceptPartiallyVisible) {
        OrientationHelper helper;
        if(this.layoutManager.canScrollVertically()) {
            helper = OrientationHelper.createVerticalHelper(this.layoutManager);
        } else {
            helper = OrientationHelper.createHorizontalHelper(this.layoutManager);
        }

        int start = helper.getStartAfterPadding();
        int end = helper.getEndAfterPadding();
        int next = toIndex > fromIndex?1:-1;
        View partiallyVisible = null;

        for(int i = fromIndex; i != toIndex; i += next) {
            View child = this.layoutManager.getChildAt(i);
            int childStart = helper.getDecoratedStart(child);
            int childEnd = helper.getDecoratedEnd(child);
            if(childStart < end && childEnd > start) {
                if(!completelyVisible) {
                    return child;
                }

                if(childStart >= start && childEnd <= end) {
                    return child;
                }

                if(acceptPartiallyVisible && partiallyVisible == null) {
                    partiallyVisible = child;
                }
            }
        }

        return partiallyVisible;
    }
}
