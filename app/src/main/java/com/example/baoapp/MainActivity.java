// MainActivity.java
package com.example.baoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {
    EditText etMaLop, etTenLop, etSiSo;
    Button btnInsert, btnDelete, btnUpdate, btnQuery;
    TextView tvResult;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMaLop = findViewById(R.id.etMaLop);
        etTenLop = findViewById(R.id.etTenLop);
        etSiSo = findViewById(R.id.etSiSo);
        btnInsert = findViewById(R.id.btnInsert);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnQuery = findViewById(R.id.btnQuery);
        tvResult = findViewById(R.id.tvResult);

        // Tạo hoặc mở CSDL
        db = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Class(MaLop VARCHAR, TenLop VARCHAR, SiSo INT);");

        // Thêm lớp
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maLop = etMaLop.getText().toString();
                String tenLop = etTenLop.getText().toString();
                int siSo = Integer.parseInt(etSiSo.getText().toString());
                db.execSQL("INSERT INTO Class VALUES(?, ?, ?);", new Object[]{maLop, tenLop, siSo});
                showData();
            }
        });

        // Xóa lớp
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maLop = etMaLop.getText().toString();
                db.execSQL("DELETE FROM Class WHERE MaLop = ?;", new Object[]{maLop});
                showData();
            }
        });

        // Cập nhật lớp
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maLop = etMaLop.getText().toString();
                String tenLop = etTenLop.getText().toString();
                int siSo = Integer.parseInt(etSiSo.getText().toString());
                db.execSQL("UPDATE Class SET TenLop = ?, SiSo = ? WHERE MaLop = ?;", new Object[]{tenLop, siSo, maLop});
                showData();
            }
        });

        // Truy vấn dữ liệu
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData();
            }
        });
    }

    // Hàm hiển thị dữ liệu từ CSDL
    private void showData() {
        Cursor cursor = db.rawQuery("SELECT * FROM Class", null);
        StringBuilder result = new StringBuilder();
        while (cursor.moveToNext()) {
            String maLop = cursor.getString(0);
            String tenLop = cursor.getString(1);
            int siSo = cursor.getInt(2);
            result.append(maLop).append(" - ").append(tenLop).append(" - ").append(siSo).append("\n");
        }
        tvResult.setText(result.toString());
        cursor.close();
    }
}