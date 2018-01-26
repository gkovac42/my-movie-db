package com.example.goran.mymoviedb.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Goran on 26.1.2018..
 */

@Singleton
public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "movie_db";
    private static final String FAVORITE_TABLE = "favorite";
    private static final String RATED_TABLE = "rated";
    private static final String USER_COLUMN = "user";
    private static final String MOVIE_ID_COLUMN = "id";
    private static int DB_VERSION = 1;

    @Inject
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    public void insertIntoFavorite(String username, int movieId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_COLUMN, username);
        values.put(MOVIE_ID_COLUMN, movieId);

        db.insert(FAVORITE_TABLE, null, values);
        db.close();
    }

    public void insertIntoRated(String username, int movieId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_COLUMN, username);
        values.put(MOVIE_ID_COLUMN, movieId);

        db.insert(RATED_TABLE, null, values);
        db.close();
    }

    public ArrayList<Integer> getFavorites(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> movieIds = new ArrayList<>();


        Cursor cursor = db.query(
                FAVORITE_TABLE,
                new String[]{MOVIE_ID_COLUMN},
                USER_COLUMN + "=?", new String[]{username}, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(0);

            movieIds.add(id);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return movieIds;
    }

    public ArrayList<Integer> getRated(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Integer> movieIds = new ArrayList<>();


        Cursor cursor = db.query(
                RATED_TABLE,
                new String[]{MOVIE_ID_COLUMN},
                USER_COLUMN + "=?", new String[]{username}, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(0);

            movieIds.add(id);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return movieIds;
    }

    public void deleteFavorite(String username, int movieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FAVORITE_TABLE, USER_COLUMN + "=? AND " + MOVIE_ID_COLUMN + "=?",
                new String[]{username, String.valueOf(movieId)});
        db.close();
    }

    public void deleteRated(String username, int movieId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RATED_TABLE, USER_COLUMN + "=? AND " + MOVIE_ID_COLUMN + "=?",
                new String[]{username, String.valueOf(movieId)});
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FAVORITE_TABLE
                + "(id INTEGER PRIMARY KEY, "
                + USER_COLUMN + " TEXT)");

        db.execSQL("CREATE TABLE " + RATED_TABLE
                + "(id INTEGER PRIMARY KEY, "
                + USER_COLUMN + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
