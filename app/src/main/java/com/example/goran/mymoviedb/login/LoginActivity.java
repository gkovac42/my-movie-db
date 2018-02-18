package com.example.goran.mymoviedb.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.LinkMovementMethod;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goran.mymoviedb.BaseActivity;
import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.R;
import com.example.goran.mymoviedb.di.LoginActivityModule;
import com.example.goran.mymoviedb.movies.home.HomeActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.txt_username) EditText txtUsername;
    @BindView(R.id.txt_password) EditText txtPassword;
    @BindView(R.id.txt_reset_password) TextView txtResetPassword;
    @BindView(R.id.chk_stay_logged_in) AppCompatCheckBox chkStayLoggedIn;

    @Inject
    LoginContract.Presenter presenter;

    @OnClick(R.id.btn_login)
    public void onClickLogin() {
        presenter.onClickLogin(
                txtUsername.getText().toString(),
                txtPassword.getText().toString());
    }

    @OnClick(R.id.txt_guest)
    public void onClickGuest() {
        presenter.onClickGuest();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        (((BaseApplication) getApplication()).getAppComponent())
                .loginActivitySubcomponent(new LoginActivityModule(this))
                .inject(this);

        txtPassword.setOnEditorActionListener((textView, i, keyEvent) -> {

            if (i == EditorInfo.IME_ACTION_DONE) {

                presenter.onClickLogin(
                        txtUsername.getText().toString(),
                        txtPassword.getText().toString());
            }
            return false;
        });

        txtResetPassword.setMovementMethod(LinkMovementMethod.getInstance());

        presenter.checkForSavedUser();
    }


    @Override
    public boolean stayLoggedIn() {
        return chkStayLoggedIn.isChecked();
    }

    @Override
    public void navigateToMain() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void displayUsernameError() {
        Toast.makeText(this, "Username too short!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayPasswordError() {
        Toast.makeText(this, "Password too short!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayLoginError() {
        Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
    }
}
