package com.example.homework;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.homework.tools.UserDbHelper;
import com.google.android.material.imageview.ShapeableImageView;

import android.content.SharedPreferences;


public class UserActivity extends AppCompatActivity {
    private String savedUsername = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        EdgeToEdge.enable(this);


        // 仅初始化控件，无多余逻辑
        Button btnLogout = findViewById(R.id.btn_logout);
        TextView username = findViewById(R.id.tv_username);
        TextView level = findViewById(R.id.tv_level);

        UserDbHelper dbHelper = new UserDbHelper(this); // 'this' 是 Context，如果在 Activity 中
        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE); // "LoginInfo" 是保存登录信息时使用的文件名，请确保与登录时使用的文件名一致
        savedUsername = sharedPreferences.getString("statususername", ""); // "username" 是存储用户名的键，第二个参数是默认值（当找不到"username"键时返回）
        int userLevel = dbHelper.getUserLevelByUsername(savedUsername);
        username.setText(savedUsername);
        level.setText("通关数："+userLevel+"关");

        btnLogout.setOnClickListener(v -> {
            // 显示对话框
            new AlertDialog.Builder(this)
                    .setTitle("要退出登录吗？")
                    .setNegativeButton("确定", (dialog, which) -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("statususername");
                        savedUsername="";
                        editor.apply();
                        Intent intent = new Intent(UserActivity.this, HomeActivity.class);
                        startActivity(intent);
                    })
                    .setPositiveButton("取消", (dialog, which) -> {

                    })
                    .show();

        });
    }
}
