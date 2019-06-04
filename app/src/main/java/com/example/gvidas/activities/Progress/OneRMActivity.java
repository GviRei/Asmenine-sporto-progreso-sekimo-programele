package com.example.gvidas.activities.Progress;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gvidas.sportapplication.R;

public class OneRMActivity extends AppCompatActivity {

    EditText wgh1, wgh2, wgh3, wgh4, wgh5;
    Button calculate, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_rm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wgh1 = (EditText)findViewById(R.id.WGH1);
        wgh2 = (EditText)findViewById(R.id.WGH2);
        wgh3 = (EditText)findViewById(R.id.WGH3);
        wgh4 = (EditText)findViewById(R.id.WGH4);
        wgh5 = (EditText)findViewById(R.id.WGH5);
        calculate = (Button)findViewById(R.id.countRM);
        reset = (Button)findViewById(R.id.reset);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate1RM();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wgh1.setText("");
                wgh2.setText("");
                wgh3.setText("");
                wgh4.setText("");
                wgh5.setText("");

            }
        });
    }

  /*  public void calculate1RM(){
        if(wgh2.getText().toString().trim().length() != 0){
            double weight = Double.parseDouble(wgh2.getText().toString());
            double max2 = weight * (1.0 + (2.0 / 30.0));
            double max3 = weight * (1.0 + (3.0 / 30.0));
            double max4 = weight * (1.0 + (4.0 / 30.0));
            double max5 = weight * (1.0 + (5.0 / 30.0));
            double max2rounded = Math.round(max2 * 100D)/ 100D;
            double max3rounded = Math.round(max3 * 100D)/ 100D;
            double max4rounded = Math.round(max4 * 100D)/ 100D;
            double max5rounded = Math.round(max5 * 100D)/ 100D;
            wgh1.setText(String.valueOf(max2rounded));
            wgh3.setText(String.valueOf(max3rounded));
            wgh4.setText(String.valueOf(max4rounded));
            wgh5.setText(String.valueOf(max5rounded));
        }
    }*/


    public void calculate1RM(){
        if(wgh1.getText().toString().trim().length() != 0){
            double weight = Double.parseDouble(wgh1.getText().toString());
            double max2 = weight *0.95;
            double max3 = weight * 0.93;
            double max4 = weight * 0.90;
            double max5 = weight * 0.87;
            double max2rounded = Math.round(max2 * 100D)/ 100D;
            double max3rounded = Math.round(max3 * 100D)/ 100D;
            double max4rounded = Math.round(max4 * 100D)/ 100D;
            double max5rounded = Math.round(max5 * 100D)/ 100D;
            wgh2.setText(String.valueOf(max2rounded));
            wgh3.setText(String.valueOf(max3rounded));
            wgh4.setText(String.valueOf(max4rounded));
            wgh5.setText(String.valueOf(max5rounded));
        } else if(wgh2.getText().toString().trim().length() != 0){
            double weight = Double.parseDouble(wgh2.getText().toString());
            double max2 = weight * (1.0 + (2.0 / 30.0));
            double max3 = max2 * 0.93;
            double max4 = max2 * 0.90;
            double max5 = max2 * 0.87;
            double max2rounded = Math.round(max2 * 100D)/ 100D;
            double max3rounded = Math.round(max3 * 100D)/ 100D;
            double max4rounded = Math.round(max4 * 100D)/ 100D;
            double max5rounded = Math.round(max5 * 100D)/ 100D;
            wgh1.setText(String.valueOf(max2rounded));
            wgh3.setText(String.valueOf(max3rounded));
            wgh4.setText(String.valueOf(max4rounded));
            wgh5.setText(String.valueOf(max5rounded));
        } else if(wgh3.getText().toString().trim().length() != 0){
            double weight = Double.parseDouble(wgh3.getText().toString());
            double max = weight * (1.0 + (3.0 / 30.0));
            double max2 = max * 0.95;
            double max4 = max * 0.90;
            double max5 = max * 0.87;
            double maxrounded = Math.round(max * 100D)/ 100D;
            double max3rounded = Math.round(max2 * 100D)/ 100D;
            double max4rounded = Math.round(max4 * 100D)/ 100D;
            double max5rounded = Math.round(max5 * 100D)/ 100D;
            wgh1.setText(String.valueOf(maxrounded));
            wgh2.setText(String.valueOf(max3rounded));
            wgh4.setText(String.valueOf(max4rounded));
            wgh5.setText(String.valueOf(max5rounded));
        }else if(wgh4.getText().toString().trim().length() != 0){
            double weight = Double.parseDouble(wgh4.getText().toString());
            double max = weight * (1.0 + (4.0 / 30.0));
            double max2 = max * 0.95;
            double max3 = max * 0.93;
            double max5 = max * 0.87;
            double maxrounded = Math.round(max * 100D)/ 100D;
            double max3rounded = Math.round(max2 * 100D)/ 100D;
            double max4rounded = Math.round(max3 * 100D)/ 100D;
            double max5rounded = Math.round(max5 * 100D)/ 100D;
            wgh1.setText(String.valueOf(maxrounded));
            wgh2.setText(String.valueOf(max3rounded));
            wgh3.setText(String.valueOf(max4rounded));
            wgh5.setText(String.valueOf(max5rounded));
        }else if(wgh5.getText().toString().trim().length() != 0){
            double weight = Double.parseDouble(wgh5.getText().toString());
            double max = weight * (1.0 + (5.0 / 30.0));
            double max2 = max * 0.95;
            double max3 = max * 0.93;
            double max4 = max * 0.90;
            double maxrounded = Math.round(max * 100D)/ 100D;
            double max3rounded = Math.round(max2 * 100D)/ 100D;
            double max4rounded = Math.round(max3 * 100D)/ 100D;
            double max5rounded = Math.round(max4 * 100D)/ 100D;
            wgh1.setText(String.valueOf(maxrounded));
            wgh2.setText(String.valueOf(max3rounded));
            wgh3.setText(String.valueOf(max4rounded));
            wgh4.setText(String.valueOf(max5rounded));
        }
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
