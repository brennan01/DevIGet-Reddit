package com.brennan.deviget.redditposts.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.brennan.deviget.redditposts.api.RedditService;
import com.brennan.deviget.redditposts.domain.RedditNewsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GetRedditPostFragment extends Fragment {

    public static final String TAG = "GetRedditPostFragment";
    private Context mContext;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedditService redditService = retrofit.create(RedditService.class);
        Call<RedditNewsResponse> call = redditService.getTop(after == null ? "" : after, "50");

        if (mListener != null) {
            mListener.onRedditPostStart();
        }

        call.enqueue(new Callback<RedditNewsResponse>() {
            @Override
            public void onResponse(Call<RedditNewsResponse> call, Response<RedditNewsResponse> response) {
                RedditNewsResponse body = response.body();

                if(mListener != null){
                    mListener.onRedditPostComplete(body);
                }

            }

            @Override
            public void onFailure(Call<RedditNewsResponse> call, Throwable t) {
                if(mListener != null){
                    mListener.onRedditPostFailed(t);
                }

            }
        });
    }

}
