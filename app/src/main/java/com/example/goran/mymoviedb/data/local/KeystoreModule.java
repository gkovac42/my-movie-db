package com.example.goran.mymoviedb.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.yakivmospan.scytale.Crypto;
import com.yakivmospan.scytale.Options;
import com.yakivmospan.scytale.Store;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 11.1.2018..
 */

@Module
public class KeystoreModule {

    @Provides
    @Singleton
    Store provideStore(Context context) {
        return new Store(context);
    }

    @Provides
    @Singleton
    Crypto provideCrypto() {
        return new Crypto(Options.TRANSFORMATION_SYMMETRIC);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }
}
