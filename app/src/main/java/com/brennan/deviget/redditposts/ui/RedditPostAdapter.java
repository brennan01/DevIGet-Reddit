package com.brennan.deviget.redditposts.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brennan.deviget.redditposts.R;
import com.brennan.deviget.redditposts.domain.RedditChildrenResponse;
import com.brennan.deviget.redditposts.domain.RedditDataResponse;
import com.brennan.deviget.redditposts.domain.RedditNewsDataResponse;
import com.squareup.picasso.Picasso;


import java.util.List;


public class RedditPostAdapter extends RecyclerView.Adapter<RedditPostAdapter.ViewHolder> {


    private RedditDataResponse mRedditDataResponse;
    private Listener mListener;

    public interface Listener {
        void onItemClick(int position, RedditChildrenResponse item);
    }

    public RedditPostAdapter(RedditDataResponse response) {
        mRedditDataResponse = response;
    }

    public void updateInfo(RedditDataResponse redditDataResponse){
        if(mRedditDataResponse == null){
            mRedditDataResponse = redditDataResponse;
        } else {
            //TODO update paging info
            mRedditDataResponse.getChildren().addAll(redditDataResponse.getChildren());
        }

        notifyDataSetChanged();
    }

    public List<RedditChildrenResponse> getItems() {
        return mRedditDataResponse == null ? null : mRedditDataResponse.getChildren();
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.post_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        RedditChildrenResponse item = mRedditDataResponse.getChildren().get(position);

        holder.bind(position, item);
    }


    @Override
    public int getItemCount() {
        return mRedditDataResponse == null || mRedditDataResponse.getChildren() == null ? 0
                : mRedditDataResponse.getChildren().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView author;
        TextView title;
        TextView time;
        TextView comments;
        ImageView thumbnail;
        ViewGroup item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (ViewGroup) itemView;
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            time = itemView.findViewById(R.id.time);
            comments = itemView.findViewById(R.id.comments);

            thumbnail = itemView.findViewById(R.id.thumbnail);

        }

        public void bind(final int position, final RedditChildrenResponse childrenResponse) {

            final RedditNewsDataResponse post = childrenResponse.getData();

            title.setText(post.getTitle());
            author.setText(post.getAuthorFullname());
            //TODO format for time ago format
            time.setText("time" + post.getCreated());
            comments.setText(item.getContext().getString(R.string.comments_count, post.getNumComments()));

            thumbnail.post(new Runnable() {
                @Override
                public void run() {
                    String iconUrl = post.getThumbnail();
                    iconUrl = iconUrl == null || iconUrl.isEmpty() ? null : iconUrl;
                    Picasso.get().load(iconUrl).into(thumbnail);
                }
            });

            this.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(position, childrenResponse);
                    }
                }
            });
        }
    }
}
