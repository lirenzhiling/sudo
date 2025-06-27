package com.example.homework;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int[][] easySudoku = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };
    int myrow=0;
    int mycol=0;
    int mygrouprow;
    int mygroupcol;
    private int buttoni=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        GridLayout gridLayout = findViewById(R.id.sudoku_grid);
        GridLayout buttons=findViewById(R.id.button_grid);
        gridLayout.setColumnCount(9);
        gridLayout.setRowCount(9);

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
                    cell.setTextColor(0xFF75E478);
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
        for (buttoni=0;buttoni<=9;buttoni++){
            buttons.getChildAt(buttoni).setTag(Integer.toString(buttoni+1));
            buttons.getChildAt(buttoni).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    View mycell=gridLayout.getChildAt(myrow*9+mycol);
                    if (mycell instanceof TextView) {
                        ((TextView) mycell).setText((String) v.getTag());
                        Log.d("111", "onClick: "+buttoni);
                    }
                }

            });
        }

    }
}
