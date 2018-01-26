package com.example.goran.mymoviedb.movies.adapters;

import com.example.goran.mymoviedb.data.model.list.Movie;

import java.util.List;

/**
 * Created by Goran on 25.1.2018..
 */

public interface BaseMovieAdapter {

    void setListener(MovieAdapterListener listener);

    void setDataSource(List<Movie> movies);

}
