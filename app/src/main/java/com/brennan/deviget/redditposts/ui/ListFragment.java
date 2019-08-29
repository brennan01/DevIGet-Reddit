package com.brennan.deviget.redditposts.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.brennan.deviget.redditposts.R;
import com.brennan.deviget.redditposts.domain.RedditChildrenResponse;
import com.brennan.deviget.redditposts.domain.RedditDataResponse;
import com.brennan.deviget.redditposts.domain.RedditNewsResponse;
import com.brennan.deviget.redditposts.ui.commons.EndlessRecyclerOnScrollListener;

import java.util.List;


public class ListFragment extends Fragment implements GetRedditPostFragment.Listener {

    private static final String TAG = "ListFragment";
    private static final String POSTS_LIST = "posts_list";
    private static final String GET_REDDIT_POSTS_FRAGMENT_TAG = "GET_REDDIT_POSTS_FRAGMENT_TAG";

    private Listener mListener;
    private RecyclerView mRecyclerView;
    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;

    private RedditPostAdapter mAdapter;
    private TextView mDismisAll;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public static ListFragment newInstance() {
        return new ListFragment();
    }

    interface Listener {
        void onItemClick(RedditChildrenResponse item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Listener)) {
            throw new RuntimeException("Context must implement mListener");
        }
        mListener = (Listener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new RedditPostAdapter(null);
        mAdapter.setListener(new RedditPostAdapter.Listener() {
            @Override
            public void onItemClick(int position, RedditChildrenResponse item) {
                if(mListener != null){
                    mListener.onItemClick(item);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mDismisAll = view.findViewById(R.id.dismiss_all);

        mEndlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreInfo(mAdapter.getAfter());
            }
        };

        mRecyclerView.addOnScrollListener(mEndlessRecyclerOnScrollListener);

        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFreshInfo();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null && savedInstanceState.getSerializable(POSTS_LIST) != null){
            RedditDataResponse data = (RedditDataResponse) savedInstanceState.getSerializable(POSTS_LIST);
            setItems(data);
        } else {
            loadFreshInfo();
        }

    }

    private void loadFreshInfo(){
        mEndlessRecyclerOnScrollListener.reset();
        mAdapter.clear();
        loadMoreInfo("");
    }

    private void loadMoreInfo(String after) {

        FragmentManager fragmentManager = getFragmentManager();
        GetRedditPostFragment getRedditPostFragment = (GetRedditPostFragment) getFragmentManager()
                .findFragmentByTag(GET_REDDIT_POSTS_FRAGMENT_TAG);

        if(getRedditPostFragment == null){
            getRedditPostFragment = GetRedditPostFragment.newInstance();
            fragmentManager.beginTransaction()
                    .add(getRedditPostFragment, GET_REDDIT_POSTS_FRAGMENT_TAG)
                    .commit();
        }
        getRedditPostFragment.setListener(this);
        getRedditPostFragment.setContext(getContext());
        getRedditPostFragment.getRedditPosts(after);
    }

    public void setItems(RedditDataResponse data){
        mAdapter.updateInfo(data);
        List<RedditChildrenResponse> items = mAdapter.getItems();
        if(items == null || items.size() == 0){
            mDismisAll.setText(getString(R.string.dismill_all));
        } else {
            mDismisAll.setText(getString(R.string.dismill_all_count,  items.size()));
        }

    }

    private RedditDataResponse getItems(){
        return mAdapter.getRedditDataResponse();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(POSTS_LIST, getItems());

    }


    @Override
    public void onRedditPostStart() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRedditPostComplete(RedditNewsResponse redditNewsResponse) {
        mSwipeRefreshLayout.setRefreshing(false);
        setItems(redditNewsResponse.getData());
    }

    @Override
    public void onRedditPostFailed(Throwable e) {
        mSwipeRefreshLayout.setRefreshing(false);
        Log.d(TAG, "Error", e);

    }
}
