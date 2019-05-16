package com.example.gvidas.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gvidas.database.EditModel;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.gvidas.activities.CustomeAdapter.editModelArrayList;

public class WorkoutActivity extends AppCompatActivity {

    String workoutPlanName = "";
    private ListView lv;

    // private CustomeAdapter customeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        LinearLayout container = (LinearLayout) findViewById(R.id.linearLayout);
        //TextView textView = (TextView)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  ListView listOfExercises = (ListView) findViewById(R.id.exerciseList);
        setSupportActionBar(toolbar);

        TextView name = (TextView) findViewById(R.id.planName);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            workoutPlanName = extras.getString("key");
        }
        name.setText(workoutPlanName);

        CustomeAdapter customeAdapter;
        lv = (ListView) findViewById(R.id.listView);

        editModelArrayList = populateList();
        customeAdapter = new CustomeAdapter(this, editModelArrayList);
        lv.setAdapter(customeAdapter);
        addEditText();
    }

    private ArrayList<EditModel> populateList() {
        int count = getCount();

        ArrayList<EditModel> list = new ArrayList<>();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int workoutPlanId = dbHandler.getWorkoutPlanId(workoutPlanName);
        String str = dbHandler.loadWorkoutPlanOnlyExercises(workoutPlanId);
        String[] exercises = str.split(" ");
        for (int i = 0; i < count; i++) {
            EditModel editModel = new EditModel();
            //editModel.setEditTextValue(String.valueOf(i));
            editModel.setChangeTextViewValue(exercises[i]);
            list.add(editModel);
        }

        return list;
    }
   /* public void addEditText(){
        RelativeLayout ll = (RelativeLayout)findViewById(R.id.relativeLayout);
        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        //int width = display.
        for(int i=0;i<2;i++){
            RelativeLayout l = new RelativeLayout(this);
            l.setGravity(RelativeLayout.CENTER_HORIZONTAL);
            for(int j=0;j<3;j++){
                EditText et = new EditText(this);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams(50,WindowManager.LayoutParams.WRAP_CONTENT);
                l.addView(et,lp);
            }
            ll.addView(l);
        }
    }*/

    public void addEditText() {


    }

    public void getWorkoutPlan() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int workoutPlanId = dbHandler.getWorkoutPlanId(workoutPlanName);
        String str = dbHandler.loadWorkoutPlanOnlyExercises(workoutPlanId);
        String[] exercises = str.split(" ");
        final List<String> listas = new ArrayList<String>(Arrays.asList(exercises));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, listas);
        //listOfExercises.setAdapter(arrayAdapter);

    }

    public int getCount() {
        // ListView listOfExercises = (ListView) findViewById(R.id.);

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int workoutPlanId = dbHandler.getWorkoutPlanId(workoutPlanName);
        String str = dbHandler.loadWorkoutPlanOnlyExercises(workoutPlanId);
        String[] exercises = str.split(" ");
        //final List<String> listas = new ArrayList<String>(Arrays.asList(exercises));
        //final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
        // (this, android.R.layout.simple_list_item_1, listas);
        //listOfExercises.setAdapter(arrayAdapter);
        int count = exercises.length;
        return count;
    }


}
