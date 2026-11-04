package com.itcast.helloworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "library.db";
    private static final int DB_VERSION = 4; // 版本号升级

    public BookDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. 用户表
        db.execSQL("CREATE TABLE user (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE NOT NULL, password TEXT NOT NULL, role INTEGER NOT NULL)");
        // 2. 图书表
        db.execSQL("CREATE TABLE book (id INTEGER PRIMARY KEY AUTOINCREMENT, book_name TEXT NOT NULL, author TEXT NOT NULL, publisher TEXT, price REAL, stock INTEGER NOT NULL)");
        // 3. 借阅记录表 (增加状态字段，方便查)
        db.execSQL("CREATE TABLE borrow_record (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, book_name TEXT NOT NULL, borrow_time TEXT)");

        // 插入默认账号
        db.execSQL("INSERT INTO user (username, password, role) VALUES ('admin', '123', 0)");
        db.execSQL("INSERT INTO user (username, password, role) VALUES ('student', '123', 1)");

        // 插入默认书
        db.execSQL("INSERT INTO book (book_name, author, publisher, price, stock) VALUES ('城南旧事', '林海音', '南京出版社', 23.0, 10)");
        db.execSQL("INSERT INTO book (book_name, author, publisher, price, stock) VALUES ('世说新语', '刘义庆', '中华书局', 45.0, 5)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS book");
        db.execSQL("DROP TABLE IF EXISTS borrow_record");
        onCreate(db);
    }
}