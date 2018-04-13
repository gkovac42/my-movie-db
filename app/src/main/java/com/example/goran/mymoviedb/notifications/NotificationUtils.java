package com.example.goran.mymoviedb.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.goran.mymoviedb.R;

/**
 * Created by Goran on 27.1.2018..
 */

public class NotificationUtils {

    private static final String CHANNEL_ID = "channel_id";
    private static final String CHANNEL_NAME = "notification_channel";

    public static void showNotification(Context context, String message) {

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Android O must specify a notification channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_local_movies_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.tmdb_logo))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(1, notificationBuilder.build());
    }
}
