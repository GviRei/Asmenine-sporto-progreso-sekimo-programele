package com.example.gvidas.activities.Progress;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.gvidas.database.MyDBHandler;
import com.example.gvidas.sportapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.provider.LineChartDataProvider;
import lecho.lib.hellocharts.view.LineChartView;

public class CheckProgressActivity extends AppCompatActivity {

    LineChartView lineChartView;
    TextView text;

    public class ProgressChart {
        double max;
        String date;

        ProgressChart(double max, String date) {
            this.max = max;
            this.date = date;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_progress);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lineChartView = (LineChartView) findViewById(R.id.chart);
        text = (TextView) findViewById(R.id.chartInfo);
        setTitle("Check Progress");

        displayChart();

    }

    public void displayChart() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        String max = dbHandler.loadVo2MaxResult();
        String date = dbHandler.loadVo2MaxDate();
        String allData = dbHandler.loadVo2Max();
        String[] splittedMax = max.split(" ");
        String[] splittedDate = date.split(" ");
        ArrayList<ProgressChart> list = new ArrayList<ProgressChart>();
        ProgressChart chart;
        for (int i = 0; i < splittedMax.length; i++) {
            chart = new ProgressChart(Double.parseDouble(splittedMax[i]), splittedDate[i]);
            list.add(chart);
        }

        text.setText(allData);

        String[] axisData = new String[splittedDate.length];
        int[] yAxisData = new int[splittedMax.length];
        Iterator itr = list.iterator();
        int j = 0;
        while (itr.hasNext()) {
            ProgressChart charts = (ProgressChart) itr.next();
            axisData[j] = charts.date;
            Integer integer = Integer.valueOf((int) Math.round(charts.max));
            yAxisData[j] = integer;
            j++;
        }
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));
        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }
        List lines = new ArrayList();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        lineChartView.setLineChartData(data);
        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);
        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        axis.setTextSize(12);
        yAxis.setName("VO2Max");
        text.setText(yAxis.getValues().toString());
        //only 5 dots visible in screen, for more you need to swipe
        Viewport v = new Viewport(lineChartView.getMaximumViewport());
        v.left = 0;
        v.right = 5;
        lineChartView.setCurrentViewport(v);

        int maximumValue = 0;
        String dateOfMax = "";
        int index = 0;
        for (int i = 0; i < yAxisData.length; i++) {
            int current = yAxisData[i];
            if (current > maximumValue) {
                maximumValue = current;
                dateOfMax = axisData[i];
            }
        }
        text.setText(Html.fromHtml("Your best Vo2Max result - " + "<b>" + String.valueOf(maximumValue) + "</b>"+ " mL/kg/min " + "<br>" + " it was calculated on " + "<b>" + dateOfMax + "</b>"));

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
