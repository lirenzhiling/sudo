package com.example.homework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.homework.tools.IntArrayWrapper;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.Arrays;
import java.util.List;

public class LevelActivity extends AppCompatActivity {

    private MZBannerView galleryRecycle;
    private Button left,right,play;
    int[][][] Sudoku = {
            {
                    {5, 0, 0, 6, 7, 8, 9, 1, 2},
                    {6, 7, 2, 1, 9, 5, 3, 4, 8},
                    {1, 9, 8, 3, 4, 2, 5, 6, 7},
                    {8, 5, 9, 7, 6, 1, 4, 2, 3},
                    {4, 2, 6, 8, 5, 3, 7, 9, 1},
                    {7, 1, 3, 9, 2, 4, 8, 5, 6},
                    {9, 6, 1, 5, 3, 7, 2, 8, 4},
                    {2, 8, 7, 4, 1, 9, 6, 3, 5},
                    {3, 4, 5, 2, 8, 6, 1, 7, 9}
            },
            {
                    {0, 0, 0, 0, 7, 0, 0, 0, 6},
                    {2, 0, 4, 0, 5, 1, 0, 7, 0},
                    {8, 0, 0, 0, 0, 2, 0, 1, 9},
                    {5, 0, 0, 6, 0, 0, 2, 0, 0},
                    {0, 0, 1, 0, 0, 4, 9, 5, 0},
                    {0, 0, 8, 0, 0, 0, 0, 0, 1},
                    {3, 7, 0, 1, 0, 0, 0, 0, 5},
                    {0, 1, 0, 3, 0, 0, 7, 0, 2},
                    {9, 0, 0, 0, 2, 0, 1, 0, 0}
            },
            {
                    {0, 7, 2, 1, 0, 0, 4, 0, 0},
                    {0, 1, 0, 8, 0, 4, 0, 2, 0},
                    {0, 4, 0, 3, 0, 0, 5, 9, 0},
                    {1, 0, 0, 9, 0, 0, 0, 4, 7},
                    {3, 0, 0, 5, 0, 6, 0, 0, 9},
                    {4, 2, 0, 0, 3, 0, 0, 0, 8},
                    {0, 5, 1, 0, 0, 7, 0, 3, 0},
                    {0, 9, 0, 2, 0, 3, 0, 6, 0},
                    {0, 0, 4, 0, 0, 9, 8, 7, 0}
            },
            {
                    {8, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 3, 6, 0, 0, 0, 0, 0},
                    {0, 7, 0, 0, 9, 0, 2, 0, 0},
                    {0, 5, 0, 0, 0, 7, 0, 0, 0},
                    {0, 0, 0, 0, 4, 5, 7, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 3, 0},
                    {0, 0, 1, 0, 0, 0, 0, 6, 8},
                    {0, 0, 8, 5, 0, 0, 0, 1, 0},
                    {0, 9, 0, 0, 0, 0, 4, 0, 0}
            },
            {
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 3, 0, 0, 0, 0},
                    {0, 0, 2, 0, 0, 0, 1, 0, 0},
                    {0, 6, 0, 5, 0, 4, 0, 3, 0},
                    {0, 0, 0, 0, 7, 0, 0, 0, 0},
                    {0, 1, 0, 9, 0, 2, 0, 8, 0},
                    {0, 0, 7, 0, 0, 0, 6, 0, 0},
                    {0, 0, 0, 0, 8, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0}
            },


    };
    private List<Integer> galleries = Arrays.asList(new Integer[]{R.mipmap.one,R.mipmap.two,R.mipmap.three,R.mipmap.four ,
            R.mipmap.fine});

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.level);
        left=findViewById(R.id.left);
        right=findViewById(R.id.right);
        play=findViewById(R.id.play);


        galleryRecycle = findViewById(R.id.Gallery);

        galleryRecycle.setPages(galleries, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        left.setOnClickListener(v->{
            ViewPager a=galleryRecycle.getViewPager();
            a.setCurrentItem(a.getCurrentItem()-1);//int b=a.getCurrentItem();//当前关卡数
        });
        right.setOnClickListener(v->{
            ViewPager a=galleryRecycle.getViewPager();
            a.setCurrentItem(a.getCurrentItem()+1);//int b=a.getCurrentItem();//当前关卡数
        });
        play.setOnClickListener(v->{
            ViewPager a=galleryRecycle.getViewPager();
            int b=a.getCurrentItem();
            try {
                Intent intent = new Intent(LevelActivity.this, MainActivity.class);
                intent.putExtra("array", new IntArrayWrapper(Sudoku[b]));
                startActivity(intent);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }

    public class BannerViewHolder implements MZViewHolder<Integer> {
        ImageView imageView;
        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.carousel,null);
            imageView = (ImageView) view.findViewById(R.id.galleryImage);
            return view;
        }
        @Override
        public void onBind(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        galleryRecycle.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        galleryRecycle.start();
    }

}
