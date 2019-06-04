package com.example.gvidas.activities.Workout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.database.TrainingExercise;
import com.example.gvidas.database.Workout;
import com.example.gvidas.sportapplication.R;

import java.util.Random;

public class CreateWorkoutActivity extends AppCompatActivity {

    Spinner spinner, spinner2;
    EditText workoutName;
    TextView info, info2;
    int sets = 0;
    int reps = 0;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner = (Spinner) findViewById(R.id.exerciseSpinner);
        spinner2 = (Spinner) findViewById(R.id.exerciseSpinner2);
        workoutName = (EditText) findViewById(R.id.workoutNameTextField);
        //info = (TextView)findViewById(R.id.addedExercises);
        setTitle("Create Workout Plan");

        dbHandler = new MyDBHandler(this, null, null, 1);



        String[] spinnerLists2 = {"Chest", "Legs", "Biceps", "Triceps", "Back", "Other"};
       // ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateWorkoutActivity.this, android.R.layout.simple_spinner_item, spinnerLists);
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(CreateWorkoutActivity.this, android.R.layout.simple_spinner_item, spinnerLists2);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(spinnerAdapter);
        spinner2.setAdapter(spinnerAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String category = spinner2.getSelectedItem().toString();
               String[] spinnerLists = dbHandler.loadExercise(category);
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateWorkoutActivity.this, android.R.layout.simple_spinner_item, spinnerLists);
                spinner.setAdapter(spinnerAdapter);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    }


    public int getSets() {
        EditText sets = (EditText) findViewById(R.id.sets);
        int number = 0;
        if(sets.getText().toString().trim().length() == 0) {
            return 0;
        } else {
        String text = sets.getText().toString();
        number = Integer.parseInt(text);
        sets.setText("");
        }
        return number;
    }

    public int getReps() {
        EditText reps = (EditText) findViewById(R.id.reps);
        int number = 0;
        if(reps.getText().toString().trim().length() == 0){
            return 0;
        } else {
            String text = reps.getText().toString();
            number = Integer.parseInt(text);
            reps.setText("");
        }
        return number;
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


    public void addExerciseToDatabase(View view) {
        String exerciseName = "";
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        exerciseName = spinner.getSelectedItem().toString();
        info = (TextView) findViewById(R.id.addedExercises);
        info2 = (TextView)findViewById(R.id.info2);
        String wName = workoutName.getText().toString();
        int exerciseId = dbHandler.getExerciseId(exerciseName);
        int workoutPlanId = dbHandler.getWorkoutPlanId(wName);
        int sets = getSets();
        int reps = getReps();
        if (sets == 0 || reps == 0) {
            Toast.makeText(CreateWorkoutActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else {
            TrainingExercise exercise = new TrainingExercise(workoutPlanId, exerciseId, sets, reps);
            dbHandler.addExerciseToWorkout(exercise);
            String plan = dbHandler.loadWorkoutPlan(workoutPlanId);
            info2.setText(wName);
            info.setText(plan);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
