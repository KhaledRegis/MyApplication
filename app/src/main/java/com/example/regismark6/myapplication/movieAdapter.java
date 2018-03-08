package com.example.regismark6.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import info.movito.themoviedbapi.model.Genre;

/**
 * Created by RegisMark6 on 2/6/2018.
 */
// recyclerView.adapter
// recyclerview.viewholder
    // this adapter is responsible for populating the recycleview card items list
public class movieAdapter extends RecyclerView.Adapter<movieAdapter.movieViewHolder>
{
    private Context mCtx;
    private List<movie> movieList;

    public movieAdapter(Context mCtx, List<movie> movieList) {
        this.mCtx = mCtx;
        this.movieList = movieList;
    }

    @Override
    public movieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.movie_card,  null);
        movieViewHolder holder = new movieViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(movieViewHolder holder, int position) {
        movie movieOb = movieList.get(position);
        holder.textViewTitle.setText(movieOb.getTitle());
        holder.textViewRating.setText(String.valueOf(movieOb.getRating()));
        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(movieOb.getImage()));
        Glide.with(mCtx).load(movieList.get(position).getImagePath()).placeholder(R.drawable.load).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class movieViewHolder extends RecyclerView.ViewHolder{
        //imageviews and textViews from movie_card.xml
        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewRating;
        public movieViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.title);
            textViewRating = itemView.findViewById(R.id.rating);
            //listener for picture clicked, some data will be passed on to the details page.
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        movie clickedOnPicture = movieList.get(pos);
                        Intent intent = new Intent(mCtx, details_page.class);
                        intent.putExtra("poster",movieList.get(pos).getImagePath());
                        intent.putExtra("id",movieList.get(pos).getId());
                        intent.putExtra("title",movieList.get(pos).getTitle());
                        intent.putExtra("plot",movieList.get(pos).getShortdesc());
                        intent.putExtra("releaseDate",movieList.get(pos).getReleaseDate());
                        intent.putExtra("rating",movieList.get(pos).getRating()+"");

                         /*
                        String genres="";
                        int genresSize =moviesList.get(i).getGenres().size();
                        for(int k=0;k<genresSize;k++ ) {
                            genres = genres + ", " + moviesList.get(i).getGenres().get(k);
                            }
                            */
                         if(movieList.get(pos).getGenres()!= null && movieList.get(pos).getGenres().size()>0) {
                             intent.putExtra("genres", storeGenresToString(movieList.get(pos).getGenres()));
                         }
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mCtx.startActivity(intent);
                         String id =movieList.get(pos).getId()+"";

                        MovieTask mt = new MovieTask(mCtx);
                        mt.execute("update",id);
                    }
                }
            });
        }

        // this returns the genres
        String storeGenresToString(List<Genre> genres){

                        String genresSTR="";
                        //int genresSize =genres.size();
                        for(int k=0;k<genres.size();k++ ) {
                            genresSTR = genresSTR + ", " + genres.get(k).getName();
                            }

             return genresSTR;
        }
    }
}
