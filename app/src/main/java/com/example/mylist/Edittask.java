package com.example.mylist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mylist.databinding.ActivityEdittaskBinding;

public class Edittask extends AppCompatActivity {

    private ActivityEdittaskBinding binding;
    private Database db;
    private int inputId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivityEdittaskBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        db = new Database(this);

        inputId = getIntent().getIntExtra("note_id", -1);
        if (inputId == -1) {
            finish();
            return;
        }

        Input input = db.getNodeById(inputId);
        binding.updatetitleEditText.setText(input.getTitle());
        binding.updatecontentEditText.setText(input.getContent());

        binding.updateButton.setOnClickListener(view -> {
            String newTitle = binding.updatetitleEditText.getText().toString();
            String newContent = binding.updatecontentEditText.getText().toString();
            Input updatedinput = new Input(inputId, newTitle, newContent);
            db.updateNote(updatedinput);
            finish();
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show();
        });
    }
}