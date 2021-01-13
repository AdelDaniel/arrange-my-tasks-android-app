package com.example.arrangemytasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.arrangemytasks.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;


    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int notification_one = 101;
    private static final int notification_two = 102;
    Notification.Builder notificationBuilder;
    private NotificationHelper notificationHelper;


    AppDatabase appDatabase;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        taskAdapter = new TaskAdapter(this, appDatabase.taskDao().getAll());
        binding.recyclerView.setAdapter(taskAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewTask.class);
                startActivity(intent);
                

                postNotification(notification_one, "111111111111111111");
//                goToNotificationSettings(NotificationHelper.CHANNEL_ONE_ID);
                // postNotification(notification_two, "22222222");

            }
        });


        notificationHelper = new NotificationHelper(this);
    }

    //Post the notifications//
    public void postNotification(int id, String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            switch (id) {
                case notification_one:
                    notificationBuilder = notificationHelper.getNotification1(title, "ONE");
                    break;

                case notification_two:
                    notificationBuilder = notificationHelper.getNotification2(title, "TWO");
                    break;
            }
            if (notificationBuilder != null) {
                notificationHelper.notify(id, notificationBuilder);
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
