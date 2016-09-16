package com.example.user.tvmasterretrofit.MovieTrailer;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 08/09/2016.
 */
public interface MovieAPI {

    String URL="https://api.themoviedb.org/3/";
    @GET("movie/popular?api_key=968cca12b1a8492036b1e1e05af57e3f")
    Call<Movie> getMovieCall();

    @GET("search/movie?api_key=968cca12b1a8492036b1e1e05af57e3f")
    Call<Movie> getSearchResponse(@Query("query") String query);

    @GET("discover/movie?primary_release_year=2015&certification=R&api_key=c94431cab6597dab45a1c126e000512f&certification_country=US&certification=R&sort_by=vote_average.desc")
    Call<Movie> getTopRatedMovies();


    class Factory{
        private static MovieAPI service;

        public static MovieAPI getInstance()
        {
            if(service==null)
            {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(URL)
                        .build();

                service = retrofit.create(MovieAPI.class);

                return  service;
            }
            else
            {
                return service;
            }

        }
    }
}
