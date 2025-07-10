package com.example.homework.tools;

import android.os.Parcel;
import android.os.Parcelable;

public class IntArrayWrapper implements Parcelable {
    private int[][] data;

    public IntArrayWrapper(int[][] data) {
        this.data = data;
    }

    protected IntArrayWrapper(Parcel in) {
        int rows = in.readInt();
        data = new int[rows][];
        for (int i = 0; i < rows; i++) {
            data[i] = in.createIntArray(); // 读取每行的一维数组
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(data.length); // 写入行数
        for (int[] row : data) {
            dest.writeIntArray(row); // 逐行写入
        }
    }

    public static final Creator<IntArrayWrapper> CREATOR = new Creator<IntArrayWrapper>() {
        @Override
        public IntArrayWrapper createFromParcel(Parcel in) {
            return new IntArrayWrapper(in);
        }

        @Override
        public IntArrayWrapper[] newArray(int size) {
            return new IntArrayWrapper[size];
        }
    };

    @Override
    public int describeContents() {
        return 0; // 默认返回0，表示没有特殊内容需要描述
    }
    // Getter
    public int[][] getData() {
        return data;
    }
}