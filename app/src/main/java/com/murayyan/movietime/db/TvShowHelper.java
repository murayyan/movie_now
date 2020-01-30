package com.murayyan.movietime.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.murayyan.movietime.model.TvShow;

import java.util.ArrayList;

import static com.murayyan.movietime.db.DatabaseContract.TvShowColumns;

public class TvShowHelper {
    private static final String DATABASE_TABLE = TvShowColumns.TV_SHOW_TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static TvShowHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvShowHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TvShowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowHelper(context);
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

    public ArrayList<TvShow> getAllTvShows() {
        ArrayList<TvShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                TvShowColumns.ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShow tvshow;
        if (cursor.getCount() > 0) {
            do {
                tvshow = new TvShow();
                tvshow.setIds(cursor.getInt(cursor.getColumnIndexOrThrow(TvShowColumns.ID)));
                tvshow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TvShowColumns.NAME)));
                tvshow.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(TvShowColumns.OVERVIEW)));
                tvshow.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(TvShowColumns.FIRST_AIR_DATE)));
                tvshow.setScore(cursor.getDouble(cursor.getColumnIndexOrThrow(TvShowColumns.VOTE_AVERAGE)));
                tvshow.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(TvShowColumns.POSTER_PATH_STRING)));

                arrayList.add(tvshow);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTvShow(TvShow tvshow) {
        ContentValues args = new ContentValues();
        args.put(TvShowColumns.ID, tvshow.getIds());
        args.put(TvShowColumns.NAME, tvshow.getTitle());
        args.put(TvShowColumns.OVERVIEW, tvshow.getDesc());
        args.put(TvShowColumns.FIRST_AIR_DATE, tvshow.getRelease());
        args.put(TvShowColumns.VOTE_AVERAGE, tvshow.getScore());
        args.put(TvShowColumns.POSTER_PATH_STRING, tvshow.getPoster());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTvShow(int id) {
        return database.delete(TvShowColumns.TV_SHOW_TABLE_NAME, TvShowColumns.ID + " = '" + id + "'", null);
    }
}
