package org.xndroid.cn.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import org.xndroid.cn.R;
import org.xndroid.cn.utils.Reflection;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29 0029.
 */

public class xRecyclerView extends LinearLayout {
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private Context mContext;
    RecyclerViewPositionHelper mRecyclerViewHelper;
    protected int mPrevFirstVisibleItem;
    protected int mCurrIntPage;
    private int SCALL_STAUS = -1;
    private Boolean isLoading = false;
    private Boolean LoadMore = true;


    public xRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout, this);
        mContext = context;
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        setLayoutMaage(null);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                SCALL_STAUS = -1;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount;
                if (SCALL_STAUS == -1) {
                    SCALL_STAUS = 0;
                    mPrevFirstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                } else {
                    totalItemCount = mRecyclerViewHelper.findFirstVisibleItemPosition();
                    if (totalItemCount > mPrevFirstVisibleItem) {
                        SCALL_STAUS = 1;
                    } else if (totalItemCount < mPrevFirstVisibleItem) {
                        SCALL_STAUS = 2;
                    } else {
                        SCALL_STAUS = 0;
                    }
                    mPrevFirstVisibleItem = totalItemCount;
                }

                if (SCALL_STAUS == 1 && !isLoading) {
                    totalItemCount = mRecyclerViewHelper.getItemCount();
                    int firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                    int visibleItemCount = Math.abs(mRecyclerViewHelper.findLastVisibleItemPosition() - firstVisibleItem);
                    int lastAdapterPosition = totalItemCount - 1;
                    int lastVisiblePosition = firstVisibleItem + visibleItemCount - 1;

                    if (lastVisiblePosition >= lastAdapterPosition - 1 && LoadMore) {
                        LoadMore = false;
                        onLoadMore();
                        mProgressBar.setVisibility(VISIBLE);
                    }

                }

            }
        });

        this.mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                xRecyclerView.this.onRefresh();
            }
        });

    }

    public xRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public xRecyclerView(Context context) {
        this(context, null);
    }


    public void setLayoutMaage(RecyclerView.LayoutManager layoutMaage) {
        if (layoutMaage == null) {
            layoutMaage = new
                    LinearLayoutManager(mContext);
        }
        mRecyclerView.setLayoutManager(layoutMaage);
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(mRecyclerView);
    }


    public void onLoadMore() {
        this.isLoading = true;
        mCurrIntPage += 1;
        if (listener != null) {
            listener.requestData(mCurrIntPage);
        }
    }

    public void onRefresh() {
        this.isLoading = true;
        mCurrIntPage = 1;
        if (listener != null) {
            listener.requestData(mCurrIntPage);
        }
    }

    OnScrollListener listener;

    public interface OnScrollListener {
        void requestData(int pager);
    }

    public void look() {
        isLoading = false;
        LoadMore = true;
        mProgressBar.setVisibility(GONE);
        mRefreshLayout.setRefreshing(false);
    }


    public void setListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public void initLoak() {
        mRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    public void setAdapter(List<String> data, Class adaptersClass) {
        HolderAdapter adapter = (HolderAdapter) mRecyclerView.getAdapter();
        if (adapter == null) {
            adapter = (HolderAdapter) Reflection.generateObject(adaptersClass, new Class[]{Context.class, List.class}, new Object[]{mContext, data});
            mRecyclerView.setAdapter(adapter);
        } else if (mCurrIntPage == 1) {
            adapter.setListData(data);
        } else {
            adapter.appendData(data);
        }

        look();
    }
}
