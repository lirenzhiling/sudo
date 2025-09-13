package com.example.homework;

import static com.example.homework.tools.UserDbHelper.COLUMN_LEVEL;
import static com.example.homework.tools.UserDbHelper.COLUMN_USERNAME;

import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ListView;

import com.example.homework.tools.RankingAdapter;
import com.example.homework.tools.RankingItem;
import com.example.homework.tools.UserDbHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);//全屏
        setContentView(R.layout.ranking);
        ListView listView = findViewById(R.id.ranking_list);
        List<RankingItem> rankingData = new ArrayList<>();
        UserDbHelper dbHelper = new UserDbHelper(this); // this 是你的 Context
        Cursor cursor = dbHelper.getUsersRankedByLevel(); // 执行查询

        if (cursor != null && cursor.moveToFirst()) {
            int rankNumber = 1; // 排名从1开始
            do {
                // 从cursor中获取用户名和等级
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                int level = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LEVEL));
                // 创建 RankingItem 并添加到列表
                rankingData.add(new RankingItem(rankNumber, username, level));
                rankNumber++; // 排名递增
            } while (cursor.moveToNext());
            cursor.close(); // 关闭cursor
        }
        dbHelper.close(); // 关闭数据库连接

        for (int i = 0; i < rankingData.size(); i++) {
            rankingData.get(i).rank = i + 1;
        }
        RankingAdapter adapter = new RankingAdapter(this, rankingData);
        listView.setAdapter(adapter);
    }
}
