package com.example.goran.mymoviedb.data.model.person;

import com.example.goran.mymoviedb.data.model.list.Movie;

import java.util.List;

/**
 * Created by Goran on 10.2.2018..
 */

public class PersonData {

    private Person person;
    private List<Movie> relatedMovies;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Movie> getRelatedMovies() {
        return relatedMovies;
    }

    public void setRelatedMovies(List<Movie> relatedMovies) {
        this.relatedMovies = relatedMovies;
    }
}
