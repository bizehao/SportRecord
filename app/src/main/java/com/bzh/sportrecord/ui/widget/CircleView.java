package com.bzh.sportrecord.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.sportrecord.R;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/26 14:36
 */
public class CircleView extends View {

    Paint mPaint; //设置画笔
    int circleColor;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        circleColor = typedArray.getColor(R.styleable.CircleView_circle_color, Color.RED);
        typedArray.recycle();

        init();
    }

    private void init() { //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(circleColor);
        mPaint.setStrokeWidth(5f); //画笔宽度
        mPaint.setStyle(Paint.Style.FILL); //画笔模式为填充
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = 400;
        int mHeight = 400;
        // 当布局参数设置为wrap_content时，设置默认值
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取传入的padpadding值
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int r = Math.min(width, height) / 2;
        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, r, mPaint);

        int start = 30;
        int end = getWidth() - 30;

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10f);
        canvas.drawLine(start, getHeight() / 2, end, getHeight() / 2, mPaint);

        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(20f);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        int spacing = (end - start) / (20 - 1); //平均间距 40
        for (int i = 0; i < 20; i++) {
            canvas.drawPoint(start + i * spacing, getHeight() / 2, mPaint);
        }
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        //canvas.drawPoint(start, getHeight() / 2, mPaint);

    }

}
