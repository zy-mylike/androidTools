package com.enetic.push.chart;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AbstractChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;

/**
 * android
 */
public class LineChartGenerator extends BaseChartGenerator {
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean isCubic = false;
    private int pointColor = ChartUtils.COLORS[0];
    private int lineColor = ChartUtils.COLORS[0];

    public void setHasLines(boolean hasLines) {
        this.hasLines = hasLines;
    }

    public void setHasPoints(boolean hasPoints) {
        this.hasPoints = hasPoints;
    }

    public void setShape(ValueShape shape) {
        this.shape = shape;
    }

    /**
     *
     * @param isFilled
     */
    public void setIsFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }

    public void setIsCubic(boolean isCubic) {
        this.isCubic = isCubic;
    }

    /**
     * 数据点颜色
     * @param pointColor
     */
    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    /**
     * 折线颜色
     * @param lineColor
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    @Override
    protected AbstractChartData generateChartData(float[]... datas) {
        List<Line> lines = new ArrayList();
        for (int i = 0; i < datas.length; ++i) {
            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < datas[i].length; ++j) {
                values.add(new PointValue(j, datas[i][j]));
            }

            Line line = new Line(values);
            line.setColor(lineColor);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            line.setPointColor(pointColor);
            lines.add(line);
        }

        return new LineChartData(lines);
    }
}
