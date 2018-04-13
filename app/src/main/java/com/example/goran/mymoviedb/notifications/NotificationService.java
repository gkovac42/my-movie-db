package com.example.goran.mymoviedb.notifications;


import android.content.Context;
import android.os.Bundle;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.Calendar;


/**
 * Created by Goran on 27.1.2018..
 */

public class NotificationService extends JobService {

    public static final String EXTRA_MOVIE_TITLE = "movie_title";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        String title = jobParameters.getExtras().getString(EXTRA_MOVIE_TITLE);

        NotificationUtils.showNotification(this, title);

        jobFinished(jobParameters, false);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


    public static void cancelJob(Context context, String title) {

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        dispatcher.cancel(title);
    }

    public static void scheduleJob(String title, Long releaseDate, Context context) {

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        Job job = createJob(dispatcher, title, releaseDate);

        dispatcher.mustSchedule(job);
    }

    private static Job createJob(FirebaseJobDispatcher dispatcher, String title, long releaseDate) {

        Bundle extras = new Bundle();
        extras.putString(EXTRA_MOVIE_TITLE, title);

        long currentDate = Calendar.getInstance().getTimeInMillis();

        int triggerDelay = (int) ((releaseDate - currentDate) / 1000); // in seconds

        return dispatcher.newJobBuilder()
                .setExtras(extras)
                .setLifetime(Lifetime.FOREVER)
                .setService(NotificationService.class)
                .setTag(title)
                .setReplaceCurrent(false)
                .setRecurring(false)
                .setTrigger(Trigger.executionWindow(triggerDelay, triggerDelay + 60*60))
                .build();
    }
}
