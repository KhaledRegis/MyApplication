package com.example.regismark6.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RegisMark6 on 3/8/2018.
 */

//this class is responsible for all sql connections as well as performing queries.

public class dbconnector extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite.db";

    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "FAVORITE";

    SQLiteOpenHelper dbhandler;

    SQLiteDatabase db;

    public dbconnector(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void open(){
        Log.i(LOGTAG, "Database opened");
        db = dbhandler.getWritableDatabase();
    }
    public void close(){
        Log.i(LOGTAG, "Database closed");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + favoriteTable.favoriteEntry.TABLE_NAME + " (" +
                favoriteTable.favoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                favoriteTable.favoriteEntry.COLUMN_MOVIEID + " INTEGER, " +
                favoriteTable.favoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                favoriteTable.favoriteEntry.COLUMN_RELEASE + " TEXT NOT NULL, " +
                favoriteTable.favoriteEntry.COLUMN_USERRATING + " REAL NOT NULL, " +
                favoriteTable.favoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                favoriteTable.favoriteEntry.COLUMN_PLOT + " TEXT NOT NULL " +

                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + favoriteTable.favoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    //query database to populate attributes "INSERT INTO favorites VALUES(.....)"
    public void addFavorite(movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(favoriteTable.favoriteEntry.COLUMN_MOVIEID, movie.getId());
        values.put(favoriteTable.favoriteEntry.COLUMN_TITLE, movie.getTitle());
        values.put(favoriteTable.favoriteEntry.COLUMN_RELEASE, movie.getReleaseDate());
        values.put(favoriteTable.favoriteEntry.COLUMN_USERRATING, movie.getRating());
        values.put(favoriteTable.favoriteEntry.COLUMN_POSTER_PATH, movie.getImagePath());
        values.put(favoriteTable.favoriteEntry.COLUMN_PLOT, movie.getShortdesc());

        db.insert(favoriteTable.favoriteEntry.TABLE_NAME, null, values);
        db.close();

    }
    //delete record from database
    public void deleteFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(favoriteTable.favoriteEntry.TABLE_NAME, favoriteTable.favoriteEntry.COLUMN_MOVIEID+ "=" + id,null);

    }
    public List<movie> getAllFavorite(){

        String[] columns = {favoriteTable.favoriteEntry._ID,
        favoriteTable.favoriteEntry.COLUMN_MOVIEID,
        favoriteTable.favoriteEntry.COLUMN_TITLE,
        favoriteTable.favoriteEntry.COLUMN_RELEASE,
        favoriteTable.favoriteEntry.COLUMN_USERRATING,
        favoriteTable.favoriteEntry.COLUMN_POSTER_PATH,
        favoriteTable.favoriteEntry.COLUMN_PLOT


        };

        String sortOrder = favoriteTable.favoriteEntry._ID + " ASC";
        List<movie> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ favoriteTable.favoriteEntry.TABLE_NAME,null);


        if(cursor.moveToFirst()){
            do{
                movie movie = new movie(1,"","",1,"","",null);
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(favoriteTable.favoriteEntry.COLUMN_MOVIEID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(favoriteTable.favoriteEntry.COLUMN_TITLE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(favoriteTable.favoriteEntry.COLUMN_RELEASE)));
                movie.setRating(Integer.parseInt(cursor.getString(cursor.getColumnIndex(favoriteTable.favoriteEntry.COLUMN_USERRATING))));
                movie.setImagePath(cursor.getString(cursor.getColumnIndex(favoriteTable.favoriteEntry.COLUMN_POSTER_PATH)));
                movie.setShortdesc(cursor.getString(cursor.getColumnIndex(favoriteTable.favoriteEntry.COLUMN_PLOT)));

                favoriteList.add(movie);
            }while(cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return favoriteList;

    }


}
