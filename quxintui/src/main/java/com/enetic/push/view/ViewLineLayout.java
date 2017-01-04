package com.enetic.push.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enetic.esale.R;

import org.w3c.dom.Text;

/**
 * Created by json on 2016/6/7.
 */
public class ViewLineLayout extends LinearLayout {
    private ImageView mLeftImg;
    private ImageView mRightImg;
    private TextView mText;

    private int leftRes =0;
    private int rightRes =0;
    private String text;

    public ViewLineLayout(Context context) {
        this(context, null);
    }

    public ViewLineLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewLineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ViewLineLayout);

        leftRes = mTypedArray.getResourceId(R.styleable.ViewLineLayout_left_drawable, 0);
        rightRes = mTypedArray.getResourceId(R.styleable.ViewLineLayout_right_drawable, 0);
        text = mTypedArray.getText(R.styleable.ViewLineLayout_texts).toString();
        mTypedArray.recycle();
        inflate(context, R.layout.layout_view_line, this);
        initViews();
    }

    private void initViews() {
        mLeftImg = (ImageView) findViewById(R.id.left_img);
        mRightImg = (ImageView) findViewById(R.id.right_img);
        mText = (TextView) findViewById(R.id.text);
        setLeftImg(leftRes);
        setRightImg(rightRes);
        setText(text);
    }


    public void setLeftImg(@DrawableRes int Img) {
        if(Img == 0){
            return;
        }
        this.mLeftImg.setVisibility(VISIBLE);
        this.mLeftImg.setImageResource(Img);
    }

    public void setRightImg(@DrawableRes int Img) {
        if(Img == 0){
            return;
        }
        this.mRightImg.setVisibility(VISIBLE);
        this.mRightImg.setImageResource(Img);
    }

    public void setText(@StringRes int res) {
        this.mText.setText(res);
    }

    public void setText(String text) {
        this.mText.setText(text);
    }
}
