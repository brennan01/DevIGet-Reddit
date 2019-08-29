package com.brennan.deviget.redditposts.ui.commons;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    /**
     * The total number of items in the dataset after the last load
     */
    private int mPreviousTotal = 0;
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private boolean mLoading = true;

    private final int mVisibleThreshold;


    public EndlessRecyclerOnScrollListener() {
        mVisibleThreshold = 5;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }

        int visibleThreshold = mVisibleThreshold;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            visibleThreshold *= ((GridLayoutManager)recyclerView.getLayoutManager()).getSpanCount();
        }

        if (!mLoading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            onLoadMore();

            mLoading = true;
        }
    }

    public void reset() {
        mPreviousTotal = 0;
        mLoading = true;
    }

    public abstract void onLoadMore();
}
