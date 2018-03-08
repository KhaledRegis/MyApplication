package com.example.regismark6.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class details_page extends AppCompatActivity {
    ImageView imageView;
    TextView titleView;
    TextView plotView;
    TextView dateView;
    TextView ratingView;
    static TextView genreView;
    private movie favorite;
    private dbconnector dbconnect;
    private final AppCompatActivity activity = details_page.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();
        imageView = (ImageView) findViewById(R.id.poster_image);
        titleView = (TextView) findViewById(R.id.title);
        plotView = (TextView) findViewById(R.id.plot);
        dateView = (TextView) findViewById(R.id.release_date);
        genreView = (TextView) findViewById(R.id.genres);
        ratingView= (TextView) findViewById(R.id.rating);
        Intent UserClicked = getIntent();

        String posterPath = getIntent().getExtras().getString("poster");
        String movieTitle =getIntent().getExtras().getString("title");
        String plot =getIntent().getExtras().getString("plot");
        String releaseDate =getIntent().getExtras().getString("releaseDate");
        String genres =getIntent().getExtras().getString("genres");
        String rating =getIntent().getExtras().getString("rating");


        Glide.with(this).load(posterPath).placeholder(R.drawable.load).into(imageView);
        titleView.setText("Title: " + movieTitle);
        plotView.setText("plot: " + plot);
        dateView.setText("Release Date: " + releaseDate);
        genreView.setText("Genres: " + genres);
        ratingView.setText("User rating: " + rating);
        MaterialFavoriteButton favoriteButton = (MaterialFavoriteButton) findViewById(R.id.favorite);

            favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if(favorite){
                        SharedPreferences.Editor editor = getSharedPreferences("com.example.regismark6.myapplication.details_page", MODE_PRIVATE).edit();
                        editor.putBoolean("Favorite Added",true);
                        editor.commit();
                        saveFavorite();
                        Snackbar.make(buttonView, "added to favorite", Snackbar.LENGTH_SHORT).show();

                    }
                    else{
                        int movie_id = getIntent().getExtras().getInt("id");
                        dbconnect = new dbconnector(details_page.this);
                        dbconnect.deleteFavorite(movie_id);
                        SharedPreferences.Editor editor = getSharedPreferences("com.example.regismark6.myapplication.details_page", MODE_PRIVATE).edit();
                        editor.putBoolean("Favorite Removed", true);
                        editor.commit();
                        Snackbar.make(buttonView, "removed from Favorite",Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
    }



    public void saveFavorite(){
        dbconnect = new dbconnector(this);
        favorite = new movie(1,"","",1,"","",null);
        int movie_id = getIntent().getExtras().getInt("id");
        String title = getIntent().getExtras().getString("title");
        String poster = getIntent().getExtras().getString("poster");
        String plot = getIntent().getExtras().getString("plot");
        String release = getIntent().getExtras().getString("releaseDate");

        favorite.setId(movie_id);
        favorite.setTitle(title);
        favorite.setShortdesc(plot);
        favorite.setReleaseDate(release);
        favorite.setImagePath(poster);

        dbconnect.addFavorite(favorite);
    }
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public dbconnector getDbconnect() {
        return dbconnect;
    }
}
