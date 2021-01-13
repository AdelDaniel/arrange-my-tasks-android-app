package com.example.arrangemytasks;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import static com.example.arrangemytasks.MyReceiver.TITLE;

public class TaskService extends Service {

    private static final String CHANNEL_ID = "1";

    public TaskService() {
    }

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        } else {

        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent notificationIntent = new Intent(this, RingActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//
//        String alarmTitle = String.format("%s Alarm", intent.getStringExtra(TITLE));
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle(alarmTitle)
//                .setContentText("Ring Ring .. Ring Ring")
//                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
//                .setContentIntent(pendingIntent)
//                .build();
////        startForeground(1, notification);

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
}