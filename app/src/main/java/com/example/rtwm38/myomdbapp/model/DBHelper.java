package com.example.rtwm38.myomdbapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String MOVIE_TABLE_NAME = "movies";
    public static final String MOVIE_COLUMN_ID = "id";
    public static final String MOVIE_COLUMN_NAME = "name";
    public static final String MOVIE_COLUMN_URL = "url";
    public static final String MOVIE_COLUMN_RELEASE = "year";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table movies " +
                        "(id integer primary key, name text,url text,year text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movies");
        onCreate(db);
    }
    public boolean insertMovie (String name, String url, String year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("url", url);
        contentValues.put("year", year);
        db.insert("movies", null, contentValues);
        return true;
    }
    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movieList = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from movies", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String poster = res.getString(res.getColumnIndex(MOVIE_COLUMN_NAME));
            String url = res.getString(res.getColumnIndex(MOVIE_COLUMN_URL));
            String year = res.getString(res.getColumnIndex(MOVIE_COLUMN_RELEASE));
            //movieList.add(res.getString(res.getColumnIndex(MOVIE_COLUMN_NAME)));
            movieList.add(new Movie(poster, url, year));
            res.moveToNext();
        }
        return movieList;
    }
}
