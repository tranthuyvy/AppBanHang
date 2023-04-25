package com.example.lezada.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.lezada.R;

public class AppleProductActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_product);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Apple");
    }
}