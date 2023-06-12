package com.example.taobaounion.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taobaounion.R;
import com.example.taobaounion.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private View mSuccess;
    private View mLoading;
    private View mError;
    private View mEmpty;

    public enum STATE {
        NONE,LOADING,SUCCESS,ERROR,EMPTY
    }

    private Unbinder mBind;
    private FrameLayout mBaseContainer;

    @OnClick(R.id.fragment_error)
    public void retry()
    {
        onRetryClick();
    }

    protected void onRetryClick() {

    }


    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = getRootView(inflater, container);

        mBaseContainer = rootView.findViewById(R.id.base_container);

        LogUtils.d(this,"------------------->"+mBaseContainer);

        addView(inflater,container);

        mBind = ButterKnife.bind(this, rootView);

        //初始化页面
        initView(rootView);
        //实现监听
        initListener();
        //初始化实现类
        initPresenter();
        //加载数据
        loadDate();

        return rootView;
    }

    protected void initListener() {
    }

    protected View getRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.base_fragment_layout, container, false);
    }


    private void addView(LayoutInflater inflater, ViewGroup container) {
        mSuccess = inflater.inflate(getRootViewResId(), container, false);
        LogUtils.d(this,"success---------------->"+getRootViewResId());
        mBaseContainer.addView(mSuccess);

        mLoading = inflater.inflate(R.layout.fragment_loading, container, false);
        mBaseContainer.addView(mLoading);

        mError = inflater.inflate(R.layout.fragment_error, container, false);
        mBaseContainer.addView(mError);

        mEmpty = inflater.inflate(R.layout.fragment_empty, container, false);
        mBaseContainer.addView(mEmpty);
        setState(STATE.NONE);
    }

    //切换页面状态
    public void setState(STATE state) {
        mEmpty.setVisibility(state == STATE.EMPTY ? View.VISIBLE : View.GONE);
        mError.setVisibility(state == STATE.ERROR ? View.VISIBLE : View.GONE);
        mLoading.setVisibility(state == STATE.LOADING ? View.VISIBLE : View.GONE);
        mSuccess.setVisibility(state == STATE.SUCCESS ? View.VISIBLE : View.GONE);

    }

    protected void initView(View rootView) {
    }

    protected void loadDate() {
        //
    }

    protected void initPresenter() {
        //
    }

    protected abstract int getRootViewResId();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {
            mBind.unbind();
        }
        release();
    }

    protected void release() {

    }
}
