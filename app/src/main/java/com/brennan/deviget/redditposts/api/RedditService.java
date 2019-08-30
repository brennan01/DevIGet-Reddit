package com.brennan.deviget.redditposts.api;

import com.brennan.deviget.redditposts.domain.Author;
import com.brennan.deviget.redditposts.domain.RedditNewsResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RedditService {
    @GET("/top.json")
    Call<RedditNewsResponse> getTop(@Query("after") String after, @Query("limit") String limit);

    @GET("/api/user_data_by_account_ids.json")
    Call<Map<String, Author>> getAuthor(@Query("ids") String ids);

}
