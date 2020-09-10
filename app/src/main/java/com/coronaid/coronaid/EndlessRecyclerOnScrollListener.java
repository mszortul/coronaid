package com.coronaid.coronaid;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();
    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    int lastItemPos;
    NewsAdapter newsAdapter;

    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, NewsAdapter newsAdapter) {
        this.mLinearLayoutManager = linearLayoutManager;
        this.newsAdapter = newsAdapter;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        //Log.i("items on list", String.valueOf(newsAdapter.getItemCount()));
        //Log.i("last visible position", String.valueOf(mLinearLayoutManager.findLastCompletelyVisibleItemPosition()));

        if (newsAdapter.getItemCount() - 1 == mLinearLayoutManager.findLastCompletelyVisibleItemPosition()) {
            onLoadMore(current_page);
        }
    }

    public abstract void onLoadMore(int lastItemPos);
}
