package com.example.lezada.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.lezada.R;

public class ResetPassActivity extends AppCompatActivity {
    EditText emailEditText;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        initView();
    }

    private void initView() {
        emailEditText = findViewById(R.id.emailEditText);
        resetButton = findViewById(R.id.resetButton);

    }
}