package com.example.mylist;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.mylist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Database db;
    private InputAdapter inputAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new Database(this);
        inputAdapter = new InputAdapter(db.getAllTasks(), this);
        binding.tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.tasksRecyclerView.setAdapter(inputAdapter);

        binding.addButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, Addtask.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputAdapter.refreshData(db.getAllTasks());
    }
}