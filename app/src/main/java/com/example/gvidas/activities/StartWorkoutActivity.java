package com.example.gvidas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

import java.util.Random;

public class StartWorkoutActivity extends AppCompatActivity {

    MyDBHandler dbHandler;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.workoutPlanSpinner);


        dbHandler = new MyDBHandler(this, null, null, 1);
        String[] spinnerLists = dbHandler.loadWorkoutPlan2();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(StartWorkoutActivity.this, android.R.layout.simple_spinner_item, spinnerLists);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadWorkout();
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void startWorkout(View view) {
        String workoutName = spinner.getSelectedItem().toString();
        Random randId = new Random();
        int workoutID = randId.nextInt(1000+1);
        Intent intent = new Intent(StartWorkoutActivity.this, WorkoutActivity.class);
        intent.putExtra("key", workoutName);
        intent.putExtra("workoutID", workoutID);
        startActivity(intent);
    }

    public void loadWorkout() {
        String wName = "";
        TextView workoutPlanTextView = (TextView) findViewById(R.id.workoutPlanTextView);
        wName = spinner.getSelectedItem().toString();

        int workoutPlanId = dbHandler.getWorkoutPlanId(wName);
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        /*String str = dbHandler.loadWorkoutPlan(workoutPlanId);
        String[] splitted = str.split("\\s+");*/
        workoutPlanTextView.setText(dbHandler.loadWorkoutPlan(workoutPlanId));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            //ends activity
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
