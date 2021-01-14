package com.example.arrangemytasks;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.content.Context;
import android.content.ContextWrapper;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

class NotificationHelper extends ContextWrapper {
    private NotificationManager notificationManager;
    Intent notificationIntent;
    PendingIntent pendingIntent;
    Notification.Builder notificationBuilder;
    NotificationChannel notificationChannel;


    public static final String CHANNEL_ONE_ID = "com.adel.task.ringTime";
    public static final String CHANNEL_ONE_NAME = "Ring Time : Time to do Task Channel One";
    public static final int notification_one = 101;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    //Create your notification channels//
    public void createChannels() {
        notificationIntent = new Intent(this, RingActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, notificationManager.IMPORTANCE_NONE);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            createNotificationManager();
        } else {
            // If earlier version channel ID is not used
            // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
        }

    }


    Notification notification;

    //Post the notifications//
    public void postNotification(String title, String body) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = new Notification.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                    .setContentTitle(title)
                    .setContentText(body + "\n Ring Ring .. Ring Ring")
                    .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                    .setContentIntent(pendingIntent);
        }
        notification = notificationBuilder.setOngoing(true)
                .setContentTitle(title)
                .setContentText(body + "\n Ring Ring .. Ring Ring")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setPriority(Notification.PRIORITY_MIN)
                .setContentIntent(pendingIntent)
                .build();
    }

    public Notification getNotification() {
        return notification;
    }
//
//    public void notify(int id, Notification.Builder notification) {
//        getManager().notify(id, notification.build());
//    }
//


    //Send your notifications to the NotificationManager system service//
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        notificationManager.createNotificationChannel(notificationChannel);
    }

    //Load the settings screen for the selected notification channel//
    public void goToNotificationSettings(String channel) {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
        startActivity(i);
    }
}