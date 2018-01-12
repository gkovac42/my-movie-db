package com.example.goran.mymoviedb.di.scope;

/**
 * Created by Goran on 10.1.2018..
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FragmentScope {

}