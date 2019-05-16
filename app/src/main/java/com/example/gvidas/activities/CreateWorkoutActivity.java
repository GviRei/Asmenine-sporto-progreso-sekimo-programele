package com.example.gvidas.activities;

import android.app.Application;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gvidas.database.Exercise;
import com.example.gvidas.database.ExerciseInPlan;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.database.Profile;
import com.example.gvidas.database.TrainingExercise;
import com.example.gvidas.database.Workout;
import com.example.gvidas.sportapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateWorkoutActivity extends AppCompatActivity {

    Spinner spinner;
    EditText workoutName;
    int sets = 0;
    int reps = 0;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.exerciseSpinner);
        workoutName = (EditText) findViewById(R.id.workoutNameTextField);


        dbHandler = new MyDBHandler(this, null, null, 1);
        String[] spinnerLists = dbHandler.loadExercise();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateWorkoutActivity.this, android.R.layout.simple_spinner_item, spinnerLists);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // spinner.setOnItemSelectedListener(this);

    }


    public void increaseSets(View view) {
        sets = sets + 1;
        ;
        displaySets(sets);
    }

    public void decreaseSets(View view) {
        sets = sets - 1;
        ;
        displaySets(sets);
    }

    public void increaseReps(View view) {
        reps = reps + 1;
        ;
        displayReps(reps);
    }

    public void decreaseReps(View view) {
        reps = reps - 1;
        ;
        displayReps(reps);
    }

    public int getSets() {
        EditText sets = (EditText) findViewById(R.id.sets);
        String text = sets.getText().toString();
        int number = Integer.parseInt(text);
        return number;
    }

    public int getReps() {
        EditText reps = (EditText) findViewById(R.id.reps);
        String text = reps.getText().toString();
        int number = Integer.parseInt(text);
        return number;
    }

    public void displaySets(int number) {
        EditText setsView = (EditText) findViewById(R.id.sets);
        setsView.setText(String.valueOf(number));
    }

    public void displayReps(int number) {
        EditText repsView = (EditText) findViewById(R.id.reps);
        repsView.setText(String.valueOf(number));
    }


    //Add workout plan name to database
    public void addWorkoutToDatabase(View view) {
        // EditText workoutName = (EditText) findViewById(R.id.workoutNameTextField);
        Random randId = new Random();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String wName = workoutName.getText().toString(); //getting workout plan name
        int id = randId.nextInt(1000 + 1);
        Workout workout = new Workout(id, wName);
        dbHandler.addWorkout(workout);
    }

    public void loadWorkout(View view) {
        TextView list = (TextView) findViewById(R.id.workoutList);
        String wName = workoutName.getText().toString();
        int workoutPlanId = dbHandler.getWorkoutPlanId(wName);
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        /*String str = dbHandler.loadWorkoutPlan(workoutPlanId);
        String[] splitted = str.split("\\s+");*/
        list.setText(dbHandler.loadWorkoutPlan(workoutPlanId));
    }


    public void addExerciseToDatabase(View view) {
        TextView list = (TextView) findViewById(R.id.workoutList);
        String exerciseName = "";
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        exerciseName = spinner.getSelectedItem().toString();
        String wName = workoutName.getText().toString();
        int exerciseId = dbHandler.getExerciseId(exerciseName);
        int workoutPlanId = dbHandler.getWorkoutPlanId(wName);
        int sets = getSets();
        int reps = getReps();
        TrainingExercise exercise = new TrainingExercise(workoutPlanId, exerciseId, sets, reps);
        dbHandler.addExerciseToWorkout(exercise);
        list.setText(String.valueOf(workoutPlanId));
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
