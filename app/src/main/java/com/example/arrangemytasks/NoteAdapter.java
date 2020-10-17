package com.example.arrangemytasks;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    List<Note> allNotes;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NoteAdapter(Context context, List<Note> allNotes) {
        this.allNotes = allNotes;
        this.context = context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class NoteHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView nameTextView;
        public NoteHolder(@NonNull View view) {
            super(view);
            nameTextView = view.findViewById(R.id.name_text_view);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);

        return new NoteHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.nameTextView.setText(allNotes.get(position).getName() );
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return allNotes.size();
    }

}
