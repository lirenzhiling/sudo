package com.example.homework;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);

        // 找到按钮并设置点击事件
        TextView start = findViewById(R.id.start);
        start.setOnClickListener(v -> {
            // 创建 Intent 跳转到 SecondActivity
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        TextView choose = findViewById(R.id.choose);
        choose.setOnClickListener(v -> {
            // 创建 Intent 跳转到 SecondActivity
            Intent intent = new Intent(HomeActivity.this, LevelActivity.class);
            startActivity(intent);
        });

        TextView people = findViewById(R.id.people);
        people.setOnClickListener(v -> {
            // 创建 Intent 跳转到 SecondActivity
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
