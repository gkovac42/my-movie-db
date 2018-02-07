package com.example.goran.mymoviedb;

import android.support.v4.app.Fragment;

import com.example.goran.mymoviedb.movies.util.ProgressDialog;

/**
 * Created by Goran on 7.2.2018..
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    private ProgressDialog progressDialog;

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog();
        progressDialog.show(getActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
