package com.example.jandroid.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HabitDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG=HabitDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "habit_tracker.db";

    private static final int DATABASE_VERSION = 1;

    public HabitDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABITS_TABLE="CREATE TABLE "+ HabitContract.HabitEntry.TABLE_NAME+"("
                +HabitContract.HabitEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                HabitContract.HabitEntry.COLUMN_HABIT_DESCRTIPTION+" TEXT NOT NULL, "+
                HabitContract.HabitEntry.COLUMN_HABIT_TYPE+ " INT NOT NULL, "+
                HabitContract.HabitEntry.COLUMN_HABIT_START_TIME+" TEXT NOT NULL, "+
                HabitContract.HabitEntry.COLUMN_HABIT_END_TIME+ " TEXT NOT NULL, "+
                HabitContract.HabitEntry.COLUMN_HABIT_LOCATION+ " TEXT);";
        db.execSQL(SQL_CREATE_HABITS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
