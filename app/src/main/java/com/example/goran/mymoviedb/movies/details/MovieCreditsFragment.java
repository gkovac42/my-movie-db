package com.example.goran.mymoviedb.movies.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.details.Cast;
import com.example.goran.mymoviedb.data.model.details.Crew;
import com.example.goran.mymoviedb.di.MovieCreditsFragmentModule;
import com.example.goran.mymoviedb.movies.adapters.CastAdapter;
import com.example.goran.mymoviedb.movies.adapters.CrewAdapter;
import com.example.goran.mymoviedb.person.PersonActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Goran on 16.1.2018..
 */

public class MovieCreditsFragment extends Fragment implements MovieCreditsContract.View {

    @Inject
    MovieCreditsContract.Presenter presenter;

    private CastAdapter castAdapter;
    private CrewAdapter crewAdapter;

    @BindView(R.id.recycler_credits_crew) RecyclerView recyclerViewCrew;
    @BindView(R.id.recycler_credits_cast) RecyclerView recyclerViewCast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        (((BaseApplication) getActivity().getApplication()).getAppComponent())
                .movieCreditsFragmentSubcomponent(new MovieCreditsFragmentModule(this))
                .inject(this);

        return inflater.inflate(R.layout.fragment_movie_credits, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        crewAdapter = new CrewAdapter();
        crewAdapter.setListener(personId -> presenter.onClickPerson(personId));

        castAdapter = new CastAdapter();
        castAdapter.setListener(personId -> presenter.onClickPerson(personId));


        recyclerViewCrew.setAdapter(crewAdapter);
        recyclerViewCrew.setNestedScrollingEnabled(false);
        recyclerViewCast.setAdapter(castAdapter);
        recyclerViewCast.setNestedScrollingEnabled(false);

        presenter.initPresenter(getActivity().getIntent().getIntExtra("movie_id", 0));
        presenter.loadCredits();
    }


    @Override
    public void displayCrew(List<Crew> crew) {
        crewAdapter.setDataSource(crew);
        crewAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayCast(List<Cast> cast) {
        castAdapter.setDataSource(cast);
        castAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToPerson(int personId) {
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        intent.putExtra("person_id", personId);
        startActivity(intent);
    }
}
