package com.example.mydataapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private EditText etNim, etNama, etProdi, etKelas, etAlamat, etEmail;
    private Button btnTambahData, btnLogout;
    private ListView listViewData;
    private TextView tvWelcome;

    private ArrayList<String> daftarData;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.tvWelcome);
        etNim = findViewById(R.id.etNim);
        etNama = findViewById(R.id.etNama);
        etProdi = findViewById(R.id.etProdi);
        etKelas = findViewById(R.id.etKelas);
        etAlamat = findViewById(R.id.etAlamat);
        etEmail = findViewById(R.id.etEmail);
        btnTambahData = findViewById(R.id.btnTambahData);
        btnLogout = findViewById(R.id.btnLogout);
        listViewData = findViewById(R.id.listViewData);

        SharedPreferences pref = getSharedPreferences("MyDataApp", MODE_PRIVATE);
        String username = pref.getString("namaUser", "Admin");
        tvWelcome.setText("Selamat Datang, " + username);

        daftarData = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, daftarData);
        listViewData.setAdapter(adapter);

        btnTambahData.setOnClickListener(v -> {
            String nim = etNim.getText().toString().trim();
            String nama = etNama.getText().toString().trim();
            String prodi = etProdi.getText().toString().trim();
            String kelas = etKelas.getText().toString().trim();
            String alamat = etAlamat.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (nim.isEmpty() || nama.isEmpty()) {
                Toast.makeText(this, "NIM dan Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!nim.matches("[0-9]+")) {
                etNim.setError("NIM harus berupa angka saja!");
                Toast.makeText(this, "NIM Invalid: Tidak boleh ada huruf", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!nama.matches("[a-zA-Z\\s]+")) {
                etNama.setError("Nama harus berupa huruf saja!");
                Toast.makeText(this, "Nama Invalid: Tidak boleh ada angka", Toast.LENGTH_SHORT).show();
                return;
            }

            String gabungData = "NIM: " + nim + "\nNama: " + nama + "\nProdi: " + prodi +
                    "\nKelas: " + kelas + "\nAlamat: " + alamat + "\nEmail: " + email;

            daftarData.add(gabungData);
            adapter.notifyDataSetChanged();
            bersihkanForm();
            Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
        });

        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            Intent intent = new Intent(Dashboard.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void bersihkanForm() {
        etNim.setText("");
        etNama.setText("");
        etProdi.setText("");
        etKelas.setText("");
        etAlamat.setText("");
        etEmail.setText("");
    }
}
