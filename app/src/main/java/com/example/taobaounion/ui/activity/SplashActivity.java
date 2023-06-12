package com.example.taobaounion.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;

import com.example.taobaounion.R;
import com.example.taobaounion.presenter.impl.LoginPresenterImpl;
import com.example.taobaounion.utils.PresenterManager;
import com.example.taobaounion.view.ILoginCallBack;

import butterknife.BindView;
import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends BaseActivity implements ILoginCallBack {

    @BindView(R.id.activity_splash)
    public GifImageView mGifImageView;
    private LoginPresenterImpl mLoginPresenter;

    @Override
    protected void initPresenter() {
        mLoginPresenter = PresenterManager.getInstance().getLoginPresenter();
        mLoginPresenter.registerViewCallBack(this);
    }

    @Override
    protected void initView() {
        mGifImageView.setImageResource(R.drawable.splash);
        mGifImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //设置启动页停留时间
        new Handler().postDelayed(() -> {
            isLogin();
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }

    //检查token是否过期
    private void isLogin() {
        mLoginPresenter.checkToken();
    }

    @Override
    protected int GetLayoutResId() {
        return R.layout.activity_splash;
    }
}