package com.example.user.tvmasterretrofit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class Home extends AppCompatActivity {

    private ImageView imgPopularMovies;
    private ImageView imgPopularShows;
    private ImageView imgTopRatedMovies;
    private ImageView imgGotoYoutube;
    private ImageView imgGotoThemovmiedb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgPopularMovies=(ImageView) findViewById(R.id.imgPopularMovies);
        imgPopularShows=(ImageView) findViewById(R.id.imgPopularShows);
        imgTopRatedMovies=(ImageView) findViewById(R.id.imgTopRated);
        imgGotoYoutube=(ImageView) findViewById(R.id.imgGotoYoutube);
        imgGotoThemovmiedb=(ImageView) findViewById(R.id.imgGotoThemovies);



        imgPopularMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        imgTopRatedMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TopRatedMovies.class));
            }
        });
        imgPopularShows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Shows.class));
            }
        });
        imgGotoYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.youtube.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        imgGotoThemovmiedb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.themoviedb.org/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }



}
