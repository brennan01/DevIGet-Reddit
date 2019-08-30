package com.brennan.deviget.redditposts.ui;

import android.text.format.DateUtils;
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


import java.util.ArrayList;
import java.util.List;


public class RedditPostAdapter extends RecyclerView.Adapter<RedditPostAdapter.ViewHolder> {


    private RedditDataResponse mRedditDataResponse;
    private Listener mListener;
    private String mAfter;
    private ArrayList<RedditChildrenResponse> mReadPosts = new ArrayList<>();

    public void clear() {
        mRedditDataResponse = null;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mRedditDataResponse.getChildren().remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mRedditDataResponse.getChildren().size());

    }

    public interface Listener {
        void onItemClick(int position, RedditChildrenResponse item);
        void onDismissItemClick(int position, RedditChildrenResponse item);
    }

    public RedditPostAdapter() {
    }

    public void init(RedditDataResponse redditDataResponse, ArrayList<RedditChildrenResponse> readPosts){
        mReadPosts = readPosts;
        addItems(redditDataResponse);
    }

    public void addItems(RedditDataResponse redditDataResponse){
        if(mRedditDataResponse == null){
            mRedditDataResponse = redditDataResponse;
        } else {
            mRedditDataResponse.getChildren().addAll(redditDataResponse.getChildren());
        }

        mAfter = redditDataResponse.getAfter();
        notifyDataSetChanged();
    }

    public String getAfter() {
        return mAfter;
    }

    public List<RedditChildrenResponse> getItems() {
        return mRedditDataResponse == null ? null : mRedditDataResponse.getChildren();
    }

    public RedditDataResponse getRedditDataResponse() {
        return mRedditDataResponse;
    }


    public ArrayList<RedditChildrenResponse> getReadPosts() {
        return mReadPosts;
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
        View badge;
        View dismissButton;
        View dismissText;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (ViewGroup) itemView;
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            time = itemView.findViewById(R.id.time);
            comments = itemView.findViewById(R.id.comments);
            dismissButton = itemView.findViewById(R.id.dismiss_post_image_view);
            dismissText = itemView.findViewById(R.id.dismiss_post_text);
            badge = item.findViewById(R.id.badge);

            thumbnail = itemView.findViewById(R.id.thumbnail);

        }

        public void bind(final int position, RedditChildrenResponse childrenResponse) {

            final RedditNewsDataResponse post = childrenResponse.getData();

            title.setText(post.getTitle());
            author.setText(post.getAuthorFullname());
            CharSequence ago = DateUtils.getRelativeTimeSpanString(post.getCreated() * 1000, System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS);
            time.setText(ago);

            comments.setText(item.getContext().getString(R.string.comments_count, post.getNumComments()));

            thumbnail.post(new Runnable() {
                @Override
                public void run() {
                    String iconUrl = post.getThumbnail();
                    iconUrl = iconUrl == null || iconUrl.isEmpty() ? null : iconUrl;
                    Picasso.get().load(iconUrl).into(thumbnail);
                }
            });

            if(mReadPosts.contains(childrenResponse)){
                badge.setVisibility(View.INVISIBLE);
            } else {
                badge.setVisibility(View.VISIBLE);
            }



            //OnClick Listener
            this.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newPosition = getAdapterPosition();
                    RedditChildrenResponse item = mRedditDataResponse.getChildren().get(newPosition);
                    mReadPosts.add(item);
                    notifyItemChanged(newPosition);
                    if (mListener != null) {
                        mListener.onItemClick(newPosition, item);
                    }
                }
            });

            //OnDismissClick Listener
            View.OnClickListener onDismissClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int newPosition = getAdapterPosition();
                        RedditChildrenResponse item = mRedditDataResponse.getChildren().get(newPosition);

                        mListener.onDismissItemClick(newPosition, item);
                    }
                }
            };
            dismissText.setOnClickListener(onDismissClickListener);
            dismissButton.setOnClickListener(onDismissClickListener);

        }
    }
}
