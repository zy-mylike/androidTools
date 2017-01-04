package com.enetic.push.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.enetic.esale.R;
import com.enetic.push.adapter.TabPagerAdapter;
import com.enetic.push.chart.PieChartGenerator;
import com.enetic.push.view.NoScrollViewPager;

import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.view.PieChartView;

/**
 * 渠道代理数据
 */
@Layout(R.layout.activity_proxydata)
public class ProxyDataActivity extends ParentActivity {

    @ViewIn(R.id.tab_title)
    private TabLayout mTabLayout;
    @ViewIn(R.id.page)
    private NoScrollViewPager mPageView;
    private List<View> mViews;
    @InflateView(R.layout.layout_prox_data)
    private View mDays;
    @InflateView(R.layout.layout_prox_data)
    private View mWeeks;
    @InflateView(R.layout.layout_prox_data)
    private View mMonths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolTitle("渠道代理数据");
        mViews = new ArrayList<>();
        mViews.add(mDays);
        mViews.add(mWeeks);
        mViews.add(mMonths);

        initTwoView(mDays, Color.rgb(0xCD, 0xED, 0xF7), Color.rgb(0x23, 0xBF, 0xFF));
        initTwoView(mWeeks, Color.rgb(0xE7, 0xf3, 0xd5), Color.rgb(0x9A, 0xDC, 0x62));
        initTwoView(mMonths, Color.rgb(0xF8, 0xD1, 0xD2), Color.rgb(0xF0, 0x43, 0x3D));

        mPageView.setAdapter(new TabPagerAdapter(mViews));
        mTabLayout.setupWithViewPager(mPageView);

        mTabLayout.setTabTextColors(getResources().getColor(R.color.home_toolbar), getResources().getColor(R.color.back));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));

        mTabLayout.getTabAt(0).setText("日");
        mTabLayout.getTabAt(1).setText("周");
        mTabLayout.getTabAt(2).setText("月");

    }


    private void initTwoView(View v, int cricleColor, int cricleProgressColor) {

        PieChartView bar1 = (PieChartView) v.findViewById(R.id.progressBar1);
        PieChartView bar2 = (PieChartView) v.findViewById(R.id.progressBar2);
        PieChartView bar3 = (PieChartView) v.findViewById(R.id.progressBar3);
        PieChartView bar4 = (PieChartView) v.findViewById(R.id.progressBar4);


        int numPieColumns = 2;
        PieChartGenerator pie1 = new PieChartGenerator();
        pie1.setHasCenterCircle(true);
//        pie1.setHasLabels(true);
        pie1.generateChart(bar1, generatePieColumnValues(numPieColumns));

        PieChartGenerator pie2 = new PieChartGenerator();
        pie2.setHasCenterCircle(true);
//        pie2.setHasLabels(true);
        pie2.generateChart(bar2, generatePieColumnValues(numPieColumns));

        PieChartGenerator pie3 = new PieChartGenerator();
        pie3.setHasCenterCircle(true);
//        pie3.setHasLabels(true);
        pie3.generateChart(bar3, generatePieColumnValues(numPieColumns));

        PieChartGenerator pie4 = new PieChartGenerator();
        pie4.setHasCenterCircle(true);
//        pie4.setHasLabels(true);
        pie4.generateChart(bar4, generatePieColumnValues(numPieColumns));

//        bar1.setCricleColor(cricleColor);
//        bar1.setCricleProgressColor(cricleProgressColor);
//
//        bar2.setCricleColor(cricleColor);
//        bar2.setCricleProgressColor(cricleProgressColor);
//
//        bar3.setCricleColor(cricleColor);
//        bar3.setCricleProgressColor(cricleProgressColor);
//
//        bar4.setCricleColor(cricleColor);
//        bar4.setCricleProgressColor(cricleProgressColor);

    }


    private float[][] generatePieColumnValues(int numPieColumns) {
        float[][] columnPieChartNumbersTab = new float[1][numPieColumns];
        for (int i = 0; i < 1; ++i) {
            for (int j = 0; j < numPieColumns; ++j) {
                columnPieChartNumbersTab[i][j] = (float) Math.random() * 100f;
            }
        }
        return columnPieChartNumbersTab;
    }

}