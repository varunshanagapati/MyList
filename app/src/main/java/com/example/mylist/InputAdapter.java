package com.example.mylist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InputAdapter extends RecyclerView.Adapter<InputAdapter.NoteViewHolder> {

    private List<Input> notes;
    private final Database db;

    public InputAdapter(List<Input> notes, Context context) {
        this.notes = notes;
        this.db = new Database(context);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        ImageView updateButton;
        ImageView deleteButton;

        public NoteViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            updateButton = itemView.findViewById(R.id.updatesaveButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskitem, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Input input = notes.get(position);
        holder.titleTextView.setText(input.getTitle());
        holder.contentTextView.setText(input.getContent());

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), Edittask.class);
                intent.putExtra("note_id", input.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteNote(input.getId());
                refreshData(db.getAllTasks());
                Toast.makeText(holder.itemView.getContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void refreshData(List<Input> newNotes) {
        notes = newNotes;
        notifyDataSetChanged();
    }
}
