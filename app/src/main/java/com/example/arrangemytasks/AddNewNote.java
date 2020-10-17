package com.example.arrangemytasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.arrangemytasks.databinding.ActivityAddNewNoteBinding;

public class AddNewNote extends AppCompatActivity {

    AppDatabase appDatabase;
    ActivityAddNewNoteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_note);
        appDatabase = AppDatabase.getInstance(this);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.editTextTextPersonName.getText().toString().isEmpty()){
                    appDatabase.noteDao().insert(
                            new Note(binding.editTextTextPersonName.getText().toString())
                    );
                   // appDatabase.notifyAll();
                    finish();
                }
            }
        });
    }
}