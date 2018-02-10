package com.example.goran.mymoviedb;

import android.support.v7.app.AppCompatActivity;

import com.example.goran.mymoviedb.movies.util.ProgressDialog;

/**
 * Created by Goran on 7.2.2018..
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    private ProgressDialog progressDialog;

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog();
        }
        progressDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
