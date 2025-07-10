package com.example.homework;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SudokuService extends Service {
    private final IBinder binder = new SudokuBinder();
    public SudokuService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class SudokuBinder extends Binder {
        SudokuService getService() { return SudokuService.this; }
        public int getTime() {
            return 100;
        }
    }
}