package com.example.goran.mymoviedb.movies.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.goran.mymoviedb.R;

/**
 * Created by Goran on 4.1.2018..
 */

public class MovieSearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        EditText searchView = getView().findViewById(R.id.search_movie);
        ImageButton btnSearch = getView().findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(listener -> {
            Intent intent = new Intent(getActivity(), MovieSearchResultActivity.class);
            intent.putExtra("title_query", searchView.getText().toString());
            startActivity(intent);
        });

    }
}
