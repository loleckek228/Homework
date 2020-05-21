package com.geekbrains.android.homework.notifications;

import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

public class Notification {
    private int messageId = 1000;

    public Notification(Context context, int icon, String notificationText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                .setSmallIcon(icon)
                .setContentTitle("Network Status Info")
                .setContentText(notificationText);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());
    }
}