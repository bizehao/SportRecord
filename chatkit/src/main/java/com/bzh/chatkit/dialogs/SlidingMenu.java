package com.bzh.chatkit.dialogs;

/**
 * created by yhao on 2017/8/11.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;


public class SlidingMenu extends HorizontalScrollView {


    //菜单占屏幕宽度比
    private static final float radio = 0.3f;
    private final int mScreenWidth;
    public final int mMenuWidth;

    private boolean once = true;
    private boolean isOpen;
    public boolean carriedOut;

    public SlidingMenu(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth = getScreenWidth(context);
        mMenuWidth = (int) (mScreenWidth * radio);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
    }

    /**
     * 当打开菜单时记录此 view ，方便下次关闭
     */
    public void onOpenMenu() {
        isOpen = true;
        getAdapter().holdOpenMenu(this);
        this.post(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(mMenuWidth * 2, 0);
            }
        });
    }

    /**
     * 关闭菜单
     */

    public void closeMenu() {
        isOpen = false;
        this.post(new Runnable() {
            @Override
            public void run() {
                smoothScrollTo(0, 0);
            }
        });
    }

    /**
     * 菜单是否打开
     */
    public boolean isOpen() {
        return isOpen;
    }


    /**
     * 获取 adapter
     */
    private DialogsListAdapter getAdapter() {
        View view = this;
        while (true) {
            view = (View) view.getParent();
            if (view instanceof RecyclerView) {
                break;
            }
        }
        return (DialogsListAdapter) ((RecyclerView) view).getAdapter();
    }

    public int getMenuScrollX() {
        return this.getScrollX();
    }

    /**
     * 当触摸此 item 时，关闭上一次打开的 item
     */
    public void closeOpenMenu() {
        getAdapter().closeOpenMenu();
    }

    /**
     * 获取打开的 item
     */
    public SlidingMenu getmOpenMenu() {
        return getAdapter().getmOpenMenu();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            wrapper.getChildAt(0).getLayoutParams().width = mScreenWidth;
            wrapper.getChildAt(1).getLayoutParams().width = mMenuWidth;
            wrapper.getChildAt(2).getLayoutParams().width = mMenuWidth;
            once = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //模拟点击
    /*@Override
    public boolean performClick() {
        System.out.println("点击");
        return super.performClick();
    }*/

    //拦截事件
    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                return true;
        }
        return super.onInterceptTouchEvent(ev);
    }*/

    /*@Override
    public boolean onTouchEvent(MotionEvent ev) {
        *//*if (getScrollingMenu() != null && getScrollingMenu() != this) {
            return false;
        }*//*
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("按下");
                downTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("按上");
                int scrollX = getScrollX();
                *//*if (System.currentTimeMillis() - downTime <= 200 && scrollX == 0) { //点击事件
                    return false;
                }
                if (System.currentTimeMillis() - downTime > 200 && scrollX == 0) { //长按事件
                    return false;
                }*//*
                if (Math.abs(scrollX) > mMenuWidth) {
                    System.out.println("打开");
                    this.smoothScrollTo(mMenuWidth * 2, 0);
                    onOpenMenu();
                    this.isOpen = true;
                } else {
                    System.out.println("关闭");
                    this.smoothScrollTo(0, 0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }*/

    long downTime = 0;

    //获取屏幕宽度
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

}
