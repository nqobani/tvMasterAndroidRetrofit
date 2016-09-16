package com.example.user.tvmasterretrofit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.tvmasterretrofit.TV_API.RecyclerViewAdapterShows;
import com.example.user.tvmasterretrofit.TV_API.Result;
import com.example.user.tvmasterretrofit.TV_API.Show;
import com.example.user.tvmasterretrofit.TV_API.ShowsAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shows extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText txtSearch;
    private ImageView imgSearch;
    private TextView lblError;
    private RecyclerView mMoviesRecyclerView;
    RecyclerViewAdapterShows mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState==null) {
            getShow();
        }


        lblError=(TextView) findViewById(R.id.lblError);
        mMoviesRecyclerView=(RecyclerView) findViewById(R.id.recyclerviewMovies);
        txtSearch=(EditText) findViewById(R.id.txtSearch);
        imgSearch=(ImageView) findViewById(R.id.imgSearch);

        mMoviesRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mMoviesRecyclerView.setLayoutManager(mLayoutManager);




        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=txtSearch.getText().toString();

                if(title.length()>0)
                {
                    searchShows(title);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Cannot search for a movie without a title",Toast.LENGTH_LONG).show();
                    getShow();
                }

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




    public void getShow()
    {
        ShowsAPI.Factory.getInstance().getPopularShows().enqueue(new Callback<Show>() {
            @Override
            public void onResponse(Call<Show> call, Response<Show> response) {
                List<Result> shows=response.body().getResults();

                mAdapter = new RecyclerViewAdapterShows();
                mMoviesRecyclerView.setAdapter(mAdapter);
                mAdapter.setMovies(shows);


                mAdapter.notifyDataSetChanged();
                lblError.setText("");
            }

            @Override
            public void onFailure(Call<Show> call, Throwable t) {
                lblError.setText(t.getCause().getMessage().toString());
            }
        });
    }

    public void searchShows(final String title)
    {
        ShowsAPI.Factory.getInstance().searchShows(title).enqueue(new Callback<Show>() {
            @Override
            public void onResponse(Call<Show> call, Response<Show> response) {
                if(response.body()==null)
                {
                    lblError.setText("Failed to Load Data, please check you Internet connection and try agian or try again after few minutes");
                }
                else
                {
                    if(response.body().getResults().size()>0)
                    {
                        List<Result> shows=response.body().getResults();

                        mAdapter = new RecyclerViewAdapterShows();
                        mMoviesRecyclerView.setAdapter(mAdapter);
                        mAdapter.setMovies(shows);


                        mAdapter.notifyDataSetChanged();
                        lblError.setText("");
                        if(shows.isEmpty())
                        {
                            lblError.setText("No matche found  \n \""+title+"\"");
                        }
                    }
                    else
                    {
                        lblError.setText("No matche found  \n \""+title+"\"");
                    }
                }

            }

            @Override
            public void onFailure(Call<Show> call, Throwable t) {
                lblError.setText(t.getCause().getMessage().toString());
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
        getMenuInflater().inflate(R.menu.shows, menu);
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
            //startActivity(new Intent(getApplicationContext(),Shows.class));
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
