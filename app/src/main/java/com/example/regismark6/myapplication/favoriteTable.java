package com.example.regismark6.myapplication;

import android.provider.BaseColumns;

/**
 * Created by RegisMark6 on 3/8/2018.
 */

public class favoriteTable {

    //favorite table attributes
    public static final class favoriteEntry implements BaseColumns

    {
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE = "release";
        public static final String COLUMN_USERRATING = "user_rating";
        public static final String COLUMN_POSTER_PATH = "poster";
        public static final String COLUMN_PLOT = "plot";
    }
}
