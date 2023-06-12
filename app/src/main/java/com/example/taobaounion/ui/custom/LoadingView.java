package com.example.taobaounion.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taobaounion.R;

public class LoadingView extends androidx.appcompat.widget.AppCompatImageView {

    private float mDegrees = 0;
    private boolean isDegrees = true;

    public LoadingView(@NonNull Context context) {
        super(context,null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setImageResource(R.mipmap.loading);
        startRotate();
    }

    private void startRotate() {
        isDegrees = true;
        post(new Runnable() {
            @Override
            public void run() {
                mDegrees += 10;
                if (mDegrees >= 360)
                    mDegrees = 0;
                invalidate();
                if (getVisibility() == VISIBLE && isDegrees) {
                    postDelayed(this, 20);
                } else {
                    removeCallbacks(this);
                }
            }
        });
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRotate();
    }

    private void stopRotate() {
        isDegrees = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(mDegrees, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
    }
}
