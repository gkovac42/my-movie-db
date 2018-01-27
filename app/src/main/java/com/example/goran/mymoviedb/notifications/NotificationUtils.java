package com.example.goran.mymoviedb.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.goran.mymoviedb.R;

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
}
