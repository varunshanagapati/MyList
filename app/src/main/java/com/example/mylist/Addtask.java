package com.example.mylist;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mylist.databinding.ActivityAddtaskBinding;

public class Addtask extends AppCompatActivity {

    private ActivityAddtaskBinding binding;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddtaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new Database(this);

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.titleEditText.getText().toString();
                String content = binding.contentEditText.getText().toString();
                Input input = new Input(0, title, content);
                db.insertInput(input);
                finish();
                Toast.makeText(Addtask.this, "Task Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}