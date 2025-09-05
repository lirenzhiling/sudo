package com.example.homework;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homework.tools.IntArrayWrapper;

public class MainActivity extends AppCompatActivity {
    int[][] easySudoku = {
            {8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 6, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 9, 0, 2, 0, 0},
            {0, 5, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 4, 5, 7, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 3, 0},
            {0, 0, 1, 0, 0, 0, 0, 6, 8},
            {0, 0, 8, 5, 0, 0, 0, 1, 0},
            {0, 9, 0, 0, 0, 0, 4, 0, 0}
    };
    int[][] mySudoku=easySudoku;

    int myrow=0;
    int mycol=0;
    int mygrouprow;
    int mygroupcol;
    private int buttoni=1;
    private boolean isPlaying = false;

    private long pausedTime = 0; // 记录暂停时的累计时间

//Service的使用

    SudokuService mService;
    boolean mBound = false;
    //1.获取Binder
    private SudokuService.SudokuBinder TimeBinder;
    //2.获取connection
    private ServiceConnection connection = new ServiceConnection() {

        //这两个方法会在活动与服务成功绑定以及解除绑定前后调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //向下转型获得mBinder
            TimeBinder = (SudokuService.SudokuBinder) service;
            TimeBinder.getTime();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        // 绑定服务
        Intent intent = new Intent(this, SudokuService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        mBound = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //解绑服务
        unbindService(connection);
        mBound = false;
    }

    public void getSumTime() {
        if (mBound) {
            int num = TimeBinder.getTime();
            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
        }
    }

//Service的使用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("array")) {
            IntArrayWrapper wrapper = intent.getParcelableExtra("array");
            if (wrapper != null) {
                easySudoku = wrapper.getData();
                mySudoku=easySudoku;
            } else {
                Log.e("MainActivity", "Parcelable data is null");
            }
        } else {
            Log.e("MainActivity", "Intent or extra is null");
            // 处理Intent为null的情况（如初始化默认值或退出Activity）
        }
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        GridLayout gridLayout = findViewById(R.id.sudoku_grid);
        GridLayout buttons=findViewById(R.id.button_grid);
        ImageView btnControl = findViewById(R.id.btn_control);
        Chronometer chronometer = findViewById(R.id.chronometer);
        gridLayout.setColumnCount(9);
        gridLayout.setRowCount(9);
        chronometer.start();

        chronometer.setOnClickListener(v->{
            getSumTime();
            Log.d("10000", "onStart: ");
        });

        // 动态生成81个格子
        for (int row = 0; row < 9; row++) {
            int grouprow=row/3;

            for (int col = 0; col < 9; col++) {
                int groupcol=col/3;
                TextView cell = new TextView(this);

                // 设置布局参数（强制正方形）
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // 等效layout_columnWeight="1"
                params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

                cell.setLayoutParams(params);
                cell.setText("  ");
                cell.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                cell.setGravity(Gravity.CENTER); // 居中显示
                if (grouprow==groupcol || grouprow+groupcol==2){
                    cell.setBackgroundResource(R.drawable.color1); // 单元格内边框
                }else {
                    cell.setBackgroundResource(R.drawable.color2); // 单元格内边框
                }


                // 测试数据：显示位置索引（实际使用时替换为数独数字）
                if (easySudoku[row][col]!=0){
                    cell.setText(String.valueOf(easySudoku[row][col]));
                }else {
                    // 为0的格子设置点击监听器
                    cell.setTextColor(0xFFff1b20);
                    cell.setTag(new int[]{row, col}); // 保存位置信息
                    cell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 获取点击的位置
                            int[] position = (int[]) v.getTag();

                            // 显示提示信息
//                            Toast.makeText(MainActivity.this,
//                                    "点击了空白格子 (" + (myrow+1) + "," + (mycol+1) + ")",
//                                    Toast.LENGTH_SHORT).show();
                            cell.setBackgroundResource(R.drawable.color_bonus);
                            if (myrow>-1&&mycol>-1){
                                View before=gridLayout.getChildAt(myrow*9+mycol);
                                mygrouprow=myrow/3;
                                mygroupcol=mycol/3;
                                if (mygrouprow==mygroupcol || mygrouprow+mygroupcol==2){
                                    before.setBackgroundResource(R.drawable.color1); // 单元格内边框
                                }else {
                                    before.setBackgroundResource(R.drawable.color2); // 单元格内边框
                                }
                                myrow = position[0];
                                mycol = position[1];
                            }
                            else {
                                myrow = position[0];
                                mycol = position[1];
                            }
                            // 这里可以添加输入数字的逻辑
                            // 例如弹出一个对话框让用户选择数字
                            // 然后更新easySudoku数组和界面显示
                        }
                    });
                    }

                gridLayout.addView(cell);
            }
        }


        //让按钮能够响应
        for (buttoni=0;buttoni<9;buttoni++){
            buttons.getChildAt(buttoni).setTag(Integer.toString(buttoni+1));
            buttons.getChildAt(buttoni).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    View mycell=gridLayout.getChildAt(myrow*9+mycol);
                    if (mycell instanceof TextView) {
                        ((TextView) mycell).setText((String) v.getTag());
                        mySudoku[myrow][mycol]=Integer.parseInt((String)v.getTag());
//                        Log.d("111", "onClick: "+buttoni);
                    }
                }

            });
        }
        buttons.getChildAt(9).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isSudokuSolved(mySudoku)){
                    // 显示对话框
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("恭喜通关")
                            .setNegativeButton("确定", (dialog, which) -> {
                                finish();
                            })
                            .setPositiveButton("退出", (dialog, which) -> {
                                finish();
                            })
                            .show();
                }else {
                    // 显示对话框
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("答案错误")
                            .setNegativeButton("确定", (dialog, which) -> {
                                Toast.makeText(v.getContext(), String.valueOf(mySudoku[0][1]), Toast.LENGTH_SHORT).show();

                            })
                            .setPositiveButton("退出", (dialog, which) -> {
                                finish();
                            })
                            .show();
                }
            }

        });


        //工具栏按钮
        btnControl.setOnClickListener(v -> {
            isPlaying = false;
            // 切换图片（直接修改src或使用状态列表）
            btnControl.setImageResource(R.drawable.stop);
            // 暂停计时
            if (!isPlaying) {
                pausedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.stop();
            }

            // 显示对话框
            new AlertDialog.Builder(this)
                    .setTitle("暂停游戏中")
                    .setMessage("要继续游戏吗？")
                    .setNegativeButton("确定", (dialog, which) -> {
                        btnControl.setImageResource(R.drawable.play);
                        isPlaying = true;
                        // 继续计时
                        if (isPlaying) {
                            chronometer.setBase(SystemClock.elapsedRealtime() - pausedTime);
                            chronometer.start();
                        }
                    })
                    .setPositiveButton("退出", (dialog, which) -> {
                        finish();
                    })
                    .show();
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isChangingConfigurations()) {
            // 应用进入后台，执行暂停逻辑
            ImageView btnControl = findViewById(R.id.btn_control);
            Chronometer chronometer = findViewById(R.id.chronometer);
            isPlaying = false;
            // 切换图片（直接修改src或使用状态列表）
            btnControl.setImageResource(R.drawable.stop);
            // 暂停计时
            if (!isPlaying) {
                pausedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.stop();
            }

            // 显示对话框
            new AlertDialog.Builder(this)
                    .setTitle("暂停游戏中")
                    .setMessage("要继续游戏吗？")
                    .setNegativeButton("确定", (dialog, which) -> {
                        btnControl.setImageResource(R.drawable.play);
                        isPlaying = true;
                        // 继续计时
                        if (isPlaying) {
                            chronometer.setBase(SystemClock.elapsedRealtime() - pausedTime);
                            chronometer.start();
                        }
                    })
                    .setPositiveButton("退出", (dialog, which) -> {
                       finish();
                    })
                    .show();
        }
    }

    public boolean isSudokuSolved(int[][] board) {
        // 首先检查棋盘是否还有空格（0）
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return false; // 还有空格，肯定未完成
                }
            }
        }

        // 检查所有行是否有重复
        for (int row = 0; row < 9; row++) {
            boolean[] seen = new boolean[10]; // 索引1-9对应数字1-9，索引0不用
            for (int col = 0; col < 9; col++) {
                int num = board[row][col];
                if (num < 1 || num > 9) {
                    return false; // 数字超出有效范围
                }
                if (seen[num]) {
                    return false; // 当前行发现重复数字
                }
                seen[num] = true;
            }
        }

        // 检查所有列是否有重复
        for (int col = 0; col < 9; col++) {
            boolean[] seen = new boolean[10];
            for (int row = 0; row < 9; row++) {
                int num = board[row][col];
                if (num < 1 || num > 9) {
                    return false;
                }
                if (seen[num]) {
                    return false; // 当前列发现重复数字
                }
                seen[num] = true;
            }
        }

        // 检查所有3x3宫格是否有重复
        for (int box = 0; box < 9; box++) {
            boolean[] seen = new boolean[10];
            // 计算当前宫格起始行和列
            int startRow = (box / 3) * 3;
            int startCol = (box % 3) * 3;
            for (int rowOffset = 0; rowOffset < 3; rowOffset++) {
                for (int colOffset = 0; colOffset < 3; colOffset++) {
                    int actualRow = startRow + rowOffset;
                    int actualCol = startCol + colOffset;
                    int num = board[actualRow][actualCol];
                    if (num < 1 || num > 9) {
                        return false;
                    }
                    if (seen[num]) {
                        return false; // 当前宫格发现重复数字
                    }
                    seen[num] = true;
                }
            }
        }

        // 所有检查都通过，通关成功！
        return true;
    }
}


