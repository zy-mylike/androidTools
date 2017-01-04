package com.enetic.push.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by json on 2016/6/21.
 */
public class TextShapeView extends TextView {

    public TextShapeView(Context context) {
        super(context);
    }

    public TextShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        setBackgroundColor(Color.TRANSPARENT);

        setGravity(Gravity.CENTER);

        int centX = (w > h ? w : h) / 2 + 1;
        int centY = (w > h ? w : h) / 2 + 1;

        int x1, y1;
        y1 = 10;
        x1 = (int) (centX - (centY - y1) / (Math.sqrt(3.0)));

        int x2, y2;
        y2 = y1;
        x2 = (int) (centX + (centY - y1) / (Math.sqrt(3.0)));

        int x3, y3;
        y3 = centY;
        x3 = (int) (y3 + (centY - y1) * 2 / (Math.sqrt(3.0)));

        int x4, y4;
        y4 = w - y1;
        x4 = x2;


        int x5, y5;
        y5 = w - y1;
        x5 = x1;

        int x6, y6;
        y6 = centY;
        x6 = (int) (centX - (centY - y1) * 2 / (Math.sqrt(3.0)));

        Paint p = new Paint();
        p.setColor(shapeColor);// 设置红色

//        p.setStyle(Paint.Style.STROKE);//设置空心
        Path path1 = new Path();
        path1.moveTo(x1, y1);
        path1.lineTo(x2, y2);
        path1.lineTo(x3, y3);

        path1.lineTo(x4, y4);
        path1.lineTo(x5, y5);
        path1.lineTo(x6, y6);

        path1.close();//封闭
        canvas.drawPath(path1, p);
        super.onDraw(canvas);

    }

    private int shapeColor = Color.RED;

    public void setColorShape(@ColorInt int color) {
        shapeColor = color;
        invalidate();
    }

}
