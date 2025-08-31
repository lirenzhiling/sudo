package com.example.homework;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnRegister;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.register);

        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);

        // 检查是否有保存的凭据
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
            etUsername.setText(savedUsername);
            etPassword.setText(savedPassword);
        }

        // 注册按钮点击事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 模拟登录验证
                if (login(username, password)) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply(); // 异步提交

                    Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    // 跳转到登录界面...
                } else {
                    Toast.makeText(RegisterActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // 模拟登录验证
    private boolean login(String username, String password) {
        // 这里应该是你的实际登录逻辑，比如与服务器验证
        return "admin".equals(username) && "123456".equals(password);
    }
}
