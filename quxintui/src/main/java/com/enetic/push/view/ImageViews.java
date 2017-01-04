package com.enetic.push.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by json on 2016/6/23.
 */
public class ImageViews extends ImageView {

    private int mWidth;
    private int mHeight;
    private int centreX;
    private int centreY;
    private int mLenght;
    private Paint paint;
    private Bitmap bitmap;

    public ImageViews(Context context) {
        super(context);

    }

    public ImageViews(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        mWidth = getWidth();
        mHeight = getHeight();

        // 计算中心点
        centreX = mWidth / 2;
        centreY = mHeight / 2;

        mLenght = mWidth / 2;

        double radian30 = 30 * Math.PI / 180;
        float a = (float) (mLenght * Math.sin(radian30));
        float b = (float) (mLenght * Math.cos(radian30));
        float c = (mHeight - 2 * b) / 2;

        if (null == paint) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.YELLOW);
            paint.setAlpha(200);
        }

        Path path = new Path();
        path.moveTo(getWidth(),centreY);
        path.lineTo(getWidth() - a, getHeight() - c);
        path.lineTo(getWidth() - a - mLenght, getHeight() - c);
        path.lineTo(0, getHeight() / 2);
        path.lineTo(a, c);
        path.lineTo(getWidth() - a, c);
        path.close();

        canvas.drawPath(path, paint);

        Paint paintcontent = new Paint();
        paintcontent.setColor(Color.WHITE);
        paintcontent.setTextAlign(Paint.Align.CENTER);
        Matrix matrix = new Matrix();
        matrix.postTranslate(this.getWidth() / 2 - bitmap.getWidth() / 2, this.getHeight() / 2 - bitmap.getHeight() / 2 - 5);
        canvas.drawText("123", this.getWidth()/2, this.getHeight()-5, paintcontent);
        canvas.drawBitmap(bitmap, matrix, paintcontent);

/*        问：canvas.drawText("3", x, y, paint);  x和y是指画得时候数字3中心的坐标吗？还是左上角的坐标？
        答：x默认是‘3’这个字符的左边在屏幕的位置，如果设置了paint.setTextAlign(Paint.Align.CENTER);那就是字符的中心，y是指定这个字符baseline在屏幕上的位置。*/

    }


}