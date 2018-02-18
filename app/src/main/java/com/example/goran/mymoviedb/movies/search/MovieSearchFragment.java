package com.example.goran.mymoviedb.movies.search;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.BaseFragment;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.data.model.keywords.Keyword;
import com.example.goran.mymoviedb.data.model.list.Movie;
import com.example.goran.mymoviedb.di.MovieSearchFragmentModule;
import com.example.goran.mymoviedb.movies.adapters.MovieAdapterListener;
import com.example.goran.mymoviedb.movies.adapters.SimpleMovieAdapter;
import com.example.goran.mymoviedb.movies.details.MovieDetailsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Goran on 4.1.2018..
 */

public class MovieSearchFragment extends BaseFragment implements MovieSearchContract.View, TextWatcher {

    @BindView(R.id.txt_search_query) AutoCompleteTextView txtSearchQuery;
    @BindView(R.id.rbtn_search_title) RadioButton rbtnTitle;
    @BindView(R.id.rbtn_search_keyword) RadioButton rbtnKeyword;
    @BindView(R.id.progress_search) ProgressBar progressBar;
    @BindView(R.id.recycler_search_results) RecyclerView recyclerView;

    @Inject
    MovieSearchContract.Presenter presenter;

    private ArrayAdapter<Keyword> keywordAdapter;
    private SimpleMovieAdapter resultAdapter;

    private void initKeywordAdapter() {
        keywordAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1);
    }

    private void initResultAdapter() {
        resultAdapter = new SimpleMovieAdapter();
        resultAdapter.setListener(new MovieAdapterListener() {

            @Override
            public void onClick(int movieId) {
                presenter.onClickResult(movieId);
            }

            @Override
            public void onBottomReached() {
                presenter.onBottomReached(rbtnTitle.isChecked());
            }
        });

        recyclerView.setAdapter(resultAdapter);
    }

    @OnClick(R.id.btn_search)
    public void onClickSearch() {
        presenter.onClickSearch(txtSearchQuery.getText().toString(),
                rbtnTitle.isChecked());
    }

    @OnClick(R.id.rbtn_search_keyword)
    public void onSelectKeyword() {
        presenter.onSelectByKeyword();
    }

    @OnClick(R.id.rbtn_search_title)
    public void onSelectTitle() {
        presenter.onSelectByTitle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        (((BaseApplication) getActivity().getApplication()).getAppComponent())
                .movieSearchFragmentSubcomponent(new MovieSearchFragmentModule(this))
                .inject(this);

        return inflater.inflate(R.layout.fragment_movie_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        rbtnTitle.setChecked(true);

        initResultAdapter();

        initKeywordAdapter();

        txtSearchQuery.setOnEditorActionListener((textView, i, keyEvent) -> {

            if (i == EditorInfo.IME_ACTION_DONE) {

                presenter.onClickSearch(
                        txtSearchQuery.getText().toString(),
                        rbtnTitle.isChecked());
            }

            return false;
        });
    }

    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideKeyboard() {

        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void displaySearchResults(List<Movie> movieList) {
        resultAdapter.setDataSource(movieList);
        resultAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayKeywords(List<Keyword> keywordList) {
        keywordAdapter.clear();
        keywordAdapter.addAll(keywordList);
    }

    @Override
    public void navigateToResult(int movieId) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra("movie_id", movieId);
        startActivity(intent);
    }

    @Override
    public void initTextWatcher() {
        txtSearchQuery.setAdapter(keywordAdapter);
        txtSearchQuery.addTextChangedListener(this);
    }

    @Override
    public void removeTextWatcher() {
        keywordAdapter.clear();
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

}
