package com.brennan.deviget.redditposts.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.brennan.deviget.redditposts.R;
import com.brennan.deviget.redditposts.domain.RedditChildrenResponse;
import com.brennan.deviget.redditposts.domain.RedditNewsDataResponse;
import com.brennan.deviget.redditposts.utils.UrlUtils;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    private static final String ITEM = "item";
    private static final String TAG = "DetailFragment";
    private TextView mEmptyView;
    private TextView mAuthor;
    private TextView mTitle;
    private ImageView mThumbnail;
    private View mContentSection;

    private RedditChildrenResponse mItem;


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
        mAuthor = view.findViewById(R.id.author);
        mThumbnail = view.findViewById(R.id.thumbnail);
        mTitle = view.findViewById(R.id.title);
        mContentSection = view.findViewById(R.id.post_content);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(savedInstanceState != null && savedInstanceState.getSerializable(ITEM) != null){
            mItem = (RedditChildrenResponse) savedInstanceState.getSerializable(ITEM);
        }
        onItemClick(mItem);

    }

    public void onItemClick(RedditChildrenResponse item) {
        mItem = item;
        if (mItem == null) {
            mEmptyView.setVisibility(View.VISIBLE);
            mContentSection.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mContentSection.setVisibility(View.VISIBLE);

            final RedditNewsDataResponse post = item.getData();
            mTitle.setText(post.getTitle());
            mAuthor.setText(post.getAuthorFullname());

            mThumbnail.post(new Runnable() {
                @Override
                public void run() {
                    String iconUrl = post.getThumbnail();
                    iconUrl = iconUrl == null || iconUrl.isEmpty() ? null : iconUrl;
                    Picasso.get().load(iconUrl).into(mThumbnail);
                }
            });

            mThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UrlUtils.openWebPage(getContext(), post.getThumbnail());
                }
            });
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ITEM, mItem);
    }
}
