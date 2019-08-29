package com.brennan.deviget.redditposts.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.brennan.deviget.redditposts.R;
import com.brennan.deviget.redditposts.domain.RedditChildrenResponse;
import com.brennan.deviget.redditposts.domain.RedditNewsDataResponse;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {

    private TextView mEmptyView;
    private TextView mAuthor;
    private TextView mTitle;
    private ImageView mThumbnail;


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

        return view;
    }

    public void onItemClick(RedditChildrenResponse item) {
        mEmptyView.setVisibility(View.GONE);

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



    }
}
