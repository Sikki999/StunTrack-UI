package com.example.appscoba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appscoba.helper.Helper;

public class EditorActivity extends AppCompatActivity {

    private EditText editMatkul, editKeterangan;
    private Button btnSave;
    private Helper db = new Helper(this);
    private String id, name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editMatkul = findViewById(R.id.edit_matkul);
        editKeterangan = findViewById(R.id.edit_keterangan);
        btnSave = findViewById(R.id.btn_save);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");

        if (TextUtils.isEmpty(id)) {
            setTitle("Tambah Matkul");
        } else {
            setTitle("Edit Matkul");
            editMatkul.setText(name);
            editKeterangan.setText(email);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (TextUtils.isEmpty(id)) {
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e) {
                    Log.e("Saving", e.getMessage());
                }
            }
        });
    }

    private void save() {
        String matkul = editMatkul.getText().toString();
        String keterangan = editKeterangan.getText().toString();

        if (TextUtils.isEmpty(matkul) || TextUtils.isEmpty(keterangan)) {
            Toast.makeText(getApplicationContext(), "Silahkan Isi semua data", Toast.LENGTH_SHORT).show();
        } else {
            db.insert(matkul, keterangan);
            finish();
        }
    }

    private void edit() {
        String matkul = editMatkul.getText().toString();
        String keterangan = editKeterangan.getText().toString();

        if (TextUtils.isEmpty(matkul) || TextUtils.isEmpty(keterangan)) {
            Toast.makeText(getApplicationContext(), "Silahkan Isi semua data", Toast.LENGTH_SHORT).show();
        } else {
            db.update(Integer.parseInt(id), matkul, keterangan);
            finish();
        }
    }
}