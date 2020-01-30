package com.murayyan.movietime.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.murayyan.movietime.db.DatabaseContract.MovieColumns;
import static com.murayyan.movietime.db.DatabaseContract.TvShowColumns;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbmovie";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s" +
                    " (%s TEXT NULL, %s TEXT NULL, %s TEXT NULL, %s TEXT NULL, %s TEXT NULL, %s TEXT NULL)",
            MovieColumns.MOVIE_TABLE_NAME,
            MovieColumns.ID,
            MovieColumns.TITLE,
            MovieColumns.OVERVIEW,
            MovieColumns.RELEASE_DATE,
            MovieColumns.VOTE_AVERAGE,
            MovieColumns.POSTER_PATH_STRING
    );

    private static final String SQL_CREATE_TABLE_TV_SHOW = String.format("CREATE TABLE %s" +
                    " (%s TEXT NULL, %s TEXT NULL, %s TEXT NULL, %s TEXT NULL, %s TEXT NULL, %s TEXT NULL)",
            TvShowColumns.TV_SHOW_TABLE_NAME,
            TvShowColumns.ID,
            TvShowColumns.NAME,
            TvShowColumns.OVERVIEW,
            TvShowColumns.FIRST_AIR_DATE,
            TvShowColumns.VOTE_AVERAGE,
            TvShowColumns.POSTER_PATH_STRING
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TV_SHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieColumns.MOVIE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TvShowColumns.TV_SHOW_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
