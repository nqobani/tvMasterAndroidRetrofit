package com.example.user.tvmasterretrofit.TV_API;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.tvmasterretrofit.R;
import com.example.user.tvmasterretrofit.Videos;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 14/09/2016.
 */
public class RecyclerViewAdapterShows extends RecyclerView.Adapter<RecyclerViewAdapterShows.ViewHolder> {
    List<Result> mMovies;

    private  static Intent i;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case

        public int movieId;
        public ImageView imgPoster;
        public TextView lblTitle;
        public TextView lblReleaseDate;
        public TextView lblOverview;
        public TextView lblVote;
        public ViewHolder(View v) {
            super(v);
            imgPoster =(ImageView) v.findViewById(R.id.imgPoster);
            lblTitle=(TextView)v.findViewById(R.id.lblTitle);
            lblOverview=(TextView)v.findViewById(R.id.lblOverView);
            lblReleaseDate=(TextView)v.findViewById(R.id.lblReleaseDate);
            lblVote=(TextView)v.findViewById(R.id.lblVoteCount);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),lblTitle.getText().toString(),Toast.LENGTH_LONG).show();

            i= new Intent(v.getContext(),Videos.class);

            i.putExtra("title",lblTitle.getText().toString());
            i.putExtra("overview",lblOverview.getText().toString());
            i.putExtra("releasedate",lblReleaseDate.getText().toString());
            i.putExtra("vote", lblVote.getText().toString());
            if(movieId>0)
            {
                //movieId=movieId-1;
            }
            i.putExtra("movieId", movieId);
            v.getContext().startActivity(i);
        }


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    public void setMovies(List<Result> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Context context =holder.imgPoster.getContext();
        if(mMovies.get(position).getBackdropPath()!=null)
        {
            Picasso.with(holder.imgPoster.getContext()).load(context.getString(R.string.app_api_endpoint)+mMovies.get(position).getBackdropPath()).into(holder.imgPoster);
        }
        holder.lblOverview.setText(mMovies.get(position).getOverview());
        holder.lblTitle.setText(mMovies.get(position).getName());
        holder.lblReleaseDate.setText((mMovies.get(position).getFirstAirDate()));
        holder.lblVote.setText("Vote Count: "+mMovies.get(position).getVoteCount());
        holder.movieId=mMovies.get(position).getId();



        holder.imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.onClick(v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
