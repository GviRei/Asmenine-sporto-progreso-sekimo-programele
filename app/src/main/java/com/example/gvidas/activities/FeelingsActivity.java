package com.example.gvidas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gvidas.database.Feelings;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.database.WorkoutDone;
import com.example.gvidas.sportapplication.R;

import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Date;
import java.util.Random;

public class FeelingsActivity extends AppCompatActivity {

    private TextView tv;
    public int workoutID;
    public String workoutName;
    Spinner tirednessSpinner, energySpinner;
    Button saveButton, loadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tirednessSpinner = (Spinner) findViewById(R.id.tirednessSpinner);
        energySpinner = (Spinner) findViewById(R.id.energySpinner);
        saveButton = (Button) findViewById(R.id.saveWorkoutToDatabase);
        loadButton = (Button) findViewById(R.id.loadWorkoutData);
        tv = (TextView) findViewById(R.id.tv);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            workoutID = extras.getInt("workoutID");
            workoutName = extras.getString("workoutName");
        }
        loadSpinners();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        String str = dbHandler.getWorkoutIDString(workoutID);

        //tv.setText(dbHandler.loadWorkout(workoutID));
        tv.setText(str);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = getCount();

                saveFeelingsToDatabase();
                tv.setText(String.valueOf(i));
                Intent intent = new Intent(FeelingsActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllDataFromWorkout();
            }
        });


    }

    public void loadAllDataFromWorkout(){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        tv.setText(dbHandler.loadAllDataFromWorkout(workoutID));
    }

    public void loadSpinners() {
        String[] tirednessList = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] energyList = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(FeelingsActivity.this, android.R.layout.simple_spinner_item, tirednessList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tirednessSpinner.setAdapter(spinnerAdapter);
        tirednessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(FeelingsActivity.this, android.R.layout.simple_spinner_item, tirednessList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        energySpinner.setAdapter(spinnerAdapter);
        energySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void saveFeelingsToDatabase() {
        Feelings feelings;
        WorkoutDone done = new WorkoutDone();
        int tiredness = Integer.parseInt(tirednessSpinner.getSelectedItem().toString());
        int energy = Integer.parseInt(energySpinner.getSelectedItem().toString());

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = format.format(today);
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int count = getCount();
        String str = dbHandler.getWorkoutIDString(workoutID);
        String[] ids = str.split(" ");
        for (int i = 0; i < count; i++) {
            {
                //int id = dbHandler.getWorkoutID(workoutID);
                feelings = new Feelings(Integer.parseInt(ids[i]), workoutID, tiredness, energy, todayDate);
                dbHandler.addFeelingsToDatabase(feelings);
            }
        }
    }

    public int getCount() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int workoutPlanId = dbHandler.getWorkoutPlanId(workoutName);
        String str = dbHandler.loadWorkoutPlanOnlyExercises(workoutPlanId);
        String[] exercises = str.split(",");
        int count = exercises.length;
        return count;
    }

}
