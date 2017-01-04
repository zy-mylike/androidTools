package com.enetic.push.chart;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AbstractChartData;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;

/**
 * Created by json on 2016/4/12.
 */
public class ColumnChartGenerator extends BaseChartGenerator {

    private ColumnChartData data;

    private boolean isStacked = false;

    public void setStacked(boolean stacked) {
        isStacked = stacked;
    }


//
//
//
//    public void setStacked(boolean stacked) {
//        this.isStacked = stacked;
//    }
//
//    public void setHasLabelForSelected(boolean hasLabelForSelected) {
//        this.hasLabelForSelected = hasLabelForSelected;
//    }
//
//    public void setHasAxes(boolean hasAxes) {
//        this.hasAxes = hasAxes;
//    }
//
//    public void setHasAxesNames(boolean hasAxesNames) {
//        this.hasAxesNames = hasAxesNames;
//    }
//
//    public void setHasLabels(boolean hasLabels) {
//        this.hasLabels = hasLabels;
//    }



    @Override
    protected AbstractChartData generateChartData(float[]... datas) {

        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < datas.length; ++i) {

            values = new ArrayList<>();
            for (int j = 0; j < datas[i].length; ++j) {
                values.add(new SubcolumnValue(datas[i][j], ChartUtils.pickColor()));
            }

            Column column = new Column(values);
            column.setHasLabels(hasLabels);
            column.setHasLabelsOnlyForSelected(hasLabelForSelected);
            columns.add(column);
        }

        data = new ColumnChartData(columns);
        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("Axis X");
                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        data.setStacked(this.isStacked);

        return new ColumnChartData(data);
    }
}
