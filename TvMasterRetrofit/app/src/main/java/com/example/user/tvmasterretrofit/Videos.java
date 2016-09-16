package com.example.user.tvmasterretrofit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.user.tvmasterretrofit.MovieDetails.TrailerAPI;
import com.example.user.tvmasterretrofit.MovieDetails.Trailer;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Videos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Picasso picassoInstance;
    private VideoView mVideoView;
    private TextView lblError;
    private TextView lblTitle;
    private TextView lblReleaseDate;
    private TextView lblVoteCount;
    private TextView lblOverview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mVideoView =(VideoView) findViewById(R.id.mVideoView_trailer);
        lblTitle=(TextView) findViewById(R.id.lblTitle);
        lblReleaseDate=(TextView) findViewById(R.id.lblReleaseDate);
        lblVoteCount=(TextView) findViewById(R.id.lblVoteCount);
        lblOverview=(TextView) findViewById(R.id.lblOverView);
        lblError=(TextView) findViewById(R.id.lblError);



        Bundle movieExtras= getIntent().getExtras();

        int movieId=0;
        if(movieExtras!=null)
        {
            lblTitle.setText(movieExtras.getString("title"));
            lblOverview.setText(movieExtras.getString("overview"));
            lblReleaseDate.setText(movieExtras.getString("releasedate"));
            lblVoteCount.setText(movieExtras.getString("vote"));
            movieId=movieExtras.getInt("movieId");
        }
        LoadTrailer(movieId);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }













    public void LoadTrailer(int movieId)
    {
       TrailerAPI.Factory.GetInstance().getTrailers(movieId,movieId,"968cca12b1a8492036b1e1e05af57e3f").enqueue(new Callback<Trailer>() {
           @Override
           public void onResponse(Call<Trailer> call, Response<Trailer> response) {
               if(response.body()==null)
               {
                   lblError.setText("No Results/video");
               }
               else if(response.body().getResults().size()>0)
               {
                   String key=response.body().getResults().get(0).getKey();
                   Uri video = Uri.parse("http://abhiandroid.jobxfryqt.netdna-cdn.com/ui/wp-content/uploads/2016/04/videoviewtestingvideo.mp4");

                   MediaController controller = new MediaController(mVideoView.getContext());
                   //controller.setAnchorView(mVideoView);
                   //controller.setMediaPlayer(mVideoView);
                   controller.setAnchorView(mVideoView);
                   mVideoView.setMediaController(controller);
                   mVideoView.setVideoURI(video);
                   mVideoView.start();

                   Uri uri = Uri.parse("https://www.youtube.com/watch?v="+key);
                   Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                   startActivity(intent);
                   lblError.setText("");
               }
               else
               {
                   lblError.setText("Failed to load a video, Try again later...");
               }

           }

           @Override
           public void onFailure(Call<Trailer> call, Throwable t) {
               lblError.setText(t.getCause().getMessage().toString());
           }
       });
    }

    /*@Override
    public void onStop() {
        super.onStop();
        this.finish();

    }*/


























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
        getMenuInflater().inflate(R.menu.videos, menu);
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
