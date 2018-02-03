package com.example.goran.mymoviedb.data.interactors;

/**
 * Created by Goran on 23.1.2018..
 */

public interface CreditsInteractor {

    void setListener(CreditsInteractorImpl.CreditsListener listener);

    void getCredits(int movieId);

}
