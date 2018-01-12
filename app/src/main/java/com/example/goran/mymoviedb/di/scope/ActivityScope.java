package com.example.goran.mymoviedb.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Goran on 10.1.2018..
 */

@Scope
@Retention(value= RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
