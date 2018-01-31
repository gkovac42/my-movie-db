package com.example.goran.mymoviedb.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.goran.mymoviedb.R;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.Calendar;

/**
 * Created by Goran on 27.1.2018..
 */

public class NotificationUtils {

    public static void showNotification(Context context, String message) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Android O must specify a notification channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    "channel_id",
                    "notification_channel",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.tmdb_logo)
                .setContentTitle("Playing now!")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(1, notificationBuilder.build());
    }

    private static Job createJob(FirebaseJobDispatcher dispatcher, String title, Long releaseDate) {

        Bundle extras = new Bundle();
        extras.putString("movie_title", title);

        Long currentDate = Calendar.getInstance().getTimeInMillis();

        int triggerDelay = (int) ((releaseDate - currentDate) / 1000);

        Log.i("NOTIFICATION", title + " " + triggerDelay);

        return dispatcher.newJobBuilder()
                .setExtras(extras)
                .setLifetime(Lifetime.FOREVER)
                .setService(NotificationService.class)
                .setTag(title)
                .setReplaceCurrent(false)
                .setRecurring(false)
                .setTrigger(Trigger.executionWindow(triggerDelay, triggerDelay + 600))
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .build();
    }

    public static void scheduleJob(String title, Long releaseDate, Context context) {

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        Job job = createJob(dispatcher, title, releaseDate);

        dispatcher.mustSchedule(job);
    }

    public static void cancelJob(Context context, String title) {

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        dispatcher.cancel(title);
    }
}
