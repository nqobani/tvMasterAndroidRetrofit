package com.example.user.tvmasterretrofit.MovieDetails;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 09/09/2016.
 */
public interface TrailerAPI {

    @GET("movie/{id}/videos")
    Call<Trailer> getTrailers(@Path("id") int id, @Query("id") int id2, @Query("api_key") String api_key);

    class Factory
    {
        private static TrailerAPI service=null;

        public static TrailerAPI GetInstance()
        {
            if(service==null)
            {
                Retrofit retrofit= new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://api.themoviedb.org/3/")
                        .build();

                 service= retrofit.create(TrailerAPI.class);

                return service;
            }
            else
            {
                return service;
            }
        }
    }
}
