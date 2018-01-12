package com.example.goran.mymoviedb.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.goran.mymoviedb.di.scope.ActivityScope;
import com.yakivmospan.scytale.Crypto;
import com.yakivmospan.scytale.Options;
import com.yakivmospan.scytale.Store;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 11.1.2018..
 */

@Module
public class KeystoreModule {

    @Provides
    @ActivityScope
    Store provideStore(Context context) {
        return new Store(context);
    }

    @Provides
    @ActivityScope
    Crypto provideCrypto() {
        return new Crypto(Options.TRANSFORMATION_SYMMETRIC);
    }

    @Provides
    @ActivityScope
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("moviesDb", Context.MODE_PRIVATE);
    }
}
