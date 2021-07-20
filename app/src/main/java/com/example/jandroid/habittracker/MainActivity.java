package com.example.jandroid.habittracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jandroid.habittracker.data.HabitContract;
import com.example.jandroid.habittracker.data.HabitDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private HabitDBHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Editor.class);
                startActivity(intent);
            }
        });
        mDbHelper = new HabitDBHelper(this);
        displayDatabaseInfo();
    }
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
    private void displayDatabaseInfo() {

        Cursor cursor = readAllHabits();

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            displayView.setText("The activities table contains " + cursor.getCount() + " activities.\n\n");
            displayView.append(HabitContract.HabitEntry._ID + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_DESCRTIPTION + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_TYPE + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_START_TIME + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_END_TIME + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_LOCATION + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int descriptionColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_DESCRTIPTION);
            int categoryColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_TYPE);
            int momentColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_START_TIME);
            int durationColumnIndex = cursor.getColumnIndex( HabitContract.HabitEntry.COLUMN_HABIT_END_TIME);
            int placeColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION );


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the element
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);
                int currentCategory = cursor.getInt(categoryColumnIndex);
                String currentStartTime = cursor.getString(momentColumnIndex);
                String currentEndTime = cursor.getString(durationColumnIndex);
                String currentPlace = cursor.getString(placeColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentDescription + " - " +
                        currentCategory + " - " +
                        currentStartTime + " - " +
                        currentEndTime + " - " +
                        currentPlace));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    public Cursor readAllHabits() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_DESCRTIPTION,
                HabitContract.HabitEntry.COLUMN_HABIT_TYPE,
                HabitContract.HabitEntry.COLUMN_HABIT_START_TIME,
                HabitContract.HabitEntry.COLUMN_HABIT_END_TIME,
                HabitContract.HabitEntry.COLUMN_HABIT_LOCATION };


        // Perform a query on the activities table
        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        return cursor;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void insertHabit(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DESCRTIPTION, "Simple activity");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_TYPE,1);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_START_TIME,"10:00");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_END_TIME,"12:00");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION,"Shia");
        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
    }
}

