package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.Adapter.Rv_Adapter;
import com.example.todoapp.databinding.ActivityMainBinding;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        noteViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(getApplication()))
                .get(NoteViewModel.class);

        // Floating button
        binding.floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, DataInsertActivity.class);
            intent.putExtra("type", "addMode");
            startActivityForResult(intent, 1);
        });

        // RecyclerView binding
        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.RecyclerView.setHasFixedSize(true);
        Rv_Adapter adapter = new Rv_Adapter();
        binding.RecyclerView.setAdapter(adapter);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        // Swipe to delete or update
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT) {
                    showDeleteConfirmationDialog(adapter, viewHolder.getAdapterPosition());
                } else {
                    Intent intent = new Intent(MainActivity.this, DataInsertActivity.class);
                    intent.putExtra("type", "update");
                    intent.putExtra("title", adapter.getNote(viewHolder.getAdapterPosition()).getTitle());
                    intent.putExtra("desc", adapter.getNote(viewHolder.getAdapterPosition()).getDescription());
                    intent.putExtra("id", adapter.getNote(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent, 2);
                }
            }
        }).attachToRecyclerView(binding.RecyclerView);
    }

    private void showDeleteConfirmationDialog(Rv_Adapter adapter, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    noteViewModel.delete(adapter.getNote(position));
                    Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Rebind the item to prevent it from disappearing
                    adapter.notifyItemChanged(position);
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("desc");
            Note note = new Note(title, description);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == 2)
        {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("desc");
            Note note = new Note(title, description);
            note.setId(data.getIntExtra("id", 0));
            noteViewModel.update(note);
            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        }
    }
}
