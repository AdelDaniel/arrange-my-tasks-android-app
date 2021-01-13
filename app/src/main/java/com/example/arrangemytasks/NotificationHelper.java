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

class NotificationHelper extends ContextWrapper {
    private NotificationManager notifyManager;
    Intent notificationIntent;
    PendingIntent pendingIntent;
    Notification.Builder notificationBuilder;

    public static final String CHANNEL_ONE_ID = "com.adel.task.ringTime";
    public static final String CHANNEL_ONE_NAME = "Ring Time : Time to do Task Channel One";

    public static final String CHANNEL_TWO_ID = "myapplication.TWO";
    public static final String CHANNEL_TWO_NAME = "Channel Two";


    public static final int notification_one = 101;
    public static final int notification_two = 102;

//Create your notification channels//

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    public void createChannels() {

        notificationIntent = new Intent(this, RingActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        // String alarmTitle = String.format("%s Alarm", intent.getStringExtra(TITLE));
        NotificationChannel notificationChannel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, notifyManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            getManager().createNotificationChannel(notificationChannel);
        } else {


        }


        NotificationChannel notificationChannel2;
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
    public Notification.Builder getNotification1(String title, String body) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(getApplicationContext(), CHANNEL_ONE_ID)
                    .setContentTitle(title)
                    .setContentText(body + "\n Ring Ring .. Ring Ring")
                    .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        } else {
            return null;
        }

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

    //Post the notifications//
    public void postNotification(int id, String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            switch (id) {
                case notification_one:
                    notificationBuilder = getNotification1(title, "ONE");
                    break;

                case notification_two:
                    notificationBuilder = getNotification2(title, "TWO");
                    break;
            }
            notificationBuilder.build();
            if (notificationBuilder != null) {
                notify(id, notificationBuilder);
            }
        }
    }

    //Load the settings screen for the selected notification channel//
    public void goToNotificationSettings(String channel) {
        Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
        startActivity(i);
    }
}