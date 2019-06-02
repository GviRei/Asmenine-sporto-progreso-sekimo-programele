package com.example.gvidas.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gvidas.Classes.LstViewAdapter;
import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    TextView text, txt;
    Button btDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.startMethodTracing();



        context = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // final Button btDelete = (Button) findViewById(R.id.btDelete);

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.startWorkout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, StartWorkoutActivity.class);
                startActivity(myIntent);
            }
        });


        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show sign up activity
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

        final ListView lstview = (ListView) findViewById(R.id.workoutListView);

        try {
            String[] items = getWorkoutNameAndDate();
            List<String> list = Arrays.asList(items);
            Collections.reverse(list);
            String[] items2 = (String[]) list.toArray();
            LstViewAdapter adapter = new LstViewAdapter(this, R.layout.list_item, R.id.txt, items2);
            lstview.setAdapter(adapter);
        } catch (Exception e) {
            return;
        }
        final String[] IDs = getWorkoutIDS();
        List<String> id = Arrays.asList(IDs);
        Collections.reverse(id);
        final String[] IDs2 = (String[]) id.toArray();

        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                txt = (TextView) view.findViewById(R.id.txt);
                btDelete = (Button) lstview.findViewById(R.id.btDelete);
                Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
                intent.putExtra("workoutID", IDs2[position]);


               // btDelete.setId(Integer.parseInt(IDs[position]));
                startActivity(intent);
            }


        });



    }



    public void clickMe(View view) {
       // Button btDelete = (Button) view;
        TextView txt = (TextView) view;
        btDelete = (Button) view;
        String name = (String) txt.getText();
        int id = btDelete.getId();
        Toast.makeText(MainActivity.this, String.valueOf(id),Toast.LENGTH_SHORT).show();


    }


    public String[] getWorkoutIDS() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String ids = dbHandler.getAllWorkoutIds();
        String[] splitted = ids.split(" ");

        LinkedHashSet<String> array = new LinkedHashSet<String>(Arrays.asList(splitted));
        String[] noDuplicatesID = array.toArray(new String[array.size()]);
        int size = noDuplicatesID.length;
        return noDuplicatesID;
    }

    public String[] getWorkoutNameAndDate() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String[] IDS = getWorkoutIDS();
        int count = IDS.length;
        int id = 0;
        String data = "";
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            id = Integer.parseInt(IDS[i]);
            data = dbHandler.loadWorkoutDataForListView(id);
            result[i] = data;
        }
        return result;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //toolbar selections
        if (id == R.id.action_profile) {
            Intent myIntent = new Intent(MainActivity.this, ProfileShowActivity.class);
            startActivity(myIntent);
            return false;
        } else if (id == R.id.action_calendar) {
            Intent myIntent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(myIntent);
            return false;
        } else if (id == R.id.action_createWorkout) {
            Intent myIntent = new Intent(MainActivity.this, CreateWorkoutActivity.class);
            startActivity(myIntent);
            return false;
        } else if (id == R.id.action_createExercise) {
            Intent myIntent = new Intent(MainActivity.this, CreateExerciseActivity.class);
            startActivity(myIntent);
            return false;
        } else if (id == R.id.action_measureVO2Max) {
            Intent myIntent = new Intent(MainActivity.this, VO2MaxActivity.class);
            startActivity(myIntent);
            return false;
        } else if (id == R.id.check_progress) {
            Intent myIntent = new Intent(MainActivity.this, CheckProgressActivity.class);
            startActivity(myIntent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
