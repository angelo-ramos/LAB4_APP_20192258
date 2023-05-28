package com.example.lab4_app_20192258;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lab4_app_20192258.databinding.ActivityMainBinding;
import com.example.lab4_app_20192258.databinding.ActivityTrabajadorBinding;

public class TrabajadorActivity extends AppCompatActivity {
    ActivityTrabajadorBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrabajadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrabajadorActivity.this, VerInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}