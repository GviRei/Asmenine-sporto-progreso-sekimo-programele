package com.example.gvidas.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gvidas.database.Exercise;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

import java.util.Random;

public class CreateExerciseActivity extends AppCompatActivity {

    TextView list;
    EditText exerciseName;
    EditText exerciseCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        list = (TextView) findViewById(R.id.list);
        exerciseName = (EditText) findViewById(R.id.exerciseNameTextField);
        exerciseCategory = (EditText) findViewById(R.id.exerciseCategoryTextField);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        // int id = Integer.parseInt(random.getText().toString());
        int id = randId.nextInt(1000 + 1);
        String name = exerciseName.getText().toString();
        String category = exerciseCategory.getText().toString();
        Exercise exercise = new Exercise(id, name, category);
        dbHandler.addExercise(exercise);
        exerciseName.setText("");
        exerciseCategory.setText("");
    }

    public void loadExercise(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        list.setText(dbHandler.loadExercise2());
        exerciseName.setText("");
        exerciseCategory.setText("");
    }
}
