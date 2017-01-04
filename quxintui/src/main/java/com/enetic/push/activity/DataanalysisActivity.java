package com.enetic.push.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.enetic.esale.R;
import com.enetic.push.adapter.TabPagerAdapter;
import com.enetic.push.chart.ColumnChartGenerator;
import com.enetic.push.chart.LineChartGenerator;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.AbstractChartView;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;


@Layout(R.layout.activity_analysis)
public class DataanalysisActivity extends ParentActivity {

    @ViewIn(R.id.chart1)
    ColumnChartView chart;
    @ViewIn(R.id.chart2)
    LineChartView chart2;
    @ViewIn(R.id.chart3)
    ColumnChartView chart3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle("数据分析");
        initOne();
    }


    private float[][] generateColumnValues(int numColumns, int numSubcolumns) {
        float[][] columnChartNumbersTab = new float[numColumns][numSubcolumns];
        for (int i = 0; i < numColumns; ++i) {
            for (int j = 0; j < numSubcolumns; ++j) {
                columnChartNumbersTab[i][j] = (float) (Math.random() * 1000f);
            }
        }
        return columnChartNumbersTab;
    }

    private void initOne( ) {
        int numSubcolumns = 1;
        int numColumns = 9;
        ColumnChartGenerator columnChartGenerator = new ColumnChartGenerator();
        columnChartGenerator.generateChart(chart, generateColumnValues(numColumns, numSubcolumns));
        chart.setBackgroundColor(Color.rgb(0x57, 0x9d, 0xff));
        setChartPort(chart, 0, 1000, 9, 0);

        int maxNumberOfLines = 1;
        int numberOfPoints = 12;
        LineChartGenerator lineChartGenerator = new LineChartGenerator();
        lineChartGenerator.setIsFilled(true);

        lineChartGenerator.generateChart(chart2, generateLineValues(numberOfPoints, maxNumberOfLines));
        chart2.setBackgroundColor(Color.rgb(0xdd, 0x5d, 0x8e));
//        setChartPort(chart2, 0, 1500, 0, 0);


        int numSubcolumns3 = 1;
        int numColumns3 = 31;
        ColumnChartGenerator lineChartGenerator3 = new ColumnChartGenerator();
        lineChartGenerator3.generateChart(chart3, generateColumnValues(numColumns3, numSubcolumns3));
        chart3.setBackgroundColor(Color.rgb(0xc4, 0x6a, 0xff));
        setChartPort(chart3, 0, 1000, 0, 0);
    }


    private float[][] generateLineValues(int numberOfPoints, int maxNumberOfLines) {
        float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 1500f;
            }
        }
        return randomNumbersTab;
    }

    private void setChartPort(AbstractChartView chart, int xl, int yt, int xr, int yb) {
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = yb;
        v.top = yt;
        v.left = xl;
        // You have to set max and current viewports separately.
        chart.setMaximumViewport(v);
        // I changing current viewport with animation in this case.
        chart.setCurrentViewportWithAnimation(v);
    }

}