package com.itcast.helloworld;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etUser = findViewById(R.id.et_reg_username);
        EditText etPass = findViewById(R.id.et_reg_password);
        Button btnSubmit = findViewById(R.id.btn_reg_submit);
        Button btnBack = findViewById(R.id.btn_back_login);
        BookDbHelper dbHelper = new BookDbHelper(this);

        btnSubmit.setOnClickListener(v -> {
            String u = etUser.getText().toString();
            String p = etPass.getText().toString();
            if(u.isEmpty() || p.isEmpty()) return;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username", u);
            values.put("password", p);
            values.put("role", 1); // 默认注册为学生
            long res = db.insert("user", null, values);
            if(res != -1) {
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "账号已存在", Toast.LENGTH_SHORT).show();
            }
        });
        btnBack.setOnClickListener(v -> finish());
    }
}