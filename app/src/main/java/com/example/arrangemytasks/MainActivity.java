package com.example.arrangemytasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arrangemytasks.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding ;
    AppDatabase appDatabase;
    NoteAdapter noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        noteAdapter = new NoteAdapter(this,appDatabase.noteDao().getAll());
        binding.recyclerView.setAdapter(noteAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNewNote.class);
                startActivity(intent);

            }
        });
    }
}