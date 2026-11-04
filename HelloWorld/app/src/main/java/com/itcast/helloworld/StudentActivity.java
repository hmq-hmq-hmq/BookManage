package com.itcast.helloworld;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StudentActivity extends AppCompatActivity {
    private RecyclerView rvBooks;
    private BookDbHelper dbHelper;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        currentUsername = getIntent().getStringExtra("username");
        dbHelper = new BookDbHelper(this);
        rvBooks = findViewById(R.id.rv_books);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        loadBooks();
    }

    private void loadBooks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("book", null, null, null, null, null, null);
        rvBooks.setAdapter(new BookAdapter(this, cursor, this));
    }

    // 借书逻辑
    public void borrowBook(String bookName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("book", new String[]{"stock"}, "book_name=?", new String[]{bookName}, null, null, null);
        if (c.moveToFirst()) {
            int stock = c.getInt(0);
            if (stock > 0) {
                db.execSQL("UPDATE book SET stock = stock - 1 WHERE book_name = ?", new String[]{bookName});
                db.execSQL("INSERT INTO borrow_record (username, book_name, borrow_time) VALUES (?, ?, datetime('now'))", new String[]{currentUsername, bookName});
                Toast.makeText(this, "借阅成功", Toast.LENGTH_SHORT).show();
                loadBooks();
            } else {
                Toast.makeText(this, "库存不足", Toast.LENGTH_SHORT).show();
            }
        }
        c.close();
    }

    // 还书逻辑 (修复闪退版)
    public void returnBook(String bookName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // 检查该学生是否借过这本书
        Cursor c = db.query("borrow_record", new String[]{"id"}, "username=? AND book_name=?", new String[]{currentUsername, bookName}, null, null, null);

        if (c.moveToFirst()) {
            int recordId = c.getInt(0); // 获取借阅记录的ID

            // 1. 库存加 1
            db.execSQL("UPDATE book SET stock = stock + 1 WHERE book_name = ?", new String[]{bookName});
            // 2. 根据 ID 删除记录
            db.delete("borrow_record", "id=?", new String[]{String.valueOf(recordId)});

            Toast.makeText(this, "还书成功", Toast.LENGTH_SHORT).show();
            loadBooks();
        } else {
            Toast.makeText(this, "你并没有借阅这本书，无法还书", Toast.LENGTH_SHORT).show();
        }
        c.close();
    }
}