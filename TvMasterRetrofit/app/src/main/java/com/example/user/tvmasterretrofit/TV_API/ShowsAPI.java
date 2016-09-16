package com.example.user.tvmasterretrofit.TV_API;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 14/09/2016.
 */
public interface ShowsAPI {
    @GET("tv/popular?api_key=c94431cab6597dab45a1c126e000512f")
    Call<Show> getPopularShows();

    @GET("search/tv?api_key=968cca12b1a8492036b1e1e05af57e3f")
    Call<Show> searchShows(@Query("query")String query);

    class Factory
    {
        private static ShowsAPI service;

        public static ShowsAPI getInstance()
        {
            if(service==null)
            {
                Retrofit retrofit= new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("http://api.themoviedb.org/3/")
                        .build();
                service=retrofit.create(ShowsAPI.class);
                return service;
            }
            else
            {
                return service;
            }
        }

    }

}
