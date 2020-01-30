package com.murayyan.movietime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.murayyan.movietime.model.Movies;

import java.util.ArrayList;

import static com.murayyan.movietime.db.DatabaseContract.MovieColumns;



public class MovieHelper {
    private static final String DATABASE_TABLE = MovieColumns.MOVIE_TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movies> getAllMovies() {
        ArrayList<Movies> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                MovieColumns.ID + " ASC",
                null);
        cursor.moveToFirst();
        Movies movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movies();
                movie.setIds(cursor.getInt(cursor.getColumnIndexOrThrow(MovieColumns.ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.TITLE)));
                movie.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.OVERVIEW)));
                movie.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.RELEASE_DATE)));
                movie.setScore(cursor.getDouble(cursor.getColumnIndexOrThrow(MovieColumns.VOTE_AVERAGE)));
                movie.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(MovieColumns.POSTER_PATH_STRING)));
                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Movies movies) {
        ContentValues args = new ContentValues();
        args.put(MovieColumns.ID, movies.getIds());
        args.put(MovieColumns.TITLE, movies.getTitle());
        args.put(MovieColumns.OVERVIEW, movies.getDesc());
        args.put(MovieColumns.RELEASE_DATE, movies.getRelease());
        args.put(MovieColumns.VOTE_AVERAGE, movies.getScore());
        args.put(MovieColumns.POSTER_PATH_STRING, movies.getPoster());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(int id) {
        return database.delete(MovieColumns.MOVIE_TABLE_NAME, MovieColumns.ID + " = '" + id + "'", null);
    }
}
