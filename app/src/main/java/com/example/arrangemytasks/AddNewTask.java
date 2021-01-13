package com.example.arrangemytasks;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TimePicker;

import com.example.arrangemytasks.databinding.ActivityAddNewTaskBinding;

import java.util.Random;

public class AddNewTask extends AppCompatActivity {

    AppDatabase appDatabase;
    ActivityAddNewTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_task);
        appDatabase = AppDatabase.getInstance(this);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleAlarm();
                appDatabase.notify();
                finish();
            }
        });

        binding.textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AddNewTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        binding.textViewTime.setText("time: " + hourOfDay + ":" + minute);
                    }
                }, 1, 1, false).show();
            }
        });
//        binding.fragmentCreateAlarmTimePicker.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("SetTextI18n")
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View v) {
//
//                        binding.textViewTime.setText( "time: "+binding.fragmentCreateAlarmTimePicker.getHour()
//                                +":" + binding.fragmentCreateAlarmTimePicker.getMinute() );
//
//                }
//
//        });

        binding.fragmentCreateAlarmRecurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.fragmentCreateAlarmRecurringOptions.setVisibility(View.VISIBLE);
                } else {
                    binding.fragmentCreateAlarmRecurringOptions.setVisibility(View.GONE);
                }
            }
        });

    }

    private void scheduleAlarm() {
        int taskId = new Random().nextInt(Integer.MAX_VALUE);

        Task task = new Task(
                taskId,
                TimePickerUtil.getTimePickerHour(binding.fragmentCreateAlarmTimePicker),
                TimePickerUtil.getTimePickerMinute(binding.fragmentCreateAlarmTimePicker),
                binding.editTextTaskName.getText().toString(),
                true,
                binding.fragmentCreateAlarmRecurring.isChecked(),
                binding.fragmentCreateAlarmCheckMon.isChecked(),
                binding.fragmentCreateAlarmCheckTue.isChecked(),
                binding.fragmentCreateAlarmCheckWed.isChecked(),
                binding.fragmentCreateAlarmCheckThu.isChecked(),
                binding.fragmentCreateAlarmCheckFri.isChecked(),
                binding.fragmentCreateAlarmCheckSat.isChecked(),
                binding.fragmentCreateAlarmCheckSun.isChecked()
        );

        appDatabase.taskDao().insert(task);
        task.schedule(AddNewTask.this);
    }

}