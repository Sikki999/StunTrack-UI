package com.example.appscoba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.appscoba.helper.Helper;
import com.example.appscoba.model.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JadwalKuliah extends AppCompatActivity {

    ListView listView;
    List<Data> lists = new ArrayList<>();
    ArrayAdapter<Data> adapter; // Ganti Adapter dengan ArrayAdapter
    Helper db;

    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_kuliah);

        db = new Helper(getApplicationContext());
        btnAdd = findViewById(R.id.btnadd);
        listView = findViewById(R.id.listitem);

        setupToolbar();

        // Inisialisasi adapter dengan layout item yang sesuai
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lists);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JadwalKuliah.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View itemView, int i, long l) {
                final String id = lists.get(i).getId();
                final String name = lists.get(i).getName();
                final String email = lists.get(i).getEmail();
                final CharSequence[] dialogItem = {"Edit", "Hapus"};

                // Buat dialog di sini
                new AlertDialog.Builder(JadwalKuliah.this)
                        .setItems(dialogItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        Intent intent = new Intent(JadwalKuliah.this, EditorActivity.class);
                                        intent.putExtra("id", id);
                                        intent.putExtra("name", name);
                                        intent.putExtra("email", email);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        db.delete(Integer.parseInt(id));
                                        lists.clear();
                                        getdata();
                                        break;
                                }
                            }
                        })
                        .show();
                return true; // Mengembalikan true untuk menunjukkan bahwa sudah menangani peristiwa long click
            }
        });

        getdata();
    }

    private void getdata() {
        ArrayList<HashMap<String, String>> rows = db.getAll();
        for (int i = 0; i < rows.size(); i++) {
            String id = rows.get(i).get("id");
            String name = rows.get(i).get("name");
            String email = rows.get(i).get("email");

            Data data = new Data();
            data.setId(id);
            data.setName(name);
            data.setEmail(email);
            lists.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lists.clear();
        getdata();
    }
    private void setupToolbar() {  //Metode untuk mengatur dan menampilkan toolbar dengan judul "Detail Mobil".
        Toolbar toolbar = findViewById(R.id.tbPenyewa);
        toolbar.setTitle("Review materi");
    }
}
