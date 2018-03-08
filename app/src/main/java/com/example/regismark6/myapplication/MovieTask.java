package com.example.regismark6.myapplication;

/**
 * Created by RegisMark6 on 3/6/2018.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by RegisMark6 on 3/4/2018.
 */

public class MovieTask extends AsyncTask<String, String, Boolean> {
    int size;
    static String param1;
    private static Context context;
    String BaseURL = "http://image.tmdb.org/t/p/w500/";
    String backDropUrl = "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    List<Genre> genre = null;
    List<MovieDb> moviesList;

    private static String input;
    public MovieTask(Context context)
    {
        this.context=context;

    }
    public MovieTask(Context context,String input)
    {
        this.context=context;
        this.input=input;
    }
    protected Boolean doInBackground(String... params) {
        // these are background processes that will do the fallowing commands: SEARCH, GET_UPCOMING_ GET_TOP_RATED, Update, favorite
        MainActivity.movieList.clear();


        MovieDb movie=null;

        if(params[0].equals("Search")) {


            moviesList = new TmdbApi("0f52beaa9f9673b829eb1f6aa995dfc8").getSearch().searchMovie(params[1],null,"en",true,1).getResults();
            size = moviesList.size();
            for(int i=0;i<20;i++){
                if(i<size) {
                    backDropUrl = moviesList.get(i).getBackdropPath();
                    String posterURL = BaseURL + backDropUrl;
                    double rating = round(moviesList.get(i).getVoteAverage(), 1);
                    String desc = moviesList.get(i).getOverview();
                    String releaseDate = moviesList.get(i).getReleaseDate();
                    List<Genre> genre =null;
                    //if i add the bellow code, the program will be very slow.
                    /*
                    int id = moviesList.get(i).getId();
                    String lang = moviesList.get(i).getOriginalLanguage();
                    movie = new TmdbApi("0f52beaa9f9673b829eb1f6aa995dfc8").getMovies().getMovie(id,lang);
                    if(movie.getGenres()!=null){
                        genre =movie.getGenres();
                    }
                    */

                    MainActivity.movieList.add(new movie(moviesList.get(i).getId(), moviesList.get(i).getTitle(), desc, rating, posterURL,releaseDate,genre));
                }

            }


            return true;
        }
        else if(params[0].equals("topRated")) {
            int id;
            String lang;
            TmdbMovies movies = new TmdbApi("0f52beaa9f9673b829eb1f6aa995dfc8").getMovies();
            moviesList = movies.getTopRatedMovies("en",1).getResults();
            size = moviesList.size();
            for(int i=0;i<20;i++){
                if(i<size) {
                    backDropUrl = moviesList.get(i).getBackdropPath();
                    String posterURL = BaseURL + backDropUrl;
                    double rating = round(moviesList.get(i).getVoteAverage(), 1);
                    String desc = moviesList.get(i).getOverview();
                    String releaseDate = moviesList.get(i).getReleaseDate();


                    //if i add the bellow code, the program will be very slow. because this will store the genres of all movies in the resulting query
                    /*
                    id = moviesList.get(i).getId();
                    lang = moviesList.get(i).getOriginalLanguage();
                    movie = new TmdbApi("0f52beaa9f9673b829eb1f6aa995dfc8").getMovies().getMovie(id, lang);
                    if (movie.getGenres() != null) {
                        genre = movie.getGenres();
                    }
                    */

                    MainActivity.movieList.add(new movie(moviesList.get(i).getId(), moviesList.get(i).getTitle(), desc, rating, posterURL, releaseDate, genre));
                }

            }


            return true;
        }
        else if(params[0].equals("upcoming")) {
            int id;
            String lang;
            TmdbMovies movies = new TmdbApi("0f52beaa9f9673b829eb1f6aa995dfc8").getMovies();
            moviesList = movies.getUpcoming("en",1,"us").getResults();
            size = moviesList.size();
            for(int i=0;i<20;i++){
                if(i<size) {
                    backDropUrl = moviesList.get(i).getBackdropPath();
                    String posterURL = BaseURL + backDropUrl;
                    double rating = round(moviesList.get(i).getVoteAverage(), 1);
                    String desc = moviesList.get(i).getOverview();
                    String releaseDate = moviesList.get(i).getReleaseDate();


                    //if i add the bellow code, the program will be very slow.
                    /*
                    id = moviesList.get(i).getId();
                    lang = moviesList.get(i).getOriginalLanguage();
                    movie = new TmdbApi("0f52beaa9f9673b829eb1f6aa995dfc8").getMovies().getMovie(id, lang);
                    if (movie.getGenres() != null) {
                        genre = movie.getGenres();
                    }
                    */

                    MainActivity.movieList.add(new movie(moviesList.get(i).getId(), moviesList.get(i).getTitle(), desc, rating, posterURL, releaseDate, genre));
                }

            }


            return true;
        }
        else if(params[0].equals("update")) {
            //this was a solution that would reduce the number of queries to moviedb api to enhance the performence, unfortunatly, it only works on emulator.
            /*
            int id = Integer.parseInt(params[1]);
            movie = new TmdbApi("0f52beaa9f9673b829eb1f6aa995dfc8").getMovies().getMovie(id, "en");
            if (movie.getGenres() != null) {
                genre = movie.getGenres();
                details_page.genreView.setText("Genres: " +storeGenresToString(genre));
            }
            return true;
            */
        }
        else if(params[0].equals("favorites")){

            dbconnector dbconnect=new dbconnector(context);
            List<movie> moviesl =dbconnect.getAllFavorite();

            for(int i=0;i<moviesl.size();i++){
                MainActivity.movieList.add(moviesl.get(i));
            }


            return true;

        }
        return false;
    }
    String storeGenresToString(List<Genre> genres){

        String genresSTR="";
        //int genresSize =genres.size();
        for(int k=0;k<genres.size();k++ ) {
            genresSTR = genresSTR + ", " + genres.get(k).getName();
        }

        return genresSTR;
    }

    private double round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    protected void onPostExecute(Boolean status) {
        MainActivity.adapter= new movieAdapter(context,MainActivity.movieList);
        MainActivity.recyclerView.setAdapter(MainActivity.adapter);




    }
    /*
            Toast toast2 = Toast.makeText(context, moviesl.size()+"",Toast.LENGTH_LONG);
            toast2.show();
     */

}