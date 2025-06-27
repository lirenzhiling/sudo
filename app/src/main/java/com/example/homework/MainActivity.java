package com.example.homework;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        GridLayout gridLayout = findViewById(R.id.sudoku_grid);
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
                }

                gridLayout.addView(cell);
            }
        }
//        gridLayout.setBackground(R.drawable.big_border);
    }
}