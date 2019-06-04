package com.example.gvidas.activities.Workout;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gvidas.database.Exercise;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

import java.util.Random;

public class CreateExerciseActivity extends AppCompatActivity {

    EditText exerciseName;
    Spinner exerciseCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        exerciseName = (EditText) findViewById(R.id.exerciseNameTextField);
        exerciseCategory = (Spinner) findViewById(R.id.exerciseCategoryTextField);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Create Exercise");


        String[] spinnerLists = {"Chest", "Legs", "Biceps", "Triceps", "Back", "Other"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateExerciseActivity.this, android.R.layout.simple_spinner_item, spinnerLists);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseCategory.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    public void addExercise(View view) {
        Random randId = new Random();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int id = randId.nextInt(1000 + 1);
        String name = exerciseName.getText().toString();
        String category = exerciseCategory.getSelectedItem().toString();
        Exercise exercise = new Exercise(id, name, category);
        dbHandler.addExercise(exercise);
        exerciseName.setText("");
        Toast toast = Toast.makeText(CreateExerciseActivity.this, "Exercise added", Toast.LENGTH_SHORT);
        //Toast.makeText(CreateExerciseActivity.this, "Exercise added", Toast.LENGTH_SHORT).show();
        view = toast.getView();

        view.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_ATOP);
        toast.show();
    }

}
