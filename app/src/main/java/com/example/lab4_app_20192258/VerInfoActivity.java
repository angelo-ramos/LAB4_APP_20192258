package com.example.lab4_app_20192258;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lab4_app_20192258.databinding.ActivityVerInfoBinding;

public class VerInfoActivity extends AppCompatActivity {
    ActivityVerInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}