package com.example.goran.mymoviedb.data.interactors;

import com.example.goran.mymoviedb.data.model.details.Credits;
import com.example.goran.mymoviedb.data.model.details.Crew;

/**
 * Created by Goran on 23.1.2018..
 */

public interface CreditsInteractor {

    void getCredits(int movieId, CreditsInteractorImpl.CreditsListener listener);

    Crew getDirector(Credits credits);

    Crew getWriter(Credits credits);
}
