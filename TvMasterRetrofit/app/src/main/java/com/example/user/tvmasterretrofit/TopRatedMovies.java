package com.example.user.tvmasterretrofit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.tvmasterretrofit.MovieTrailer.Movie;
import com.example.user.tvmasterretrofit.MovieTrailer.MovieAPI;
import com.example.user.tvmasterretrofit.MovieTrailer.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedMovies extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView lblError;
    private RecyclerView mMoviesRecyclerView;
    RecycleViewAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lblError=(TextView) findViewById(R.id.lblError);
        mMoviesRecyclerView=(RecyclerView) findViewById(R.id.recyclerviewMovies);

        mMoviesRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mMoviesRecyclerView.setLayoutManager(mLayoutManager);


        getResponse_TopRatedMovies();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }






    public void getResponse_TopRatedMovies()
    {
        MovieAPI.Factory.getInstance().getTopRatedMovies().enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                List<Result> movies=response.body().getResults();

                mAdapter = new RecycleViewAdapter();
                mMoviesRecyclerView.setAdapter(mAdapter);
                mAdapter.setMovies(movies);
                mAdapter.notifyDataSetChanged();
                lblError.setText("");
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                lblError.setText(t.getCause().toString());
            }
        });
    }




















    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_rated_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_popular_movies) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        } else if (id == R.id.nav_popular_shows) {
            startActivity(new Intent(getApplicationContext(),Shows.class));
        } else if (id == R.id.nav_top_rated_movies) {
            //startActivity(new Intent(getApplicationContext(),TopRatedMovies.class));
        } else if (id == R.id.nav_youtube) {
            Uri uri=Uri.parse("https://www.youtube.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if(id==R.id.nav_themoviedb)
        {
            Uri uri=Uri.parse("https://www.themoviedb.org");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if(id==R.id.nav_home) {
            startActivity(new Intent(getApplicationContext(),Home.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
