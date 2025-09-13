package com.example.homework;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity  extends AppCompatActivity {
    private String savedUsername="1";
    private MediaPlayer mediaPlayer;
    private ImageView musicplay;
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
            if (!savedUsername.isEmpty()){
                // 创建 Intent 跳转到 SecondActivity
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        });

        ImageView rank = findViewById(R.id.img_rank);
        rank.setOnClickListener(v -> {
            // 创建 Intent 跳转到 SecondActivity
            Intent intent = new Intent(HomeActivity.this, RankingActivity.class);
            startActivity(intent);
        });

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
        musicplay= findViewById(R.id.music);
        musicplay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause(); // 暂停播放
                    musicplay.setImageResource(R.drawable.musicstop);
                }else if(mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start(); // 开始播放
                    musicplay.setImageResource(R.drawable.music);
                }
            }

        });

        SharedPreferences sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE); // "LoginInfo" 是保存登录信息时使用的文件名，请确保与登录时使用的文件名一致
        savedUsername = sharedPreferences.getString("statususername", ""); // "username" 是存储用户名的键，第二个参数是默认值（当找不到"username"键时返回）

        TextView tvNickname = findViewById(R.id.tv_nickname); // 请确保布局文件中有此ID的TextView

        if (!savedUsername.isEmpty()) {
            tvNickname.setText(savedUsername); // 用户名存在，设置TextView的文本
        }
        tvNickname.setOnClickListener(v -> {
            if (!savedUsername.isEmpty()) {
                // 显示对话框
                new AlertDialog.Builder(this)
                        .setTitle("要退出登录吗？")
                        .setNegativeButton("确定", (dialog, which) -> {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("statususername");
                            savedUsername="";
                            editor.apply();
                            tvNickname.setText("未登录");
                        })
                        .setPositiveButton("取消", (dialog, which) -> {

                        })
                        .show();
            } else {
                // 创建 Intent 跳转到 SecondActivity
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
