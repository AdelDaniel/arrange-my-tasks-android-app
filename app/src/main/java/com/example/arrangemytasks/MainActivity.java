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

//                goToNotificationSettings(NotificationHelper.CHANNEL_ONE_ID);
                // postNotification(notification_two, "22222222");

            }
        });


    }

}
