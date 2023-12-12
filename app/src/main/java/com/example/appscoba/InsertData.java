package com.example.appscoba;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertData extends AppCompatActivity {
    private EditText etagenda, etwaktu, etKeterangan;
    private Button btnadd;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        databaseHelper = new DatabaseHelper(this);

        etagenda = findViewById(R.id.et_agenda);
        etwaktu = findViewById(R.id.et_waktu1);
        etKeterangan = findViewById(R.id.etKeterangan1);
        btnadd = findViewById(R.id.btnAdd);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etAgenda = etagenda.getText().toString();
                String etWaktu = etwaktu.getText().toString();
                String etketerangan = etKeterangan.getText().toString();

                if (etAgenda.isEmpty() || etWaktu.isEmpty() || etketerangan.isEmpty()) {
                    Toast.makeText(InsertData.this, "Pastikan terisi semua", Toast.LENGTH_SHORT).show();
                    return;
                }

                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("agenda", etAgenda);
                values.put("waktu", etWaktu);
                values.put("keterangan", etketerangan);

                long result = db.insert("catatan", null, values);
                db.close(); // Close the database after use

                if (result != -1) {
                    Toast.makeText(InsertData.this, "Data Inserted successfully", Toast.LENGTH_SHORT).show();
                    etagenda.setText("");
                    etwaktu.setText("");
                    etKeterangan.setText("");
                } else {
                    Toast.makeText(InsertData.this, "Failed to insert data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
