package com.example.mydataapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnLogout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);

        sharedPreferences = getSharedPreferences("MyDataApp", MODE_PRIVATE);
        String username = sharedPreferences.getString("namaUser", "User");
        tvWelcome.setText("Welcome to Dashboard, " + username);

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
