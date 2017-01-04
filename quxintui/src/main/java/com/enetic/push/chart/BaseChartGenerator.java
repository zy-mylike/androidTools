package com.enetic.push.chart;

import lecho.lib.hellocharts.model.AbstractChartData;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.view.AbstractChartView;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public abstract class BaseChartGenerator {
    protected boolean hasLabels = false;
    protected boolean hasLabelForSelected = false;
    protected boolean hasAxes = true;
    protected boolean hasAxesNames = true;
    protected boolean HasXLine = false;
    protected boolean HasYLine = true;


    public void setHasLabels(boolean hasLabels) {
        this.hasLabels = hasLabels;
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

    public void setHasXLine(boolean hasXLine) {
        HasXLine = hasXLine;
    }

    public void setHasYLine(boolean hasYLine) {
        HasYLine = hasYLine;
    }

    protected final void setAxis(AbstractChartData data) {
        setAxis(data, null, null, HasXLine, HasYLine);
    }

    protected final void setAxis(AbstractChartData data, String xName, String yName, boolean xHasLine, boolean yHasLine) {
        Axis axisX = new Axis().setHasLines(xHasLine);
        Axis axisY = new Axis().setHasLines(yHasLine);
        if (xName != null && !"".equals(xName)) {
            axisX.setName(xName);
        }
        if (yName != null && !"".equals(yName)) {
            axisY.setName(yName);
        }
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
    }

    public void generateChart(AbstractChartView chart, float[]... datas) {
        AbstractChartData data = generateChartData(datas);

        setAxis(data);

        if (data instanceof LineChartData) {
            LineChartView chartView = (LineChartView) chart;
            chartView.setLineChartData((LineChartData) data);
        } else if (data instanceof ColumnChartData) {
            ColumnChartView chartView = (ColumnChartView) chart;
            chartView.setColumnChartData((ColumnChartData) data);
        } else if (data instanceof PieChartData) {
            PieChartView chartView = (PieChartView) chart;
            chartView.setPieChartData((PieChartData) data);
        }
    }

    protected abstract AbstractChartData generateChartData(float[]... datas);
}
