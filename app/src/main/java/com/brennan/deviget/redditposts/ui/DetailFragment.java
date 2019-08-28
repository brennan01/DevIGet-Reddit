package com.brennan.deviget.redditposts.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.brennan.deviget.redditposts.R;

public class DetailFragment extends Fragment {

    private TextView mEmptyView;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mEmptyView = view.findViewById(R.id.non_selected_post_message);

        return view;
    }

    public void onItemClick(int itemId) {
        Toast.makeText(getActivity(), "Item Selected" + itemId, Toast.LENGTH_SHORT).show();
        mEmptyView.setVisibility(View.GONE);

    }
}
