package com.bzh.sportrecord.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.bzh.sportrecord.R;

public class SideLetterBar extends View {
    private static final String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int choose = -1;
    private Paint paint = new Paint();
    private boolean showBg = false;
    private OnLetterChangedListener onLetterChangedListener;
    private TextView overlay;

    //自定义属性
    private int backgroundColor;
    private int pressBackgroundColor;

    public SideLetterBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SideLetterBar); //获取自定义属性
        backgroundColor =typedArray.getColor(R.styleable.SideLetterBar_background_color,ContextCompat.getColor(context,R.color.transparent));//默认透明
        pressBackgroundColor =typedArray.getColor(R.styleable.SideLetterBar_press_background_color,ContextCompat.getColor(context,R.color.tab_unchecked));//默认灰色
        typedArray.recycle();
    }

    public SideLetterBar(Context context) {
        super(context);
    }

    /**
     * 设置悬浮的textview
     *
     * @param overlay
     */
    public void setOverlay(TextView overlay) {
        this.overlay = overlay;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(100,100,100,100,paint);
        super.onDraw(canvas);
        if (showBg) {
            canvas.drawColor(Color.TRANSPARENT);
        }

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;//每个字母的高度
        for (int i = 0; i < b.length; i++) {
            paint.setTextSize(getResources().getDimension(R.dimen.font_12sp));
            //if (i == 0 || i == 1) {
                //paint.setColor(Color.parseColor("#FFD24B"));// #FFD24B
            //} else {
                paint.setColor(Color.parseColor("#999999"));
            //}
            paint.setAntiAlias(true); //抗锯齿
            if (i == choose) {
                paint.setColor(Color.parseColor("#5c5c5c"));
                paint.setFakeBoldText(true);  //加粗
            }
            float xPos = width / 2 - paint.measureText(b[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(b[i], xPos, yPos, paint);
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnLetterChangedListener listener = onLetterChangedListener;
        final int c = (int) (y / getHeight() * b.length);
        setBackgroundColor(pressBackgroundColor); //变色
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("按下");
                //setBackgroundColor(pressBackgroundColor); //变色
                showBg = true;
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                        if (overlay != null) {
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(b[c]);
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < b.length) {
                        listener.onLetterChanged(b[c]);
                        choose = c;
                        invalidate();
                        if (overlay != null) {
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(b[c]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("按上");
                setBackgroundColor(backgroundColor); //恢复
                showBg = false;
                choose = -1;
                invalidate();
                if (overlay != null) {
                    overlay.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }

    public interface OnLetterChangedListener {
        void onLetterChanged(String letter);
    }

}
