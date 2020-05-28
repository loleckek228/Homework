package com.geekbrains.android.homework.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.geekbrains.android.homework.R;

public class NetworkStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager conMngr = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = conMngr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (isDisConnected(mobile))
            new Notification(context, R.drawable.ic_disconneting_network, "Сеть телефона отлючена");
    }

    private boolean isDisConnected(NetworkInfo networkInfo) {
        return networkInfo != null && !networkInfo.isConnectedOrConnecting();
    }
}