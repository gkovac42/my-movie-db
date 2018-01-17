package com.example.goran.mymoviedb.di;

import com.example.goran.mymoviedb.BaseApplication;
import com.example.goran.mymoviedb.data.remote.ApiHelper;
import com.example.goran.mymoviedb.data.remote.RemoteModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Goran on 28.12.2017..
 */
@Singleton
@Component(modules = {AppModule.class, RemoteModule.class})
public interface AppComponent {

    void inject(BaseApplication baseApplication);

    ApiHelper apiHelper();

    LoginActivitySubcomponent loginActivitySubcomponent(LoginActivityModule loginActivityModule);

    HomeActivitySubcomponent homeActivitySubcomponent(HomeActivityModule homeActivityModule);

    MovieListFragmentSubcomponent movieListFragmentSubcomponent(MovieListFragmentModule movieListFragmentModule);

    MovieDetailsFragmentSubcomponent movieDetailsFragmentSubcomponent(MovieDetailsFragmentModule movieDetailsFragmentModule);

    MovieCreditsFragmentSubcomponent movieCreditsFragmentSubcomponent(MovieCreditsFragmentModule movieCreditsFragmentModule);

    MovieSearchFragmentSubcomponent movieSearchFragmentSubcomponent(MovieSearchFragmentModule movieSearchFragmentModule);
}
