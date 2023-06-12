package com.example.taobaounion.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.taobaounion.base.BaseApplication;

public class UserUtils {

    //按钮等待静止
    public static CountDownTimer getCountDownTimer(Button v) {
        v.setEnabled(false);
        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 更新倒计时文本
                String countdownText = millisUntilFinished / 1000 + "s";
                v.setText(countdownText);
            }

            @Override
            public void onFinish() {
                // 倒计时结束，更新按钮文本和状态
                v.setEnabled(true);
                v.setText("获取验证码");
            }
        };
        return countDownTimer;
    }

    //显示图灵验证码
    public static void showCode(ImageView v) {
        Glide.with(BaseApplication.getAppContext()).
                load(UrlUtils.getCodeUrl(System.currentTimeMillis())).
                diskCacheStrategy(DiskCacheStrategy.NONE).
                skipMemoryCache(true).
                into(v);
    }


    public static void showLoginSuccessAnimation(View view, Activity activity) {
        // 设置旋转动画
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        rotateAnim.setDuration(500);
        rotateAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        // 设置缩小动画
        AnimatorSet scaleAnim = new AnimatorSet();
        scaleAnim.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.5f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.5f)
        );
        scaleAnim.setDuration(500);
        scaleAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        // 组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(rotateAnim, scaleAnim);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 动画结束后关闭 Activity
                activity.finish();
            }
        });

        // 启动动画
        animatorSet.start();
    }
}
