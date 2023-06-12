package com.example.taobaounion.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.taobaounion.R;


public class AutoLooperViewPager extends ViewPager {

    private static final int DEFAULT_DURATION = 3000;
    private int mDuration;

    public AutoLooperViewPager(@NonNull Context context) {
        this(context,null);
    }

    public AutoLooperViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs,R.styleable.AutoLooperStyle);
        mDuration = t.getInteger(R.styleable.AutoLooperStyle_duration, DEFAULT_DURATION);
        t.recycle();
    }

    private boolean isLoop = false;
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            if (isLoop)
                postDelayed(this, mDuration);
        }
    };

    public void startLoop() {
        isLoop = true;
        post(task);
    }

    public void stopLoop() {
        removeCallbacks(task);
        isLoop = false;

    }

    public void setDuration(int duration)
    {
        this.mDuration = duration;
    }
}
