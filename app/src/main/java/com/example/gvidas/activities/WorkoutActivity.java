package com.example.gvidas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gvidas.Classes.EditModel;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.database.WorkoutDone;
import com.example.gvidas.sportapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.gvidas.activities.CustomeAdapter.editModelArrayList;

public class WorkoutActivity extends AppCompatActivity {

    String workoutPlanName = "";
    int workoutID = 0;
    private ListView lv;
    private Button btn;

    // private CustomeAdapter customeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        LinearLayout container = (LinearLayout) findViewById(R.id.linearLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn = (Button) findViewById(R.id.finishWorkout);
        setSupportActionBar(toolbar);

        TextView name = (TextView) findViewById(R.id.planName);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            workoutPlanName = extras.getString("key");
            workoutID = extras.getInt("workoutID");
        }
        name.setText(workoutPlanName);

        CustomeAdapter customeAdapter;
        lv = (ListView) findViewById(R.id.listView);

        editModelArrayList = populateList();
        customeAdapter = new CustomeAdapter(this, editModelArrayList);
        lv.setAdapter(customeAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutActivity.this, FeelingsActivity.class);
                intent.putExtra("workoutID", workoutID);
                intent.putExtra("workoutName", workoutPlanName);
                saveWorkoutDataToDatabase();
                startActivity(intent);
            }
        });
    }

    private void saveWorkoutDataToDatabase(){
        String exerciseName = "";
        int weight = 0;
        WorkoutDone workout;
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        for (int i = 0; i < CustomeAdapter.editModelArrayList.size(); i++) {
            Random random = new Random();
            exerciseName = CustomeAdapter.editModelArrayList.get(i).getChangeTextViewValue();
            weight = Integer.parseInt(CustomeAdapter.editModelArrayList.get(i).getEditTextValue());
            int id = random.nextInt(1000+ 1);
            workout = new WorkoutDone(id, workoutID, exerciseName, weight);
            dbHandler.saveFinishedWorkout(workout);
        }
    }

    private ArrayList<EditModel> populateList() {
        int count = getCount();

        ArrayList<EditModel> list = new ArrayList<>();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int workoutPlanId = dbHandler.getWorkoutPlanId(workoutPlanName);
        String str = dbHandler.loadWorkoutPlanOnlyExercises(workoutPlanId);
        String[] exercises = str.split(",");
        for (int i = 0; i < count; i++) {
            EditModel editModel = new EditModel();
            editModel.setChangeTextViewValue(exercises[i]);
            list.add(editModel);
        }
        return list;
    }

    public void getWorkoutPlan() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int workoutPlanId = dbHandler.getWorkoutPlanId(workoutPlanName);
        String str = dbHandler.loadWorkoutPlanOnlyExercises(workoutPlanId);
        String[] exercises = str.split("");
        final List<String> listas = new ArrayList<String>(Arrays.asList(exercises));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listas);
        //listOfExercises.setAdapter(arrayAdapter);

    }

    public int getCount() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int workoutPlanId = dbHandler.getWorkoutPlanId(workoutPlanName);
        String str = dbHandler.loadWorkoutPlanOnlyExercises(workoutPlanId);
        String[] exercises = str.split(",");
        int count = exercises.length;
        return count;
    }


}
