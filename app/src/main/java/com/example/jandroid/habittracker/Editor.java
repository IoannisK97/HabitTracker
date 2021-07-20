package com.example.jandroid.habittracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jandroid.habittracker.data.HabitContract;
import com.example.jandroid.habittracker.data.HabitDBHelper;

public class Editor extends AppCompatActivity {
    private EditText descriptionEditText;
    private Spinner typeSpinner;
    private EditText startTime;
    private EditText endTime;
    private EditText location;

    private int type=0;

    private HabitDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        descriptionEditText = (EditText) findViewById(R.id.edit_activity_description);
        typeSpinner = (Spinner) findViewById(R.id.spinner_category);
        startTime=(EditText) findViewById(R.id.edit_start_time);
        endTime=(EditText) findViewById(R.id.edit_end_time);
        location = (EditText) findViewById(R.id.edit_location);

        setupCategorySpinner();

        mDbHelper=new HabitDBHelper(this);


    }
    private void setupCategorySpinner() {
        // Create adapter for spinners. The list options are from the String array it will use
        // the spinners will use the default layout
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_type_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapters to the spinners
        typeSpinner.setAdapter(categorySpinnerAdapter);

        // Set the integer mSelected to the constant values
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Sport")) {
                        type = HabitContract.HabitEntry.TYPE_SPORT;
                    } else if (selection.equals("Social")){
                        type = HabitContract.HabitEntry.TYPE_SOCIAL; // Spiritual
                    } else if (selection.equals("Religion")) {
                        type = HabitContract.HabitEntry.TYPE_RELIGION; // Intellectual
                    } else if (selection.equals("Academic")) {
                        type = HabitContract.HabitEntry.TYPE_ACADEMIC; // Manual
                    } else {
                        type = HabitContract.HabitEntry.TYPE_OTHER; // Social
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type = 0;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertHabit();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void insertHabit(){
        String descriptionString = descriptionEditText.getText().toString().trim();
        String startString=startTime.getText().toString().trim();
        String endString=endTime.getText().toString().trim();
        String locationString = location.getText().toString().trim();

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DESCRTIPTION, descriptionString);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_TYPE, type);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_START_TIME, startString);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_END_TIME, endString);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_LOCATION, locationString);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence text;
        if (newRowId != -1) {
            text = "Activity saved with Id: " + newRowId;
        }
        else {
            text = "Error with saving activity";
        }
        Toast.makeText(context, text, duration).show();
    }
}