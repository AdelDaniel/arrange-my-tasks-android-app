package com.example.arrangemytasks;

import android.graphics.Color;
import android.content.Context;
import android.content.ContextWrapper;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

class NotificationHelper extends ContextWrapper {
    private NotificationManager notifyManager;
    public static final String CHANNEL_ONE_ID = "myapplication.ONE";
    public static final String CHANNEL_ONE_NAME = "Channel One";
    public static final String CHANNEL_TWO_ID = "myapplication.TWO";
    public static final String CHANNEL_TWO_NAME = "Channel Two";

//Create your notification channels//

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels() {

        NotificationChannel notificationChannel ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, notifyManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            getManager().createNotificationChannel(notificationChannel);
        }


        NotificationChannel notificationChannel2 ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel2 = new NotificationChannel(CHANNEL_TWO_ID,
                    CHANNEL_TWO_NAME, notifyManager.IMPORTANCE_DEFAULT);
            notificationChannel2.enableLights(false);
            notificationChannel2.enableVibration(true);
            notificationChannel2.setLightColor(Color.RED);
            notificationChannel2.setShowBadge(false);
            getManager().createNotificationChannel(notificationChannel2);
        }


    }

//Create the notification that’ll be posted to Channel One//
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotification1(String title, String body) {
            return new Notification.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.ic_baseline_warning_24)
                    .setAutoCancel(true);

    }

//Create the notification that’ll be posted to Channel Two//
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getNotification2(String title, String body) {
            return new Notification.Builder(getApplicationContext(), CHANNEL_TWO_ID)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.alert)
                    .setAutoCancel(true);

    }


    public void notify(int id, Notification.Builder notification) {
        getManager().notify(id, notification.build());
    }

//Send your notifications to the NotificationManager system service//
    private NotificationManager getManager() {
        if (notifyManager == null) {
            notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notifyManager;
    }
}