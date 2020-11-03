package com.example.arrangemytasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.arrangemytasks.databinding.ActivityAddNewNoteBinding;

public class AddNewNote extends AppCompatActivity {

    final AppDatabase appDatabase;
    ActivityAddNewNoteBinding binding;
    String noteName;
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;

    public AddNewNote() {
        appDatabase = AppDatabase.getInstance(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_note);
        intiNotifications();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.editTextTextPersonName.getText().toString().isEmpty()){
                    noteName = binding.editTextTextPersonName.getText().toString() ;
                    appDatabase.noteDao().insert(
                            new Note(noteName)
                    );

                    showNotification();
                    synchronized(appDatabase){
                        appDatabase.notify();
                    }
                    // appDatabase.notifyAll();
                    finish();
                }
            }
        });
    }

    private void intiNotifications(){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "channel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    private void showNotification(){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, NotificationView.class);
        // Set the Activity to start in a new, empty task
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);

        // Create the PendingIntent
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = new NotificationCompat.Builder(AddNewNote.this, "1")
                .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentTitle("Notification added ")
                .setContentText("new Note added \n\n \t Note Name: " +noteName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setFullScreenIntent (pendingIntent,true);

        notificationManager= NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }
}