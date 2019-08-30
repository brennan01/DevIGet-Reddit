package com.brennan.deviget.redditposts.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.brennan.deviget.redditposts.api.RedditService;
import com.brennan.deviget.redditposts.domain.Author;
import com.brennan.deviget.redditposts.domain.RedditNewsResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GetRedditPostFragment extends Fragment {

    public static final String TAG = "GetRedditPostFragment";
    private Context mContext;
    private GetPostsTask mTask;
    private String mAfter;


    public interface Listener {
        void onRedditPostStart();

        void onRedditPostComplete(RedditNewsResponse redditNewsResponse);

        void onRedditPostFailed(Throwable e);
    }

    private Listener mListener;

    public GetRedditPostFragment() {
    }

    public static GetRedditPostFragment newInstance() {
        GetRedditPostFragment fragment = new GetRedditPostFragment();
        return fragment;
    }

    public void setListener(Listener mListener) {
        this.mListener = mListener;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getRedditPosts(String after){

        cancelTask();
        mAfter = after;
        mTask = new GetPostsTask();
        if (mListener != null){
            mListener.onRedditPostStart();
        }
        mTask.execute();

    }
    public void cancelTask() {
        if (mTask != null) {
            mTask.cancel(true);
        }
    }

    private class GetPostsTask extends AsyncTask<Void, Void,RedditNewsResponse> {

        Exception exception;

        @Override
        protected RedditNewsResponse doInBackground(Void... voids) {
            RedditNewsResponse response;
            try {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://www.reddit.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RedditService redditService = retrofit.create(RedditService.class);
                Call<RedditNewsResponse> call = redditService.getTop(mAfter == null ? "" : mAfter, "50");

                response = call.execute().body();

                Call<Map<String, Author>> author = redditService.getAuthor(response.getData().getAuthorIds());
                Map<String, Author> authorMap = author.execute().body();

                response.getData().setAuthorLegibleNames(authorMap);

            } catch (Exception e) {
                response = null;
                exception = e;
                Log.e(TAG, "Failed getting access API", e);

            }

            return response;
        }

        @Override
        protected void onPostExecute(RedditNewsResponse response) {
            super.onPostExecute(response);
            if(response == null){
                mListener.onRedditPostFailed(exception);
            } else {
                mListener.onRedditPostComplete(response);
            }
        }

    }

}
