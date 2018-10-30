package com.bzh.sportrecord.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.sportrecord.R;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/29 9:17
 */
public class LineView extends View {

    private Paint mPaint;

    public LineView(Context context) {
        super(context, null);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(20f);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int mWidth = 400;
        int mHeight = 50;
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
        setBackgroundColor(Color.TRANSPARENT);
        float bx = 30; //开始
        float ex = getWidth() - 30; //结束
        float y = getHeight() / 2;
        mPaint.setColor(getResources().getColor(R.color.gray, null));
        canvas.drawLine(bx, y, ex, y, mPaint);
        float muqian = 8;
        float dengfen = (getWidth()-60)/19;
        ex = muqian*dengfen;
        mPaint.setColor(getResources().getColor(R.color.red,null));
        canvas.drawLine(bx, y, ex, y, mPaint);
    }
}
