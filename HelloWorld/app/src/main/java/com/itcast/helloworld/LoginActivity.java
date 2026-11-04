package com.itcast.helloworld;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 获取界面控件
        EditText etUser = findViewById(R.id.et_username);
        EditText etPass = findViewById(R.id.et_password);
        CheckBox cbAdmin = findViewById(R.id.cb_admin);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnReg = findViewById(R.id.btn_register);

        BookDbHelper dbHelper = new BookDbHelper(this);

        // 点击“登录”按钮
        btnLogin.setOnClickListener(v -> {
            String u = etUser.getText().toString().trim();
            String p = etPass.getText().toString().trim();

            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            // 0代表管理员，1代表学生
            int role = cbAdmin.isChecked() ? 0 : 1;

            SQLiteDatabase db = dbHelper.getReadableDatabase();
            // 查询账号、密码和对应的身份是否匹配
            Cursor c = db.query("user", null, "username=? AND password=? AND role=?", new String[]{u, p, String.valueOf(role)}, null, null, null);

            if (c.moveToFirst()) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                // 根据身份跳转到不同页面
                Intent intent = new Intent(LoginActivity.this, role == 0 ? AdminActivity.class : StudentActivity.class);
                intent.putExtra("username", u);
                startActivity(intent);
                finish(); // 销毁登录页，防止按返回键又回到登录界面
            } else {
                Toast.makeText(this, "登录失败，请检查账号密码或身份", Toast.LENGTH_SHORT).show();
            }
            c.close();
        });

        // 点击“去注册”按钮，跳转到注册页面
        btnReg.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}