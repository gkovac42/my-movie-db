package com.example.goran.mymoviedb.movies.util;

import android.text.TextUtils;

import com.example.goran.mymoviedb.data.model.details.Genre;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Goran on 23.12.2017..
 */

public abstract class MovieUtils {



    public static String formatTitle(String title, String date) throws IndexOutOfBoundsException {
        try {
            return title + " (" + date.substring(0, 4) + ")";

            //some movies don't have a proper release date which causes exception
        } catch (IndexOutOfBoundsException e) {
            return title + " (n/a)";
        }
    }

    public static Long dateStringToLong(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
            return format.parse(date).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // formats movie genres into a single String, separated by ,
    public static String getGenres(List<Genre> genres) {

        ArrayList<String> formattedGenres = new ArrayList<>();

        for (Genre genre : genres) {
            formattedGenres.add(genre.getName());
        }

        return TextUtils.join(", ", formattedGenres);
    }

    // localized id to genre name converter to avoid unnecessary network calls
    public static String getGenreById(int id) {

        String genreName;

        switch (id) {
            case 28:
                genreName = "Action";
                break;
            case 12:
                genreName = "Adventure";
                break;
            case 16:
                genreName = "Animation";
                break;
            case 35:
                genreName = "Comedy";
                break;
            case 80:
                genreName = "Crime";
                break;
            case 99:
                genreName = "Documentary";
                break;
            case 18:
                genreName = "Drama";
                break;
            case 10751:
                genreName = "Family";
                break;
            case 14:
                genreName = "Fantasy";
                break;
            case 36:
                genreName = "History";
                break;
            case 27:
                genreName = "Horror";
                break;
            case 10402:
                genreName = "Music";
                break;
            case 9648:
                genreName = "Mystery";
                break;
            case 10749:
                genreName = "Romance";
                break;
            case 878:
                genreName = "SciFi";
                break;
            case 10770:
                genreName = "TV";
                break;
            case 53:
                genreName = "Thriller";
                break;
            case 10752:
                genreName = "War";
                break;
            case 37:
                genreName = "Western";
                break;
            default:
                genreName = "n/a";
        }
        return genreName;
    }
}
