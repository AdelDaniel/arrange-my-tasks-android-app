package com.example.arrangemytasks;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;


import static com.example.arrangemytasks.MyReceiver.TITLE;

public class TaskService extends Service {
    private NotificationHelper notificationHelper;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    public TaskService() {
    }

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

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String alarmTitle = String.format("%s Alarm", intent.getStringExtra(TITLE));

        notificationHelper.postNotification(alarmTitle, "this is time");
        startForeground(NotificationHelper.notification_one, notificationHelper.getNotification());
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