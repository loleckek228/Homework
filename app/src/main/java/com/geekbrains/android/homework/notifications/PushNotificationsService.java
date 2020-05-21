package com.geekbrains.android.homework.notifications;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotificationsService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        try {
            long ticketId = Long.parseLong(remoteMessage.getData().get("ticketId"));
            if (remoteMessage.getNotification() != null) {
                Log.d("TAG", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }
            Toast.makeText(this, String.valueOf(ticketId), Toast.LENGTH_LONG).show();
        } catch (NullPointerException exc) {
            Toast.makeText(this, "Exception!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}