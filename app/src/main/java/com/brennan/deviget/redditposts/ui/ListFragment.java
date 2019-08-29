package com.brennan.deviget.redditposts.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brennan.deviget.redditposts.R;
import com.brennan.deviget.redditposts.domain.RedditChildrenResponse;
import com.brennan.deviget.redditposts.domain.RedditDataResponse;


public class ListFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RedditPostAdapter(null);
        mAdapter.setListener(new RedditPostAdapter.Listener() {
            @Override
            public void onItemClick(int position, RedditChildrenResponse item) {
                if(mListener != null){
                    mListener.onItemClick(item);
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mDismisAll = view.findViewById(R.id.dismiss_all);

        return view;
    }

    public void setItems(RedditDataResponse data){
        mAdapter.updateInfo(data);
        if(data.getChildren().size() == 0){
            mDismisAll.setText(getString(R.string.dismill_all));
        } else {
            mDismisAll.setText(getString(R.string.dismill_all_count,  data.getChildren().size()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
