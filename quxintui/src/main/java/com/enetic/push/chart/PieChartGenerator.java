package com.enetic.push.chart;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AbstractChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;

/**
 * Created by json on 2016/4/12.
 */
public class PieChartGenerator extends BaseChartGenerator {

    private PieChartData data;

    private boolean hasLabels = false;
    private boolean hasLabelsOutside = false;
    private boolean hasCenterCircle = false;
    //    private boolean hasCenterText1 = false;
//    private boolean hasCenterText2 = false;
    private boolean isExploded = false;
    private boolean hasLabelForSelected = false;

    private String centerText1 = "";
    private String centerText2 = "";

    public void setHasLabelsOutside(boolean hasLabelsOutside) {
        this.hasLabelsOutside = hasLabelsOutside;
    }

    public void setHasCenterCircle(boolean hasCenterCircle) {
        this.hasCenterCircle = hasCenterCircle;
    }

    public void setCenterText1(String centerText1) {
        this.centerText1 = centerText1;
    }

    public void setCenterText2(String centerText2) {
        this.centerText2 = centerText2;
    }

    public void setExploded(boolean exploded) {
        isExploded = exploded;
    }

    public void setHasLabelForSelected(boolean hasLabelForSelected) {
        this.hasLabelForSelected = hasLabelForSelected;
    }

    public void setHasAxes(boolean hasAxes) {
        this.hasAxes = hasAxes;
    }

    public void setHasAxesNames(boolean hasAxesNames) {
        this.hasAxesNames = hasAxesNames;
    }

    public void setHasLabels(boolean hasLabels) {
        this.hasLabels = hasLabels;
    }

    @Override
    protected AbstractChartData generateChartData(float[]... datas) {
        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < datas[0].length; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }

        data = new PieChartData(values);
        data = new PieChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        data.setHasLabelsOutside(hasLabelsOutside);
        data.setHasCenterCircle(hasCenterCircle);
        if (isExploded) {
            data.setSlicesSpacing(24);
        }

        if (!TextUtils.isEmpty(centerText1)) {
            data.setCenterText1(centerText1);

        }

        if (!TextUtils.isEmpty(centerText2)) {
            data.setCenterText2(centerText2);
        }

        return new PieChartData(data);
    }
}
