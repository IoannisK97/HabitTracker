package com.example.jandroid.habittracker.data;

import android.provider.BaseColumns;

public class HabitContract {
    private HabitContract(){

    }
    public static final class HabitEntry implements BaseColumns{

        public final static String TABLE_NAME="Habits";
        public final static String ID=BaseColumns._ID;

        public final static String COLUMN_HABIT_DESCRTIPTION="description";
        public final static String COLUMN_HABIT_TYPE="type";
        public final static String COLUMN_HABIT_START_TIME="start_time";
        public final static String COLUMN_HABIT_END_TIME="end_time";
        public final static String COLUMN_HABIT_LOCATION="location";

        public final static int TYPE_SPORT=0;
        public final static int TYPE_SOCIAL=1;
        public final static int TYPE_RELIGION=2;
        public final static int TYPE_ACADEMIC=3;
        public final static int TYPE_OTHER=4;



    }

}

