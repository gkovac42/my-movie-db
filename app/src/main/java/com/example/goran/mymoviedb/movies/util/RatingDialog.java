package com.example.goran.mymoviedb.movies.util;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.example.goran.mymoviedb.R;

/**
 * Created by Goran on 25.1.2018..
 */

public class RatingDialog extends DialogFragment {

    private RatingBar ratingBar;
    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_rating, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ratingBar = view.findViewById(R.id.ratingBar);

        view.findViewById(R.id.btn_dialog_rate).setOnClickListener(onClickListener);
        view.findViewById(R.id.btn_dialog_clear).setOnClickListener(onClickListener);

    }

    public double getRating() {
        return ratingBar.getRating();
    }
}
