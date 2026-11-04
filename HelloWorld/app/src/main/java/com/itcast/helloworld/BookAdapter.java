package com.itcast.helloworld;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.VH> {
    private Context context;
    private Cursor cursor;
    private StudentActivity studentActivity;

    public BookAdapter(Context context, Cursor cursor, StudentActivity studentActivity) {
        this.context = context;
        this.cursor = cursor;
        this.studentActivity = studentActivity;
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(context).inflate(R.layout.item_student_book, parent, false));
    }

    @Override public void onBindViewHolder(@NonNull VH holder, int position) {
        if (cursor.moveToPosition(position)) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("book_name"));
            String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            int stock = cursor.getInt(cursor.getColumnIndexOrThrow("stock"));

            holder.tvName.setText(name);
            holder.tvInfo.setText(author + " | ¥" + price + " | 库存:" + stock);

            holder.btnBorrow.setOnClickListener(v -> studentActivity.borrowBook(name));
            holder.btnReturn.setOnClickListener(v -> studentActivity.returnBook(name));
        }
    }

    @Override public int getItemCount() { return cursor == null ? 0 : cursor.getCount(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvInfo;
        Button btnBorrow, btnReturn;
        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            btnBorrow = itemView.findViewById(R.id.btn_borrow);
            btnReturn = itemView.findViewById(R.id.btn_return);
        }
    }
}