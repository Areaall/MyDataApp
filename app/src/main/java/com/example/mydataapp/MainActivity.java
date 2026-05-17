package com.example.mydataapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnCancel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);

        sharedPreferences = getSharedPreferences("MyDataApp", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(MainActivity.this, Dashboard.class));
            finish();
        }

        btnCancel.setOnClickListener(v -> {
            etUsername.setText("");
            etPassword.setText("");
        });

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();

            if (user.equals("admin") && pass.equals("admin123")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("namaUser", etUsername.getText().toString());
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Username/Password Salah atau Kosong!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}