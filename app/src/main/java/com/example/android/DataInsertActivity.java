package com.example.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.databinding.ActivityDataInsertBinding;


public class DataInsertActivity extends AppCompatActivity {
    ActivityDataInsertBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDataInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Update
        String type = getIntent().getStringExtra("type");
        if (type.equals("update")) {
            setTitle("Update Data");
            binding.tvtittle.setText(getIntent().getStringExtra("title"));
            binding.tvDescription.setText(getIntent().getStringExtra("desc"));
            int id = getIntent().getIntExtra("id", 0);
            binding.btnAdd.setText("Update");

            binding.btnAdd.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.putExtra("title", binding.tvtittle.getText().toString());
                intent.putExtra("desc", binding.tvDescription.getText().toString());
                intent.putExtra("id", id);
                setResult(RESULT_OK, intent);
                finish();
            });
        }
        else {
            binding.btnAdd.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.putExtra("title", binding.tvtittle.getText().toString());
                intent.putExtra("desc", binding.tvDescription.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            });

        }



    }
    @Override
   public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));

    }
}