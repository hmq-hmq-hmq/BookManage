package com.itcast.helloworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //4本书最大馆藏
    private final int book1Max = 3;
    private final int book2Max = 2;
    private final int book3Max = 4;
    private final int book4Max = 3;

    //当前库存
    private int book1Stock = 3;
    private int book2Stock = 2;
    private int book3Stock = 4;
    private int book4Stock = 3;

    //统计计数
    private int totalBorrowTimes = 0;
    private int totalReturnTimes = 0;

    //控件
    private TextView tvBook1Count, tvBook2Count, tvBook3Count, tvBook4Count;
    private TextView tvBook1Status, tvBook2Status, tvBook3Status, tvBook4Status;
    private TextView tvTotalInfo;
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBook1Count = findViewById(R.id.book1_count);
        tvBook2Count = findViewById(R.id.book2_count);
        tvBook3Count = findViewById(R.id.book3_count);
        tvBook4Count = findViewById(R.id.book4_count);

        tvBook1Status = findViewById(R.id.book1_status);
        tvBook2Status = findViewById(R.id.book2_status);
        tvBook3Status = findViewById(R.id.book3_status);
        tvBook4Status = findViewById(R.id.book4_status);

        tvTotalInfo = findViewById(R.id.total_info);
        tvLog = findViewById(R.id.log_text);

        Button borrow1 = findViewById(R.id.btn_borrow1);
        Button return1 = findViewById(R.id.btn_return1);
        Button borrow2 = findViewById(R.id.btn_borrow2);
        Button return2 = findViewById(R.id.btn_return2);
        Button borrow3 = findViewById(R.id.btn_borrow3);
        Button return3 = findViewById(R.id.btn_return3);
        Button borrow4 = findViewById(R.id.btn_borrow4);
        Button return4 = findViewById(R.id.btn_return4);

        Button btnReset = findViewById(R.id.btn_reset);
        Button btnClearLog = findViewById(R.id.btn_clear_log);

        refreshInfo();

        //===== 红楼梦 =====
        borrow1.setOnClickListener(v -> {
            if (book1Stock > 0) {
                book1Stock--;
                totalBorrowTimes++;
                addLog("✅ 借阅《红楼梦》");
                Toast.makeText(this, "借阅《红楼梦》成功", Toast.LENGTH_SHORT).show();
            } else {
                addLog("❌ 尝试借阅《红楼梦》，库存不足");
                Toast.makeText(this, "《红楼梦》库存不足，无法借阅", Toast.LENGTH_SHORT).show();
            }
            refreshInfo();
        });
        return1.setOnClickListener(v -> {
            if(book1Stock < book1Max){
                book1Stock++;
                totalReturnTimes++;
                addLog("✅ 归还《红楼梦》");
                Toast.makeText(this, "归还《红楼梦》成功", Toast.LENGTH_SHORT).show();
            }else{
                addLog("❌ 尝试归还《红楼梦》，无借出图书");
                Toast.makeText(this, "没有借出的《红楼梦》可以归还", Toast.LENGTH_SHORT).show();
            }
            refreshInfo();
        });

        //===== 西游记 =====
        borrow2.setOnClickListener(v -> {
            if (book2Stock > 0) {
                book2Stock--;
                totalBorrowTimes++;
                addLog("✅ 借阅《西游记》");
                Toast.makeText(this, "借阅《西游记》成功", Toast.LENGTH_SHORT).show();
            } else {
                addLog("❌ 尝试借阅《西游记》，库存不足");
                Toast.makeText(this, "《西游记》库存不足，无法借阅", Toast.LENGTH_SHORT).show();
            }
            refreshInfo();
        });
        return2.setOnClickListener(v -> {
            if(book2Stock < book2Max){
                book2Stock++;
                totalReturnTimes++;
                addLog("✅ 归还《西游记》");
                Toast.makeText(this, "归还《西游记》成功", Toast.LENGTH_SHORT).show();
            }else{
                addLog("❌ 尝试归还《西游记》，无借出图书");
                Toast.makeText(this, "没有借出的《西游记》可以归还", Toast.LENGTH_SHORT).show();
            }
            refreshInfo();
        });

        //===== 三体 =====
        borrow3.setOnClickListener(v -> {
            if (book3Stock > 0) {
                book3Stock--;
                totalBorrowTimes++;
                addLog("✅ 借阅《三体》");
                Toast.makeText(this, "借阅《三体》成功", Toast.LENGTH_SHORT).show();
            } else {
                addLog("❌ 尝试借阅《三体》，库存不足");
                Toast.makeText(this, "《三体》库存不足，无法借阅", Toast.LENGTH_SHORT).show();
            }
            refreshInfo();
        });
        return3.setOnClickListener(v -> {
            if(book3Stock < book3Max){
                book3Stock++;
                totalReturnTimes++;
                addLog("✅ 归还《三体》");
                Toast.makeText(this, "归还《三体》成功", Toast.LENGTH_SHORT).show();
            }else{
                addLog("❌ 尝试归还《三体》，无借出图书");
                Toast.makeText(this, "没有借出的《三体》可以归还", Toast.LENGTH_SHORT).show();
            }
            refreshInfo();
        });

        //===== 平凡的世界【新增】 =====
        borrow4.setOnClickListener(v -> {
            if (book4Stock > 0) {
                book4Stock--;
                totalBorrowTimes++;
                addLog("✅ 借阅《平凡的世界》");
                Toast.makeText(this, "借阅《平凡的世界》成功", Toast.LENGTH_SHORT).show();
            } else {
                addLog("❌ 尝试借阅《平凡的世界》，库存不足");
                Toast.makeText(this, "《平凡的世界》库存不足，无法借阅", Toast.LENGTH_SHORT).show();
            }
            refreshInfo();
        });
        return4.setOnClickListener(v -> {
            if(book4Stock < book4Max){
                book4Stock++;
                totalReturnTimes++;
                addLog("✅ 归还《平凡的世界》");
                Toast.makeText(this, "归还《平凡的世界》成功", Toast.LENGTH_SHORT).show();
            }else{
                addLog("❌ 尝试归还《平凡的世界》，无借出图书");
                Toast.makeText(this, "没有借出的《平凡的世界》可以归还", Toast.LENGTH_SHORT).show();
            }
            refreshInfo();
        });

        //重置库存
        btnReset.setOnClickListener(v -> {
            book1Stock = book1Max;
            book2Stock = book2Max;
            book3Stock = book3Max;
            book4Stock = book4Max;
            addLog("🔄 执行【全部库存重置】");
            Toast.makeText(this, "库存已全部重置为初始状态", Toast.LENGTH_SHORT).show();
            refreshInfo();
        });

        //清空日志
        btnClearLog.setOnClickListener(v -> {
            tvLog.setText("");
            Toast.makeText(this, "操作日志已清空", Toast.LENGTH_SHORT).show();
        });
    }

    //添加日志，附带时间
    private void addLog(String msg){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String time = sdf.format(new Date());
        String oldText = tvLog.getText().toString();
        tvLog.setText(oldText + "["+time+"] " + msg + "\n");
    }

    //刷新所有UI
    private void refreshInfo() {
        tvBook1Count.setText("库存：" + book1Stock + "本");
        tvBook2Count.setText("库存：" + book2Stock + "本");
        tvBook3Count.setText("库存：" + book3Stock + "本");
        tvBook4Count.setText("库存：" + book4Stock + "本");

        tvBook1Status.setText(book1Stock>0 ? "状态：可借阅" : "状态：已借完");
        tvBook2Status.setText(book2Stock>0 ? "状态：可借阅" : "状态：已借完");
        tvBook3Status.setText(book3Stock>0 ? "状态：可借阅" : "状态：已借完");
        tvBook4Status.setText(book4Stock>0 ? "状态：可借阅" : "状态：已借完");

        int totalAll = book1Max + book2Max + book3Max + book4Max;
        int remain = book1Stock + book2Stock + book3Stock + book4Stock;
        int borrowed = totalAll - remain;

        tvTotalInfo.setText("馆内总藏书：" + totalAll + "本\n当前已借出：" + borrowed + "本\n累计借阅："+totalBorrowTimes+"次｜累计归还："+totalReturnTimes+"次");
    }

    //返回键退出弹窗
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("退出提示")
                .setMessage("确定要退出图书馆管理系统吗？未保存的操作记录将会丢失！")
                .setPositiveButton("确定退出", (dialog, which) -> {
                    super.onBackPressed();
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
