package com.example.user.tvmasterretrofit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.tvmasterretrofit.MovieTrailer.MovieAPI;
import com.example.user.tvmasterretrofit.MovieTrailer.Movie;
import com.example.user.tvmasterretrofit.MovieTrailer.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView lblError;
    private EditText txtSearch;
    private ImageView imgSearch;
    private RecyclerView mMoviesRecyclerView;
    RecycleViewAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;


    Button btnVideo;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState==null)
        {
            mMoviesRecyclerView=(RecyclerView) findViewById(R.id.lstMovies);
            txtSearch=(EditText) findViewById(R.id.txtSearch);
            imgSearch=(ImageView) findViewById(R.id.imgSearch);
            lblError=(TextView) findViewById(R.id.lblError);


            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mMoviesRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this);
            mMoviesRecyclerView.setLayoutManager(mLayoutManager);

            loadMovie();
        }
        else
        {

        }







        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMoviesOnClick();

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }








    public void SearchMoviesOnClick()
    {
        String searchText=txtSearch.getText().toString();
        if(searchText.length()>0)
        {
            SearchMovie( searchText);
        }
        else
        {
            Toast.makeText(getApplication(),"Can not search a movie without a title",Toast.LENGTH_LONG).show();
            loadMovie();
        }
    }


    public void loadMovie()
    {
        MovieAPI.Factory.getInstance().getMovieCall().enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                List<Result> movies=response.body().getResults();

                mAdapter = new RecycleViewAdapter();
                mMoviesRecyclerView.setAdapter(mAdapter);
                mAdapter.setMovies(movies);


                    lblError.setText("");
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                lblError.setText(t.getCause().toString());
            }
        });
    }

    public void SearchMovie( String title)
    {
        MovieAPI.Factory.getInstance().getSearchResponse(title).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.body()==null)
                {
                    lblError.setText("No Results Found");
                }
                else{

                    List<Result> movies=response.body().getResults();

                    mAdapter = new RecycleViewAdapter();
                    mMoviesRecyclerView.setAdapter(mAdapter);
                    mAdapter.setMovies(movies);

                    if(movies.isEmpty())
                    {
                        lblError.setText("No match found \""+txtSearch.getText().toString()+"\"");
                    }
                    else
                    {
                        lblError.setText("");
                    }
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

                lblError.setText(t.getCause().toString());

            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("rotate","rotated");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("rotate","rotated");
        super.onRestoreInstanceState(savedInstanceState);

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
        getMenuInflater().inflate(R.menu.main, menu);
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
            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
        } else if (id == R.id.nav_popular_shows) {
            startActivity(new Intent(getApplicationContext(),Shows.class));
        } else if (id == R.id.nav_top_rated_movies) {
            startActivity(new Intent(getApplicationContext(),TopRatedMovies.class));
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
