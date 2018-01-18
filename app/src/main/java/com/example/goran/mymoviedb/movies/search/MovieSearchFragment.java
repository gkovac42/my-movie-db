package com.example.goran.mymoviedb.movies.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.Movie;
import com.example.goran.mymoviedb.data.model.keywords.Keyword;
import com.example.goran.mymoviedb.di.MovieSearchFragmentModule;
import com.example.goran.mymoviedb.movies.adapters.SimpleMovieAdapter;
import com.example.goran.mymoviedb.movies.details.MovieDetailsActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Goran on 4.1.2018..
 */

public class MovieSearchFragment extends Fragment implements MovieSearchContract.View, TextWatcher {

    @Inject
    MovieSearchContract.Presenter presenter;

    private SimpleMovieAdapter resultAdapter;
    private ArrayAdapter<Keyword> keywordAdapter;
    private List<Movie> movieList;

    @BindView(R.id.txt_search_query)
    AutoCompleteTextView txtSearchQuery;
    @BindView(R.id.rbtn_search_title)
    RadioButton rbtnTitle;
    @BindView(R.id.rbtn_search_keyword)
    RadioButton rbtnKeyword;
    @BindView(R.id.progress_search)
    ProgressBar progressBar;
    @BindView(R.id.recycler_search_results)
    RecyclerView recyclerView;

    @OnClick(R.id.btn_search)
    public void onClickSearch() {

        Boolean searchByTitle = rbtnTitle.isChecked();
        String query = txtSearchQuery.getText().toString();
        presenter.onClickSearch(query, searchByTitle);
    }

    @OnClick(R.id.rbtn_search_keyword)
    public void onSelectKeyword() {
        presenter.onSelectKeyword();
    }

    @OnClick(R.id.rbtn_search_title)
    public void onSelectTitle() {
        presenter.onSelectTitle();
    }

    public ArrayAdapter<Keyword> getKeywordAdapter() {
        return keywordAdapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Fresco.initialize(getActivity());

        (((BaseApplication) getActivity().getApplication()).getAppComponent())
                .movieSearchFragmentSubcomponent(new MovieSearchFragmentModule(this))
                .inject(this);

        return inflater.inflate(R.layout.fragment_movie_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        hideProgressBar();

        rbtnTitle.setChecked(true);

        resultAdapter = new SimpleMovieAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(resultAdapter);
        resultAdapter.setListener(position -> presenter.onClickResult(position));
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displaySearchResults(List<Movie> movieList) {
        this.movieList = movieList;
        resultAdapter.setDataSource(movieList);
        resultAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayKeywords(List<Keyword> keywordList) {
        keywordAdapter.clear();
        keywordAdapter.addAll(keywordList);
    }

    @Override
    public void navigateToResult(int position) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movieList.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void initTextWatcher() {
        keywordAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.select_dialog_item);
        txtSearchQuery.setAdapter(keywordAdapter);
        txtSearchQuery.addTextChangedListener(this);
    }

    @Override
    public void removeTextWatcher() {
        txtSearchQuery.setAdapter(null);
        txtSearchQuery.removeTextChangedListener(this);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        presenter.loadKeywords(editable.toString());
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
