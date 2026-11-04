package com.itcast.helloworld;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private EditText etName, etAuthor, etPrice, etStock;
    private ListView lvBooks;
    private BookDbHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        etName = findViewById(R.id.et_book_name);
        etAuthor = findViewById(R.id.et_book_author);
        etPrice = findViewById(R.id.et_book_price);
        etStock = findViewById(R.id.et_book_stock);
        lvBooks = findViewById(R.id.lv_books);
        Button btnAdd = findViewById(R.id.btn_add_book);
        Button btnRecords = findViewById(R.id.btn_view_records);

        dbHelper = new BookDbHelper(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookList);
        lvBooks.setAdapter(adapter);

        loadBooks();

        // 添加图书（支持自定义库存）
        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String author = etAuthor.getText().toString();
            String priceStr = etPrice.getText().toString();
            String stockStr = etStock.getText().toString();

            if (name.isEmpty() || author.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty()) {
                Toast.makeText(this, "请填写完整", Toast.LENGTH_SHORT).show(); return;
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("book_name", name); cv.put("author", author);
            cv.put("price", Double.parseDouble(priceStr));
            cv.put("stock", Integer.parseInt(stockStr));
            cv.put("publisher", "默认出版社");
            db.insert("book", null, cv);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            loadBooks();
        });

        // 查看借阅记录
        btnRecords.setOnClickListener(v -> {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("borrow_record", null, null, null, null, null, "id DESC");
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "暂无借阅记录", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder sb = new StringBuilder();
            while (cursor.moveToNext()) {
                String user = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String book = cursor.getString(cursor.getColumnIndexOrThrow("book_name"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("borrow_time"));
                sb.append("学生: ").append(user).append("\n书: ").append(book).append("\n时间: ").append(time).append("\n\n");
            }
            cursor.close();
            new AlertDialog.Builder(this).setTitle("借阅记录").setMessage(sb.toString()).setPositiveButton("知道了", null).show();
        });

        // 长按删除逻辑
        lvBooks.setOnItemLongClickListener((parent, view, position, id) -> {
            String selected = bookList.get(position);
            String bookName = selected.split(" \\| ")[0];
            new AlertDialog.Builder(this)
                    .setTitle("删除图书").setMessage("确定要删除《" + bookName + "》吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("book", "book_name=?", new String[]{bookName});
                        loadBooks();
                    }).setNegativeButton("取消", null).show();
            return true;
        });
    }

    private void loadBooks() {
        bookList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("book", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("book_name"));
            String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            int stock = cursor.getInt(cursor.getColumnIndexOrThrow("stock"));
            bookList.add(name + " | " + author + " | ¥" + price + " | 库存:" + stock);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}