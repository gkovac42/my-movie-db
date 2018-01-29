package com.example.goran.mymoviedb.notifications;


import android.content.Context;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;


/**
 * Created by Goran on 27.1.2018..
 */

public class NotificationService extends JobService {

    private static final String TAG = NotificationService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        /*Observable<Integer> favoriteMovies = Observable.fromIterable(dbHelper.getFavorites("gkovac42"));
        favoriteMovies
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(Integer integer) {
                                   // TODO - check if movie release date matches current date, show notification
                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {
                                   jobFinished(jobParameters, false);
                               }
                           }
                );*/
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public static Job createJob(FirebaseJobDispatcher dispatcher) {

        return dispatcher.newJobBuilder()
                .setLifetime(Lifetime.FOREVER)
                .setService(NotificationService.class)
                .setTag(NotificationService.TAG)
                .setReplaceCurrent(false)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(1, 60 * 60 * 24)) // 1 day
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .build();
    }

    public static void scheduleJob(Context context) {

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        Job job = createJob(dispatcher);

        dispatcher.mustSchedule(job);
    }
}
