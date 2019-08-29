package com.brennan.deviget.redditposts.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brennan.deviget.redditposts.R;
import com.brennan.deviget.redditposts.domain.RedditChildrenResponse;
import com.brennan.deviget.redditposts.domain.RedditDataResponse;

import java.util.List;


public class ListFragment extends Fragment {

    private static final String TAG = "ListFragment";
    private static final String POSTS_LIST = "posts_list";

    private Listener mListener;
    private RecyclerView mRecyclerView;
    private RedditPostAdapter mAdapter;
    private TextView mDismisAll;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null && savedInstanceState.getSerializable(POSTS_LIST) != null){
            RedditDataResponse data = (RedditDataResponse) savedInstanceState.getSerializable(POSTS_LIST);
            mAdapter.setRedditDataResponse(data);
        }

        List<RedditChildrenResponse> items = mAdapter.getItems();
        if(items == null || items.size() == 0){
            mDismisAll.setText(getString(R.string.dismill_all));
        } else {
            mDismisAll.setText(getString(R.string.dismill_all_count,  items.size()));
        }
    }

    public void setItems(RedditDataResponse data){
        mAdapter.updateInfo(data);

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
}
