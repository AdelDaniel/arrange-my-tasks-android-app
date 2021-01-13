package com.example.arrangemytasks;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.arrangemytasks.MyReceiver.TITLE;

public class TaskService extends Service {
    //
    private static final String CHANNEL_ID = "1";
    private NotificationHelper notificationHelper;
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;

    public TaskService() {
    }

    Intent notificationIntent;
    PendingIntent pendingIntent;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();


        notificationHelper = new NotificationHelper(this);
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        } else {

        }
        notificationIntent = new Intent(this, RingActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String alarmTitle = String.format("%s Alarm", intent.getStringExtra(TITLE));
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Ring Ring .. Ring Ring")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        // notificationHelper.postNotification(NotificationHelper.notification_one, "adel");
        mediaPlayer.start();


        long[] pattern = {0, 100, 1000};
        vibrator.vibrate(pattern, 0);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void showNotification() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, RingActivity.class);
        // Set the Activity to start in a new, empty task
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);

        // Create the PendingIntent
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentTitle("Notification added ")
                .setContentText("new Note added \n\n \t Note Name: ")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        //.setFullScreenIntent (pendingIntent,true);

        notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());

        /**----------------------------------------**/
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                channel.setAllowBubbles(true);
            }
            channel.setShowBadge(true);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}