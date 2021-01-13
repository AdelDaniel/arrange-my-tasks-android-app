package com.example.arrangemytasks;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
//
//public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
//
//    List<Task> allTasks;
//    Context context;
//
//    // Provide a suitable constructor (depends on the kind of dataSet)
//    public TaskAdapter(Context context, List<Task> allTasks) {
//        this.allTasks = allTasks;
//        this.context = context;
//    }
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class TaskHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public TextView nameTextView;
//        public TaskHolder(@NonNull View view) {
//            super(view);
//            nameTextView = view.findViewById(R.id.name_text_view);
//        }
//    }
//
//    // Create new views (invoked by the layout manager)
//    @NonNull
//    @Override
//    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
//
//        return new TaskHolder(view);
//    }
//
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
//
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.nameTextView.setText(allTasks.get(position).getTitle() );
//    }
//
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return allTasks.size();
//    }
//
//}

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    List<Task> allTasks;
    Context context;

    public TaskAdapter(Context context, List<Task> allTasks) {
        this.allTasks = allTasks;
        this.context = context;
    }


    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = allTasks.get(position);
        holder.bind(task);
    }


    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    public void setAlarms(List<Task> allTasks) {
        this.allTasks = allTasks;
        notifyDataSetChanged();
    }


    public class TaskHolder extends RecyclerView.ViewHolder {
        private TextView alarmTime;
        private ImageView alarmRecurring;
        private TextView alarmRecurringDays;
        private TextView alarmTitle;

        Switch alarmStarted;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            alarmTime = itemView.findViewById(R.id.item_alarm_time);
            alarmStarted = itemView.findViewById(R.id.item_alarm_started);
            alarmRecurring = itemView.findViewById(R.id.item_alarm_recurring);
            alarmRecurringDays = itemView.findViewById(R.id.item_alarm_recurringDays);
            alarmTitle = itemView.findViewById(R.id.item_alarm_title);
        }

        public void bind(Task task) {
            String alarmText = String.format("%02d:%02d", task.getHour(), task.getMinute());
            alarmTime.setText(alarmText);
            alarmStarted.setChecked(task.isStarted());

            if (task.isRecurring()) {
                alarmRecurring.setImageResource(R.drawable.ic_repeat_black_24dp);
                alarmRecurringDays.setText(task.getRecurringDaysText());
            } else {
                alarmRecurring.setImageResource(R.drawable.ic_looks_one_black_24dp);
                alarmRecurringDays.setText("Once Off");
            }

            if (task.getTitle().length() != 0) {
                alarmTitle.setText(task.getTitle());
            } else {
                alarmTitle.setText("My task");
            }

        }
    }
}
