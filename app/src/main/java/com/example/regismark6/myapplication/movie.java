package com.example.regismark6.myapplication;

import java.util.List;

import info.movito.themoviedbapi.model.Genre;

/**
 * Created by RegisMark6 on 3/6/2018.
 */

public class movie {
    //movie class that will hold the movie attributes
    private int id;
    private String title, shortdesc;
    private double rating;
    private String imagePath;
    private String releaseDate;
    private List<Genre> genres;

    public movie(int id, String title, String shortdesc, double rating, String imagePath, String releaseDate,List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.rating = rating;
        this.imagePath = imagePath;
        this.releaseDate = releaseDate;
        this.genres = genres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public double getRating() {
        return rating;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}

