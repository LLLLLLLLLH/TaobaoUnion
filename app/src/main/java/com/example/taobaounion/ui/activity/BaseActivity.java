package com.example.taobaounion.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(GetLayoutResId());
        mBind = ButterKnife.bind(this);
        initView();
        initEven();
        initPresenter();
    }

    protected abstract void initPresenter();

    protected void initEven() {
    }

    protected abstract void initView();

    protected abstract int GetLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
        this.release();
    }

    protected void release() {

    }
}
